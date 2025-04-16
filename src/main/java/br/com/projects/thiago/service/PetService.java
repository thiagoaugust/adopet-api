package br.com.projects.thiago.service;

import br.com.projects.thiago.dto.CadastroPetDto;
import br.com.projects.thiago.dto.PetDto;
import br.com.projects.thiago.model.Abrigo;
import br.com.projects.thiago.model.Pet;
import br.com.projects.thiago.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<PetDto> buscarPetsDisponiveis() {
        return repository
                .findAllByAdotadoFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public void cadastrarPet(Abrigo abrigo, CadastroPetDto dto) {
        repository.save(new Pet(dto, abrigo));
    }
}
