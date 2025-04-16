package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private CadastroAbrigoDto cadastroAbrigoDto;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    @Mock
    private Abrigo abrigo;

    @Test
    void deveListarAbrigos(){
        abrigoService.listar();
        then(abrigoRepository).should().findAll();
    }

    @Test
    void deveLancarExcecaoSeAbrigoJaExisteQuandoCadastrarUmAbrigo(){
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(
                cadastroAbrigoDto.nome(),
                cadastroAbrigoDto.telefone(),
                cadastroAbrigoDto.email()))
                .willReturn(true);
        assertThrows(ValidacaoException.class, () -> abrigoService.cadatrar(cadastroAbrigoDto));
    }

    @Test
    void deveCadastrarUmAbrigo(){
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(
                cadastroAbrigoDto.nome(),
                cadastroAbrigoDto.telefone(),
                cadastroAbrigoDto.email()))
                .willReturn(false);

        abrigoService.cadatrar(cadastroAbrigoDto);

        then(abrigoRepository).should().save(abrigoCaptor.capture());
        Abrigo abrigo = abrigoCaptor.getValue();
        assertEquals(cadastroAbrigoDto.nome(), abrigo.getNome());
        assertEquals(cadastroAbrigoDto.telefone(), abrigo.getTelefone());
        assertEquals(cadastroAbrigoDto.email(), abrigo.getEmail());
    }

    @Test
    void deveListarPetsDoAbrigoPorNome(){
        String nome = "Miau";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));
        abrigoService.listarPetsDoAbrigo(nome);
        then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    void deveListarPetsDoAbrigoPorId(){
        Long id = 1L;
        given(abrigoRepository.findById(id)).willReturn(Optional.of(abrigo));
        abrigoService.listarPetsDoAbrigo(String.valueOf(id));
        then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontraAbrigo(){
        Long id = 1L;
        given(abrigoRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(ValidacaoException.class, () -> abrigoService.listarPetsDoAbrigo(String.valueOf(id)));
    }


}