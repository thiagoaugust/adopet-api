package br.com.projects.thiago.repository;

import br.com.projects.thiago.model.Adocao;
import br.com.projects.thiago.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    boolean existsByPetIdAndStatus(Long idPet, StatusAdocao status);

}
