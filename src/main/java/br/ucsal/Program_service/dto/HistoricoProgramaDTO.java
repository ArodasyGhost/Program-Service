package br.ucsal.Program_service.dto;

import java.time.LocalDateTime;

public record HistoricoProgramaDTO(
        Integer id,
        Integer programaId,
        Integer professorId,
        LocalDateTime dataAtualizacao,
        String descricao
) {}