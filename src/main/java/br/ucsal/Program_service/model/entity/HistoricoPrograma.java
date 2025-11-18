package br.ucsal.Program_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class HistoricoPrograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relacionamento interno
    @ManyToOne(optional = false)
    @JoinColumn(name = "programa_id")
    private Programa programa;

    @Column(nullable = true) // 'Criar Programa' (id null) ou atualizações
    private Integer professorId;

    // --- CAMPOS QUE PERMANECEM ---
    private LocalDateTime dataAtualizacao;
    @Column(length = 1000)
    private String descricao;

    public HistoricoPrograma() {}

    // Construtor atualizado para receber o ID
    public HistoricoPrograma(Programa programa, Integer professorId, String descricao) {
        this.programa = programa;
        this.professorId = professorId;
        this.dataAtualizacao = LocalDateTime.now();
        this.descricao = descricao;
    }
}