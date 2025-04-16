package br.com.projects.thiago.repository;

import br.com.projects.thiago.model.Abrigo;
import br.com.projects.thiago.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByAdotadoFalse();

    List<Pet> findByAbrigo(Abrigo abrigo);
}
