package com.api.app.controllers;

import com.api.app.dtos.ImovelDTO;
import com.api.app.models.ImovelModel;
import com.api.app.services.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imovel")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ImovelModel> cadastrar(@RequestBody ImovelDTO dto) {
        ImovelModel imovel = imovelService.cadastrar(dto);
        return ResponseEntity.status(201).body(imovel);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ImovelModel>> listarTodos() {
        return ResponseEntity.ok(imovelService.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelModel> buscarPorId(@PathVariable Long id) {
        return imovelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ImovelModel> atualizar(@PathVariable Long id, @RequestBody ImovelDTO dto) {
        return ResponseEntity.ok(imovelService.atualizar(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        imovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
