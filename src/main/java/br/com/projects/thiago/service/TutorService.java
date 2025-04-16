package br.com.projects.thiago.service;

import br.com.projects.thiago.dto.AtualizacaoTutorDto;
import br.com.projects.thiago.dto.CadastroTutorDto;
import br.com.projects.thiago.exception.ValidacaoException;
import br.com.projects.thiago.model.Tutor;
import br.com.projects.thiago.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public void cadastrar(CadastroTutorDto dto) {
        boolean jaCadastrado = repository.existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }

        repository.save(new Tutor(dto));
    }

    public void atualizar(AtualizacaoTutorDto dto) {
        Tutor tutor = repository.getReferenceById(dto.id());
        tutor.atualizarDados(dto);
    }

}
