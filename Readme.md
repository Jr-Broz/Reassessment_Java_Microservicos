# Continuum Control

API em Spring Boot para controle de anomalias temporais da Continuum Authority. Dá pra cadastrar agentes, eventos históricos e anomalias, criar missões, designar agentes, autorizar, iniciar e encerrar missões, registrar intervenções e ocorrências, ver o histórico de cada anomalia e acompanhar o estado da linha temporal pelo painel.

## Tecnologias

- Java 25 + Spring Boot 4.1.1
- Spring Web, Spring Data JPA, Spring Data MongoDB, Spring Data Redis + Cache
- Spring Security com JWT (biblioteca java-jwt da Auth0)
- Bean Validation, Lombok, Actuator
- JUnit 5 + Mockito
- Docker e Docker Compose

## Serviços

| Serviço | Porta | Para que usei |
|---|---|---|
| PostgreSQL 17 | 5432 | banco principal |
| MongoDB 8 | 27017 | ocorrências das missões |
| Redis 7 | 6379 | cache do painel |
| Aplicação | 8080 | a API |

**Por que três bancos:**

- **PostgreSQL**: o domínio é cheio de relacionamento (anomalia pertence a um evento, missão a uma anomalia, missão tem vários agentes...) e várias operações mudam várias tabelas de uma vez, como iniciar e encerrar missão. Precisa de chave estrangeira pra garantir integridade e de transação pra não ficar nada pela metade.
- **MongoDB**: as ocorrências não têm estrutura fixa. Uma distorção temporal tem intensidade e duração, uma evidência tem objeto, período estimado e grau de confiabilidade. No Mongo cada documento pode ter campos diferentes no `detalhes`, sem precisar de uma tabela por tipo ou um monte de coluna vazia.
- **Redis**: o painel faz várias contagens no banco toda vez que é chamado. Ele pode ficar alguns segundos desatualizado, então guardo o resultado no Redis com tempo de expiração (TTL de 30s por padrão). Assim o Postgres não é consultado a cada acesso ao painel.

## Configuração

As senhas, portas e chaves ficam no `.env`, que não vai pro repositório. Tem um modelo no `.env.example`:

```bash
cp .env.example .env
```

Troque os valores `troque_aqui` e gere a chave do JWT com:

```bash
openssl rand -base64 32
```

e cole em `JWT_SECRET`.

O `application.yml` só lê essas variáveis (`${POSTGRES_HOST:localhost}`, etc.), então pra mudar alguma configuração é só mexer no `.env`, sem recompilar.

## Como rodar

### Com Docker (tudo junto)

```bash
docker compose up -d --build
```

Sobe os três bancos e a aplicação. Na primeira vez demora um pouco por causa do download das imagens e dependências. Pra ver o log:

```bash
docker compose logs -f app
```

Quando aparecer `Started InfnetApplication` está pronto.

Dentro do Docker a aplicação acha os bancos pelo nome do serviço (`postgres`, `mongo`, `redis`), isso já está configurado no `docker-compose.yml`.

### Sem Docker na aplicação

Sobe só os bancos e roda pelo Maven:

```bash
docker compose up -d postgres mongo redis
./mvnw spring-boot:run
```

(Se o `app` estiver rodando no Docker, pare antes com `docker compose stop app`, senão dá conflito na porta 8080.)

## Build

```bash
./mvnw clean package
```

Gera o `target/Infnet-0.0.1-SNAPSHOT.jar` (roda os testes antes). Pra rodar o jar:

```bash
java -jar target/Infnet-0.0.1-SNAPSHOT.jar
```

## Testes

```bash
./mvnw test
```

Testes de unidade com Mockito no `OperacaoMissaoService`, que é onde estão as principais regras de consistência. Os repositórios são mockados, então não precisa de banco ligado.

| Teste | O que verifica |
|---|---|
| Não deixa iniciar missão sem agente | missão sem agente não inicia |
| Agente ocupado impede o início e nada é registrado | agente que não está disponível bloqueia o início e nada vai pro histórico |
| Iniciar muda missão, agentes e anomalia | missão vai pra EM_EXECUCAO, agentes pra EM_MISSAO e anomalia pra EM_CORRECAO |
| Missão que falhou não pode estabilizar a anomalia | FALHOU + ESTABILIZADA é recusado |

## Verificando se está funcionando

```bash
curl localhost:8080/actuator/health
```

Tem que responder `"status":"UP"`. Depois o login:

```bash
curl -X POST localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"admin","senha":"senha123"}'
```

Volta um `token`, que vai no header das outras requisições:

```bash
curl localhost:8080/agentes -H "Authorization: Bearer SEU_TOKEN"
```

## Dados iniciais

O `data.sql` carrega ao subir: 4 agentes, 3 eventos históricos, 2 anomalias (uma delas com risco crítico) e 4 usuários. Ele usa `ON CONFLICT DO NOTHING`, então reiniciar não duplica nada.

| Usuário | Senha | Perfil |
|---|---|---|
| `operador` | `senha123` | OPERADOR |
| `agente` | `senha123` | AGENTE (é a agente Ana Souza, id 1) |
| `supervisor` | `senha123` | SUPERVISOR |
| `admin` | `senha123` | ADMINISTRADOR |

