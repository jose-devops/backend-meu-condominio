package com.api.app.services;

import com.api.app.dtos.MoradorDTO;
import com.api.app.dtos.MoradorResponseDTO;
import com.api.app.models.MoradorModel;
import com.api.app.models.UsuarioModel;
import com.api.app.repositories.ImovelRepository;
import com.api.app.repositories.MoradorRepository;
import com.api.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public MoradorModel cadastrar(MoradorDTO dto) {
        MoradorModel morador = converterDTOparaModel(dto);
        return moradorRepository.save(morador);
    }

    public List<MoradorResponseDTO> listarTodos() {
        return moradorRepository.findAll()
                .stream()
                .map(this::converterModelParaResponseDTO)
                .toList();
    }

    public Optional<MoradorModel> buscarPorId(Long id) {
        return moradorRepository.findById(id);
    }

    public MoradorModel atualizar(Long id, MoradorDTO dto) {
        MoradorModel existente = moradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Morador não encontrado"));

        MoradorModel atualizado = converterDTOparaModel(dto);
        atualizado.setId(existente.getId()); // manter ID original

        return moradorRepository.save(atualizado);
    }

    public void deletar(Long id) {
        if (!moradorRepository.existsById(id)) {
            throw new RuntimeException("Morador não encontrado");
        }
        moradorRepository.deleteById(id);
    }

    public MoradorResponseDTO converterModelParaResponseDTO(MoradorModel model) {
        MoradorResponseDTO dto = new MoradorResponseDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setCpf(model.getCpf());
        dto.setDataAniversario(model.getDataAniversario());
        dto.setTelefonePrincipal(model.getTelefonePrincipal());
        dto.setTelefoneSecundario(model.getTelefoneSecundario());
        dto.setProfissao(model.getProfissao());
        dto.setRendaMensal(model.getRendaMensal());
        dto.setObservacao(model.getObservacao());

        if (model.getUsuario() != null) {
            dto.setUsuarioEmail(model.getUsuario().getEmail());
        }

        return dto;
    }

    private MoradorModel converterDTOparaModel(MoradorDTO dto) {
        MoradorModel morador = new MoradorModel();
        morador.setNome(dto.getNome());
        morador.setCpf(dto.getCpf());
        morador.setDataAniversario(dto.getDataAniversario());
        morador.setTelefonePrincipal(dto.getTelefonePrincipal());
        morador.setTelefoneSecundario(dto.getTelefoneSecundario());
        morador.setProfissao(dto.getProfissao());
        morador.setRendaMensal(dto.getRendaMensal());
        morador.setObservacao(dto.getObservacao());

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            morador.setUsuario(usuario);
        }

        return morador;
    }
}
