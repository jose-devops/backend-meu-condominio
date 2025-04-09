package com.api.app.services;

import com.api.app.models.ProprietarioModel;
import com.api.app.repositories.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    // Criar um novo proprietário
    public ProprietarioModel criarProprietario(ProprietarioModel proprietario) {
        return proprietarioRepository.save(proprietario);
    }

    // Listar todos os proprietários
    public List<ProprietarioModel> listarTodos() {
        return proprietarioRepository.findAll();
    }

    // Buscar um proprietário por ID
    public Optional<ProprietarioModel> buscarPorId(Long id) {
        return proprietarioRepository.findById(id);
    }

    // Deletar um proprietário
    public void deletarProprietario(Long id) {
        proprietarioRepository.deleteById(id);
    }
}
