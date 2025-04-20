package com.api.app.controllers;

import com.api.app.dtos.ImovelDTO;
import com.api.app.dtos.ImovelResponseDTO;
import com.api.app.models.ImovelModel;
import com.api.app.services.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/imovel")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ImovelResponseDTO> cadastrar(@RequestBody ImovelDTO dto) {
        ImovelModel imovel = imovelService.cadastrar(dto);

        ImovelResponseDTO response = new ImovelResponseDTO();
        response.setId(imovel.getId());
        response.setEndereco(imovel.getEndereco());
        response.setCep(imovel.getCep());
        response.setCidade(imovel.getCidade());
        response.setEstado(imovel.getUf());
        response.setDescricao(imovel.getDescricao());
        response.setProprietarioId(imovel.getProprietario().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ImovelResponseDTO>> listarTodos() {
        List<ImovelModel> imoveis = imovelService.listarTodos();

        List<ImovelResponseDTO> resposta = imoveis.stream().map(imovel -> {
            ImovelResponseDTO dto = new ImovelResponseDTO();
            dto.setId(imovel.getId());
            dto.setEndereco(imovel.getEndereco());
            dto.setCep(imovel.getCep());
            dto.setCidade(imovel.getCidade());
            dto.setEstado(imovel.getUf());
            dto.setDescricao(imovel.getDescricao());
            dto.setProprietarioId(imovel.getProprietario().getId());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelModel> buscarPorId(@PathVariable Long id) {
        return imovelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ImovelResponseDTO> atualizar(@PathVariable Long id, @RequestBody ImovelDTO dto) {
        ImovelModel imovel = imovelService.atualizar(id, dto);

        ImovelResponseDTO response = new ImovelResponseDTO();
        response.setId(imovel.getId());
        response.setEndereco(imovel.getEndereco());
        response.setCep(imovel.getCep());
        response.setCidade(imovel.getCidade());
        response.setEstado(imovel.getUf());
        response.setDescricao(imovel.getDescricao());
        response.setProprietarioId(imovel.getProprietario().getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        imovelService.deletar(id);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Imóvel deletado com sucesso!");

        return ResponseEntity.ok(resposta); // 200 OK com mensagem no corpo
    }
}
