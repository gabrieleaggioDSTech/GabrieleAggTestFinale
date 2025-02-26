package com.demo.service.Prodotto;

import com.demo.object.dto.ProdottoDTO;
import com.demo.object.model.Prodotto;
import com.demo.repository.ProdottoRepository;
import com.demo.tools.DevTools;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdottoServiceImpl implements ProdottoService {

    private final ProdottoRepository prodottoRepository;

    public ProdottoServiceImpl(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
    }

    @Override
    public ProdottoDTO getProdottoById(Long id) {
        return prodottoRepository.findById(id)
                .map(DevTools::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<ProdottoDTO> getAllProdotti() {
        return prodottoRepository.findAll().stream()
                .map(DevTools::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdottoDTO createProdotto(ProdottoDTO prodottoDTO) {
        Prodotto prodotto = DevTools.convertToEntity(prodottoDTO);
        Prodotto savedProdotto = prodottoRepository.save(prodotto);
        return DevTools.convertToDTO(savedProdotto);
    }

    @Override
    @Transactional
    public ProdottoDTO updateProdotto(Long id, ProdottoDTO prodottoDTO) {
        return prodottoRepository.findById(id)
                .map(existingProdotto -> {
                    existingProdotto.setNome(prodottoDTO.getNome());
                    existingProdotto.setPrezzo(prodottoDTO.getPrezzo());
                    return DevTools.convertToDTO(prodottoRepository.save(existingProdotto));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteProdotto(Long id) {
        prodottoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProdottoDTO saveProdotto(ProdottoDTO prodottoDTO) {
        Prodotto prodotto = DevTools.convertToEntity(prodottoDTO);
        Prodotto savedProdotto = prodottoRepository.save(prodotto);
        return DevTools.convertToDTO(savedProdotto);
    }
}
