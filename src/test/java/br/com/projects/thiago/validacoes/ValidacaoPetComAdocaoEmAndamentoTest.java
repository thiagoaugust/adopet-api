package br.com.projects.thiago.validacoes;

import br.com.projects.thiago.dto.SolicitacaoAdocaoDto;
import br.com.projects.thiago.exception.ValidacaoException;
import br.com.projects.thiago.model.StatusAdocao;
import br.com.projects.thiago.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacaoPetComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void deveValidarAdocaoEmAndamento(){
        when(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(false);
        Assertions.assertDoesNotThrow(() -> validacaoPetComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void deveValidarAdocaoEmAndamentoJaExiste(){
        when(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(true);
        Assertions.assertThrows(ValidacaoException.class, () -> validacaoPetComAdocaoEmAndamento.validar(dto));
    }


}