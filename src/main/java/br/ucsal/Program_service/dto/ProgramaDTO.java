package br.ucsal.Program_service.dto;

public record ProgramaDTO(
        Integer id,
        Integer disciplina_id,
        Integer matriz_id,
        Integer semestre,
        String ementa,
        String objetivos,
        String conteudoProgramatico,
        String metodologia,
        String avaliacao,
        boolean ativo
) {}