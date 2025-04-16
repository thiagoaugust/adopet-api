package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.*;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;

    private AprovacaoAdocaoDto aprovacaoAdocaoDto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Mock
    private ValidacaoSolicitacaoAdocao validacao1;

    @Mock
    private ValidacaoSolicitacaoAdocao validacao2;

    @Mock
    private Adocao adocao;

    @Test
    void deveriaSalvarAdocaoAoSolicitar(){
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        adocaoService.solicitar(dto);

        then(repository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        assertEquals(pet, adocao.getPet());
        assertEquals(tutor, adocao.getTutor());
        assertEquals(dto.motivo(), adocao.getMotivo());
    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar(){
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        validacoes.add(validacao1);
        validacoes.add(validacao2);

        adocaoService.solicitar(dto);

        then(validacao1).should().validar(dto);
        then(validacao2).should().validar(dto);
    }

    @Test
    void deveriaAprovarAdocao(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Esperança", "11987456632", "abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Fred", "RND", 12, "preto", 12.5f), abrigo);
        Tutor tutor = new Tutor(new CadastroTutorDto("Thiago", "115548745", "email@email.com"));

        Adocao adocao = spy(new Adocao(tutor, pet, "motivo"));
        AprovacaoAdocaoDto aprovacaoAdocaoDto = new AprovacaoAdocaoDto(1L);

        when(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).thenReturn(adocao);

        adocaoService.aprovar(aprovacaoAdocaoDto);

        verify(adocao).marcarComoAprovada();
    }

    @Test
    void deveriaReprovarAdocao(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Esperança", "11987456632", "abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Fred", "RND", 12, "preto", 12.5f), abrigo);
        Tutor tutor = new Tutor(new CadastroTutorDto("Thiago", "115548745", "email@email.com"));

        Adocao adocao = spy(new Adocao(tutor, pet, "motivo"));
        ReprovacaoAdocaoDto reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(1L, "Pessoa não confiável");

        when(repository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).thenReturn(adocao);

        adocaoService.reprovar(reprovacaoAdocaoDto);

        verify(adocao).marcarComoReprovada(reprovacaoAdocaoDto.justificativa());

    }

}