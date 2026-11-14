-- Agentes
INSERT INTO agentes (id, nome, matricula, especialidade, status_do_agente) VALUES
  (1, 'Ana Souza', 'AG-001', 'TATICO', 'DISPONIVEL'),
  (2, 'Marina Torres', 'AG-002', 'CORRECAO_TEMPORAL', 'DISPONIVEL'),
  (3, 'Carlos Lima', 'AG-003', 'INVESTIGACAO', 'DISPONIVEL'),
  (4, 'Rafael Duarte', 'AG-004', 'HISTORIA', 'DISPONIVEL')
ON CONFLICT DO NOTHING; -- se ele ja existir n vai quebrar, como antes, o postgres so vai ignorar

-- Usuários (senha de todos: senha123, gravada com BCrypt)
INSERT INTO usuarios (id, username, senha, perfil, agente_id) VALUES
  (1, 'operador', '$2a$10$D1qLxLhVIirfWXnwcRlP..CYvdqU4yDDwM5WQpuD91cV4FU2WU82C', 'OPERADOR', NULL),
  (2, 'agente', '$2a$10$D1qLxLhVIirfWXnwcRlP..CYvdqU4yDDwM5WQpuD91cV4FU2WU82C', 'AGENTE', 1),
  (3, 'supervisor', '$2a$10$D1qLxLhVIirfWXnwcRlP..CYvdqU4yDDwM5WQpuD91cV4FU2WU82C', 'SUPERVISOR', NULL),
  (4, 'admin', '$2a$10$D1qLxLhVIirfWXnwcRlP..CYvdqU4yDDwM5WQpuD91cV4FU2WU82C', 'ADMINISTRADOR', NULL)
ON CONFLICT DO NOTHING;

-- Eventos históricos
INSERT INTO eventos_historicos (id, titulo, descricao, data, localizacao, importancia) VALUES
  (1, 'Pouso na Lua', 'Apollo 11', '1969-07-20', 'Mar da Tranquilidade', 'CRITICA'),
  (2, 'Queda do Muro de Berlim', 'Abertura das fronteiras', '1989-11-09', 'Berlim', 'ALTA'),
  (3, 'Independência do Brasil', 'Grito do Ipiranga', '1822-09-07', 'São Paulo', 'ALTA')
ON CONFLICT DO NOTHING;

-- Anomalias
INSERT INTO anomalias (id, evento_id, divergencia, risco, origem, status, detectada_em, observacoes) VALUES
  (1, 1, 'Bandeira registrada com cor diferente', 'ALTO', 'DESCONHECIDA', 'DETECTADA', NOW(), NULL),
  (2, 2, 'Muro continua de pé nos registros', 'CRITICO', 'INTERFERENCIA_NAO_AUTORIZADA', 'DETECTADA', NOW(), NULL)
ON CONFLICT DO NOTHING;

-- Histórico das anomalias
INSERT INTO historico_anomalias (id, anomalia_id, tipo_registro, usuario, descricao_registro, valor_anterior, valor_novo, instante) VALUES
  (1, 1, 'ANOMALIA_REGISTRADA', 'sistema', 'Carga inicial', NULL, 'ALTO', NOW()),
  (2, 2, 'ANOMALIA_REGISTRADA', 'sistema', 'Carga inicial', NULL, 'CRITICO', NOW())
ON CONFLICT DO NOTHING;

-- Avança as sequências para depois dos ids fixos
SELECT setval(pg_get_serial_sequence('agentes', 'id'), (SELECT MAX(id) FROM agentes));
SELECT setval(pg_get_serial_sequence('usuarios', 'id'), (SELECT MAX(id) FROM usuarios));
SELECT setval(pg_get_serial_sequence('eventos_historicos', 'id'), (SELECT MAX(id) FROM eventos_historicos));
SELECT setval(pg_get_serial_sequence('anomalias', 'id'), (SELECT MAX(id) FROM anomalias));
SELECT setval(pg_get_serial_sequence('historico_anomalias', 'id'), (SELECT MAX(id) FROM historico_anomalias));