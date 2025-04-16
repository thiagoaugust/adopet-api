package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Tutor tutor;

    @Mock
    private Pet pet;

    @Test
    void deveValidarTutorComAdocaoEmAndamento(){
        Adocao adocao1 = new Adocao(tutor, pet, "Motivo");
        adocao1.marcarComoReprovada("");
        Adocao adocao2 = new Adocao(tutor, pet, "Motivo 2");
        adocao2.marcarComoReprovada("");
        List<Adocao> adocoes = List.of(adocao1, adocao2);
        when(adocaoRepository.findAll()).thenReturn(adocoes);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void deveValidarTutorComAdocaoEmAndamentoExcecao(){
        Adocao adocao1 = new Adocao(tutor, pet, "Motivo");
        Adocao adocao2 = new Adocao(tutor, pet, "Motivo 2");
        List<Adocao> adocoes = List.of(adocao1, adocao2);
        when(adocaoRepository.findAll()).thenReturn(adocoes);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void deveValidarTutorComAdocaoEmAndamentoExcecao2(){
        Adocao adocao1 = new Adocao(tutor, pet, "Motivo");
        Adocao adocao2 = adocao1;
        List<Adocao> adocoes = List.of(adocao1, adocao2);
        when(adocaoRepository.findAll()).thenReturn(adocoes);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

}