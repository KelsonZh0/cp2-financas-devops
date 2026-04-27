package br.com.fiap.financas.controllers;

import br.com.fiap.financas.models.Despesas;
import br.com.fiap.financas.services.FinancaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("financas")
@Slf4j
public class FinancaController {
    @Autowired
    private FinancaService service;

    @GetMapping
    public List<Despesas> listAll() {
        return service.getAllFinanca();
    }

    @PostMapping
    public ResponseEntity<Despesas> createFinanca(@RequestBody Despesas financa){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addFinanca(financa));
    }

    @GetMapping("{id}")
    public ResponseEntity<Despesas> getFinancaById(@PathVariable Long id){
        log.info("Obtendo dados da despesa {}", id);
        return ResponseEntity.ok(service.getMovieById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFinanca(@PathVariable Long id) {
        log.info("Deletando despesa com id {}", id);
        service.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Despesas> updateFinanca(@PathVariable Long id, @RequestBody Despesas financa) {
        log.info("Atualizando despesa com id {} com os dados {}", id, financa);
        return ResponseEntity.ok(service.updateFinanca(id, financa));
    }
}
