package br.com.projects.thiago.service;

import br.com.projects.thiago.dto.CadastroPetDto;
import br.com.projects.thiago.dto.PetDto;
import br.com.projects.thiago.model.Abrigo;
import br.com.projects.thiago.model.Pet;
import br.com.projects.thiago.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository repository;

    @Mock
    private List<PetDto> pets;

    @Mock
    private Abrigo abrigo;

    @Mock
    private CadastroPetDto dto;

    @Test
    void deveCadastrarPet(){
        petService.cadastrarPet(abrigo, dto);
        verify(repository, times(1)).save(new Pet(dto, abrigo));
    }

    @Test
    void deveBuscarPetsDisponiveis(){
        when(repository
                .findAllByAdotadoFalse()
                .stream()
                .map(PetDto::new)
                .toList()).thenReturn(pets);

        List<PetDto> petDtos = petService.buscarPetsDisponiveis();
        assertEquals(pets.size(), petDtos.size());
    }

}