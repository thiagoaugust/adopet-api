package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository repository;

    @Mock
    private CadastroTutorDto dto;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Test
    void deveCadastrarTutor(){
        when(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).thenReturn(false);

        tutorService.cadastrar(dto);

        verify(repository, times(1)).save(new Tutor(dto));
    }

    @Test
    void deveLancarExcecaoTutorJaExiste(){
        when(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).thenReturn(true);
        assertThrows(ValidacaoException.class, () ->tutorService.cadastrar(dto));
    }

    @Test
    void deveAtualizarTutor(){
        Tutor tutor = spy(new Tutor());
        when(repository.getReferenceById(anyLong())).thenReturn(tutor);

        tutorService.atualizar(atualizacaoTutorDto);
        verify(tutor, times(1)).atualizarDados(atualizacaoTutorDto);
    }

}