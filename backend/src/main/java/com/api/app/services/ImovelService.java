package com.api.app.services;

import com.api.app.dtos.ImovelDTO;
import com.api.app.models.ImovelModel;
import com.api.app.models.ProprietarioModel;
import com.api.app.models.MoradorModel;
import com.api.app.models.StatusImovel;
import com.api.app.repositories.ImovelRepository;
import com.api.app.repositories.ProprietarioRepository;
import com.api.app.repositories.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private MoradorRepository inquilinoRepository;

    public ImovelModel cadastrar(ImovelDTO dto) {
        ImovelModel imovel = converterDTOparaModel(dto);
        return imovelRepository.save(imovel);
    }

    public List<ImovelModel> listarTodos() {
        return imovelRepository.findAll();
    }

    public Optional<ImovelModel> buscarPorId(Long id) {
        return imovelRepository.findById(id);
    }

    public ImovelModel atualizar(Long id, ImovelDTO dto) {
        ImovelModel imovelExistente = imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        ImovelModel atualizado = converterDTOparaModel(dto);
        atualizado.setId(imovelExistente.getId()); // mantém o ID original

        return imovelRepository.save(atualizado);
    }

    public void deletar(Long id) {
        if (!imovelRepository.existsById(id)) {
            throw new RuntimeException("Imóvel não encontrado");
        }
        imovelRepository.deleteById(id);
    }

    private ImovelModel converterDTOparaModel(ImovelDTO dto) {
        ImovelModel imovel = new ImovelModel();

        // vincula proprietário
        ProprietarioModel proprietario = proprietarioRepository.findById(dto.getProprietarioId())
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));
        imovel.setProprietario(proprietario);

        // vincula morador, se informado
        if (dto.getMoradorId() != null) {
            MoradorModel morador = inquilinoRepository.findById(dto.getMoradorId())  // ✅ certo
                    .orElseThrow(() -> new RuntimeException("Morador não encontrado"));
            imovel.setMorador(morador);
        }
        imovel.setDescricao(dto.getDescricao());
        imovel.setEndereco(dto.getEndereco());
        imovel.setCep(dto.getCep());
        imovel.setUf(dto.getUf());
        imovel.setCidade(dto.getCidade());
        imovel.setBairro(dto.getBairro());
        imovel.setNumero(dto.getNumero());
        imovel.setComplemento(dto.getComplemento());
        imovel.setValorAluguel(dto.getValorAluguel());
        imovel.setValorCondominio(dto.getValorCondominio());
        imovel.setSituacao(dto.getSituacao());
        imovel.setObservacao(dto.getObservacao());

        return imovel;
    }
}
