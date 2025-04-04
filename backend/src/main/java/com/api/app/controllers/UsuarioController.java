package com.api.app.controllers;

import com.api.app.dtos.LoginRequest;
import com.api.app.dtos.LoginResponse;
import com.api.app.models.TipoAcesso;
import com.api.app.models.UsuarioModel;
import com.api.app.repositories.UsuarioRepository;
import com.api.app.services.UsuarioService;  // Importando o UsuarioService

import com.api.app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;  // Injetando o UsuarioService

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest login) {
        Optional<UsuarioModel> user = usuarioRepository.findByEmail(login.getEmail());
        if (user.isPresent() && user.get().getSenha().equals(login.getSenha())) {
            // Passando o tipo (papel) como "INQUILINO" ou o papel que o usuário tem
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getTipoAcesso().name());
            return new LoginResponse(token);
        }
        throw new RuntimeException("Credenciais inválidas");
    }

    @PostMapping("/cadastrar")
    public UsuarioModel criarUsuario(@RequestBody UsuarioModel usuario) {
        usuario.setAtivo(true);  // Define como ativo o usuário criado
        usuario.setTipoAcesso(TipoAcesso.PROPRIETARIO);  // Aqui, ao invés de usar uma String, usamos o enum TipoAcesso
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> usuarios = usuarioService.listarTodos();  // Usando o método do service
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);  // Usando o método do service
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<UsuarioModel> alterarUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioAtualizado, @RequestHeader("Authorization") String token) {
        // Valida o token antes de realizar a operação
        String username = jwtUtil.extractUsername(token.substring(7));  // Remove o "Bearer " do token

        if (username == null) {
            throw new RuntimeException("Token inválido ou expirado");
        }

        // Verifica se o usuário autenticado pode editar esse cadastro
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            UsuarioModel usuario = usuarioExistente.get();
            if (!usuario.getEmail().equals(username)) {
                throw new RuntimeException("Você não tem permissão para editar este usuário!");
            }

            // Atualiza os dados do usuário
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setAtivo(usuarioAtualizado.getAtivo());
            usuario.setTipoAcesso(usuarioAtualizado.getTipoAcesso());
            usuario.setObservacao(usuarioAtualizado.getObservacao());

            // Salva as alterações
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }
}
