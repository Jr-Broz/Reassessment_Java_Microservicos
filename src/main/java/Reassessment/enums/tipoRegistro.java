package Reassessment.enums;

public enum tipoRegistro {
    ANOMALIA_REGISTRADA, // anomalia criada
    RISCO_ALTERADO, // mudanca de classificacao 
    STATUS_ALTERADO, // mudanca de situacao
    MISSAO_CRIADA, // missao criada pra resolver anomalia
    AGENTES_DESIGNADOS, //  agentes para a missao
    MISSAO_AUTORIZADA, // autorizacao da missao registrada
    MISSAO_INICIADA, //  missao iniciada, no caso é + um regitro de q foi
    INTERVENCAO_REGISTRADA, // intervencao foi registrada
    OCORRENCIA_REGISTRADA, // ocorrencia foi registrada
    MISSAO_ENCERRADA, // ou concluiu ou falhou
    MISSAO_CANCELADA, // missao cancelada 
}