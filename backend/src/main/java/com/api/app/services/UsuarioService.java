package com.api.app.services;

import com.api.app.models.UsuarioModel;
import com.api.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para autenticar o usuário com o email e senha
    public Optional<UsuarioModel> autenticar(String email, String senha) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return usuario;
        }
        return Optional.empty();
    }

    // Método para criar um novo usuário
    public UsuarioModel criarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para listar todos os usuários
    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Método para deletar um usuário pelo ID
    public void deletarUsuario(Long id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }

    // Método para alterar um usuário
    public UsuarioModel alterarUsuario(Long id, UsuarioModel usuarioAtualizado) {
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            UsuarioModel usuario = usuarioExistente.get();
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setAtivo(usuarioAtualizado.getAtivo());
            usuario.setTipoAcesso(usuarioAtualizado.getTipoAcesso());
            usuario.setObservacao(usuarioAtualizado.getObservacao());
            // Atualize outros campos conforme necessário
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }
}
