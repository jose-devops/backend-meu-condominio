package com.api.app.controllers;

import com.api.app.dtos.MoradorDTO;
import com.api.app.dtos.MoradorResponseDTO;
import com.api.app.models.MoradorModel;
import com.api.app.services.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/morador")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    @PostMapping("/cadastrar")
    public ResponseEntity<MoradorResponseDTO> cadastrar(@RequestBody MoradorDTO dto) {
        MoradorModel novoMorador = moradorService.cadastrar(dto);
        MoradorResponseDTO resposta = moradorService.converterModelParaResponseDTO(novoMorador);
        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MoradorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(moradorService.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<MoradorModel> buscarPorId(@PathVariable Long id) {
        return moradorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MoradorModel> atualizar(@PathVariable Long id, @RequestBody MoradorDTO dto) {
        return ResponseEntity.ok(moradorService.atualizar(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        moradorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