Pra zerar o banco e voltar pros dados iniciais:

```bash
docker compose down -v
docker compose up -d --build
```

## Permissões

O login gera um JWT, e o `SecurityFilter` valida o token em cada requisição e carrega o perfil do usuário. A API é stateless (sem sessão) e as senhas ficam salvas com BCrypt.

| Operação | Quem pode |
|---|---|
| Consultas (`GET`) | qualquer usuário logado |
| Cadastrar eventos, anomalias e missões, designar agentes | OPERADOR, SUPERVISOR, ADMINISTRADOR |
| Registrar intervenções e ocorrências | AGENTE, SUPERVISOR, ADMINISTRADOR |
| Autorizar, iniciar, encerrar e cancelar missão | SUPERVISOR, ADMINISTRADOR |
| Alterar risco e status de anomalia | SUPERVISOR, ADMINISTRADOR |
| Cadastrar e alterar agentes | SUPERVISOR, ADMINISTRADOR |
| Excluir | ADMINISTRADOR |

Além do perfil, o agente só consegue registrar intervenção ou ocorrência **no próprio nome**. Se tentar no nome de outro agente, leva 403.

## Principais regras

- Missão nasce PLANEJADA e só pode ser criada pra anomalia ativa (irreversível não recebe missão).
- Só agente DISPONIVEL pode ser designado e iniciar missão.
- Missão não inicia sem pelo menos um agente.
- Missão de prioridade EMERGENCIA ou de anomalia CRITICA precisa ser autorizada antes de iniciar. A autorização guarda quem autorizou, quando e uma observação.
- Ao iniciar: missão vai pra EM_EXECUCAO, agentes pra EM_MISSAO e, se for missão de correção, a anomalia vai pra EM_CORRECAO. Tudo na mesma transação.
- Intervenção e ocorrência só em missão EM_EXECUCAO e com agente designado nela.
- Ao encerrar: agentes voltam pra DISPONIVEL (ou SUSPENSO se a missão falhou com impacto severo) e a anomalia recebe a situação final. Missão que falhou não pode estabilizar a anomalia.
- Agente que já participou de missão não pode ser excluído, tem que ir pra INATIVO. Evento com anomalia também não pode ser excluído.
- Toda operação numa anomalia vai pro histórico com o usuário que fez (`GET /anomalias/{id}/historico`).

Os métodos de escrita dos services são `@Transactional`: se alguma regra falhar no meio, o Spring faz rollback e nada é salvo.

Na ocorrência, que grava no Postgres (histórico) e no Mongo, o save no Mongo fica por último. Se ele falhar, o histórico no Postgres é desfeito junto.

## Rotas

| Recurso | Rotas |
|---|---|
| Login | `POST /auth/login` |
| Agentes | `GET /agentes` (filtros `?status=` e `?especialidade=`), `GET /agentes/{id}`, `POST`, `PUT /{id}`, `PATCH /{id}/status`, `DELETE /{id}`, `GET /agentes/{id}/missoes` |
| Eventos | `GET /eventos` (filtros `?importancia=` e `?inicio=&fim=`), `GET /eventos/{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| Anomalias | `GET /anomalias` (filtros `?status=` e `?risco=`), `/ativas`, `/criticas`, `/{id}`, `/{id}/historico`, `/{id}/missoes`, `POST`, `PUT /{id}`, `PATCH /{id}/risco`, `PATCH /{id}/status` |
| Missões | `GET /missoes` (filtro `?status=`), `/em-execucao`, `/{id}`, `POST`, `PUT /{id}` |
| Ações da missão | `POST /missoes/{id}/agentes`, `/autorizar`, `/iniciar`, `/encerrar`, `/cancelar` |
| Intervenções | `GET` e `POST /missoes/{id}/intervencoes` |
| Ocorrências | `GET` e `POST /missoes/{id}/ocorrencias` |
| Painel | `GET /painel/estado`, `GET /painel/indice` |

## Índice de Estabilidade

Começa em 100 e vai perdendo pontos, sem passar de 0:

```
penalidade = ativas x 5 + criticas x 10 + em execucao x 2 + falhas x 8 + intervencoes severas x 6
indice = 100 - penalidade
```

| Índice | Estado |
|---|---|
| 80 a 100 | ESTAVEL |
| 50 a 79 | ATENCAO |
| 20 a 49 | INSTAVEL |
| 0 a 19 | CRITICA |

## Erros

Os erros voltam sempre no mesmo formato, pelo `GlobalExceptionHandler`:

```json
{
  "timestamp": "2026-11-14T01:00:00",
  "status": 409,
  "erro": "Conflict",
  "mensagem": "Missao precisa de pelo menos 1 agente pra poder comecar"
}
```

| Status | Quando |
|---|---|
| 400 | dados inválidos |
| 401 | não logado ou senha errada |
| 403 | sem permissão |
| 404 | não encontrado |
| 409 | conflito com uma regra (ex: agente ocupado) |
| 500 | erro inesperado |