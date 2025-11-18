package br.ucsal.Program_service.controller;

import br.ucsal.Program_service.dto.HistoricoProgramaDTO;
import br.ucsal.Program_service.model.entity.HistoricoPrograma;
import br.ucsal.Program_service.service.HistoricoProgramaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
@CrossOrigin(origins = "*")
public class HistoricoProgramaController {

    private final HistoricoProgramaService service;

    public HistoricoProgramaController(HistoricoProgramaService service) {
        this.service = service;
    }

    // Este endpoint provavelmente será chamado apenas pelo Orquestrador ou ProgramaService
    @PostMapping
    public HistoricoPrograma salvar(@RequestBody HistoricoProgramaDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<HistoricoPrograma> listar() {
        return service.listar();
    }
}