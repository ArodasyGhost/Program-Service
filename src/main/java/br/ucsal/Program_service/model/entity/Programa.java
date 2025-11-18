package br.ucsal.Program_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer disciplinaId; // Armazena SÓ o ID

    @Column(nullable = false)
    private Integer matrizId; // Armazena SÓ o ID

    private Integer atualizadoPorProfessorId; // Armazena SÓ o ID

    private Integer semestre;
    @Column(columnDefinition = "TEXT")
    private String ementa;
    @Column(columnDefinition = "TEXT")
    private String objetivos;
    @Column(columnDefinition = "TEXT")
    private String conteudoProgramatico;
    @Column(columnDefinition = "TEXT")
    private String metodologia;
    @Column(columnDefinition = "TEXT")
    private String avaliacao;

    private boolean ativo = true;
    private boolean finalizado = false;
    private LocalDateTime dataFinalizacao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    // --- Relacionamento DENTRO do microserviço
    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoPrograma> historico;

    public Programa() {}

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
