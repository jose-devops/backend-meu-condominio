package com.api.app.controllers;

import com.api.app.models.ProprietarioModel;
import com.api.app.models.UsuarioModel;
import com.api.app.repositories.ProprietarioRepository;
import com.api.app.repositories.UsuarioRepository;
import com.api.app.security.JwtUtil;
import com.api.app.services.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioService proprietarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<ProprietarioModel> cadastrarProprietario(@RequestBody ProprietarioModel proprietario,
                                                                   @RequestHeader("Authorization") String tokenHeader) {

        // Extrair o token removendo o prefixo "Bearer " se presente
        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;


        // Extrair o email do usuário autenticado do token
        String emailFromToken = jwtUtil.extractUsername(token);


        // Buscar o usuário autenticado no banco de dados
        UsuarioModel usuario = usuarioRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Associar o usuário autenticado ao proprietário
        proprietario.setUsuario(usuario);



        // Salvar o proprietário no banco de dados
        ProprietarioModel novoProprietario = proprietarioService.criarProprietario(proprietario);

        // Retornar o novo proprietário com status CREATED
        return new ResponseEntity<>(novoProprietario, HttpStatus.CREATED);
    }

    // Listar todos os proprietários
    @GetMapping("/listar")
    public ResponseEntity<List<ProprietarioModel>> listarProprietarios() {
        List<ProprietarioModel> proprietarios = proprietarioService.listarTodos();
        return new ResponseEntity<>(proprietarios, HttpStatus.OK);
    }

    // Buscar um proprietário por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProprietarioModel> buscarProprietarioPorId(@PathVariable Long id) {
        Optional<ProprietarioModel> proprietario = proprietarioService.buscarPorId(id);
        return proprietario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProprietario(@PathVariable Long id) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();

            // Aqui buscamos o Proprietário relacionado a este Usuário
            ProprietarioModel proprietario = proprietarioRepository.findByUsuario(usuario)
                    .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

            // Deletar o proprietário, o que automaticamente vai deletar o usuário associado
            proprietarioRepository.delete(proprietario);

            // Agora deletamos o usuário
            usuarioRepository.delete(usuario);

            return ResponseEntity.ok("Proprietário e usuário deletados com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }


    @PutMapping("/alterar/{id}")
    public ResponseEntity<ProprietarioModel> alterarProprietario(@PathVariable Long id,
                                                                 @RequestBody ProprietarioModel proprietarioAtualizado,
                                                                 @RequestHeader("Authorization") String tokenHeader) {

        // Extrair o token removendo o prefixo "Bearer " se presente
        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;

        // Extrair o email do usuário autenticado do token
        String emailFromToken = jwtUtil.extractUsername(token);

        // Buscar o usuário autenticado no banco de dados
        UsuarioModel usuario = usuarioRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Buscar o proprietário a ser alterado
        ProprietarioModel proprietario = proprietarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

        // Verificar se o proprietário pertence ao usuário autenticado
        if (!proprietario.getUsuario().getEmail().equals(emailFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);  // Não é permitido editar esse proprietário
        }

        // Atualizar os dados do proprietário
        proprietario.setNome(proprietarioAtualizado.getNome());
        proprietario.setRazaoSocial(proprietarioAtualizado.getRazaoSocial());
        proprietario.setCpfCnpj(proprietarioAtualizado.getCpfCnpj());
        proprietario.setTelefonePrincipal(proprietarioAtualizado.getTelefonePrincipal());
        proprietario.setTelefoneSecundario(proprietarioAtualizado.getTelefoneSecundario());
        proprietario.setEmail(proprietarioAtualizado.getEmail());
        proprietario.setObservacao(proprietarioAtualizado.getObservacao());

        // Caso o proprietário tenha mudado o tipo de "usuario" (caso necessário), podemos atualizar
        if (proprietarioAtualizado.getUsuario() != null) {
            proprietario.setUsuario(proprietarioAtualizado.getUsuario());
        }

        // Salvar o proprietário atualizado
        ProprietarioModel updatedProprietario = proprietarioRepository.save(proprietario);

        // Retornar o proprietário atualizado
        return ResponseEntity.ok(updatedProprietario);
    }





}
