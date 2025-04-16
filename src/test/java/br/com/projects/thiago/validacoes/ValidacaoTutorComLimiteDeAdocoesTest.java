package br.com.projects.thiago.validacoes;

import br.com.projects.thiago.dto.CadastroTutorDto;
import br.com.projects.thiago.dto.SolicitacaoAdocaoDto;
import br.com.projects.thiago.exception.ValidacaoException;
import br.com.projects.thiago.model.Adocao;
import br.com.projects.thiago.model.Pet;
import br.com.projects.thiago.model.Tutor;
import br.com.projects.thiago.repository.AdocaoRepository;
import br.com.projects.thiago.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacaoTutorComLimiteDeAdocoes;

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
    void deveriaValidarTutorComLimiteDeAdocoes(){
        Adocao adocao1 = new Adocao(tutor, pet, "Motivo");
        Adocao adocao2 = new Adocao(tutor, pet, "Motivo 2");
        List<Adocao> adocoes = List.of(adocao1, adocao2);
        when(adocaoRepository.findAll()).thenReturn(adocoes);

        Assertions.assertDoesNotThrow(() -> validacaoTutorComLimiteDeAdocoes.validar(dto));
    }

    @Test
    void deveriaValidarTutorComLimiteDeAdocoesException(){
        Tutor thiago = new Tutor(new CadastroTutorDto("Thiago", "11981333149", "thiago.thiago@gmail.com"));
        List<Adocao> adocoes = getAdocaos(thiago);
        when(adocaoRepository.findAll()).thenReturn(adocoes);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(thiago);

        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComLimiteDeAdocoes.validar(dto));
    }

    private List<Adocao> getAdocaos(Tutor thiago) {
        Adocao adocao1 = new Adocao(thiago, pet, "Motivo");
        Adocao adocao2 = new Adocao(thiago, pet, "Motivo 2");
        Adocao adocao3 = new Adocao(thiago, pet, "Motivo 3");
        Adocao adocao4 = new Adocao(thiago, pet, "Motivo 4");
        Adocao adocao5 = new Adocao(thiago, pet, "Motivo 5");
        Adocao adocao6 = new Adocao(thiago, pet, "Motivo 6");
        adocao1.marcarComoAprovada();
        adocao2.marcarComoAprovada();
        adocao3.marcarComoAprovada();
        adocao4.marcarComoAprovada();
        adocao5.marcarComoAprovada();
        adocao6.marcarComoAprovada();
        List<Adocao> adocoes = List.of(adocao1, adocao2, adocao3, adocao4, adocao5, adocao6);
        return adocoes;
    }

}