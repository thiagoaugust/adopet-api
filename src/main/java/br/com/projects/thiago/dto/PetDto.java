package br.com.projects.thiago.dto;

import br.com.projects.thiago.model.Pet;
import br.com.projects.thiago.model.TipoPet;

public record PetDto(Long id, TipoPet tipo, String nome, String raca, Integer idade) {

    public PetDto(Pet pet) {
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade());
    }

}
