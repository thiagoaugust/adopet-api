package br.com.projects.thiago.validacoes;

import br.com.projects.thiago.dto.SolicitacaoAdocaoDto;
import br.com.projects.thiago.exception.ValidacaoException;
import br.com.projects.thiago.model.Pet;
import br.com.projects.thiago.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private PetRepository petRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }

}
