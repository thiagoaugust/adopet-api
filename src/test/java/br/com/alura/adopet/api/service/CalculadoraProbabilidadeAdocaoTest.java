package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraProbabilidadeAdocaoTest {


    @Test
    @DisplayName("Retorna Probabilidade Alta -> Cachorro Idade < 10 e Peso <= 15 ")
    void cenario01(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Feliz", "11988775564", "abrigofeliz@gmail.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Benny", "RND", 4, "Preto", 4.0f), abrigo);
        CalculadoraProbabilidadeAdocao calculadoraProbabilidadeAdocao = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidadeAdocao = calculadoraProbabilidadeAdocao.calcular(pet);
        assertEquals(ProbabilidadeAdocao.ALTA, probabilidadeAdocao);
    }

    @Test
    @DisplayName("Retorna Probabilidade Média -> Cachorro Idade > 15 e Peso < 15")
    void cenario02(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Feliz", "11988775564", "abrigofeliz@gmail.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Benny", "RND", 15, "Preto", 4.0f), abrigo);
        CalculadoraProbabilidadeAdocao calculadoraProbabilidadeAdocao = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidadeAdocao = calculadoraProbabilidadeAdocao.calcular(pet);
        assertEquals(ProbabilidadeAdocao.MEDIA, probabilidadeAdocao);
    }

    @Test
    @DisplayName("Retorna Probabilidade Baixa -> Cachorro Idade > 15 e Peso > 15")
    void cenario03(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Feliz", "11988775564", "abrigofeliz@gmail.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Benny", "RND", 15, "Preto", 16.0f), abrigo);
        CalculadoraProbabilidadeAdocao calculadoraProbabilidadeAdocao = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidadeAdocao = calculadoraProbabilidadeAdocao.calcular(pet);
        assertEquals(ProbabilidadeAdocao.BAIXA, probabilidadeAdocao);
    }

    @Test
    @DisplayName("Retorna Probabilidade Baixa -> Gato Idade > 10 e Peso > 10")
    void cenario04(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Feliz", "11988775564", "abrigofeliz@gmail.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Benny", "RND", 11, "Preto", 12.0f), abrigo);
        CalculadoraProbabilidadeAdocao calculadoraProbabilidadeAdocao = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidadeAdocao = calculadoraProbabilidadeAdocao.calcular(pet);
        assertEquals(ProbabilidadeAdocao.BAIXA, probabilidadeAdocao);
    }

    @Test
    @DisplayName("Retorna Probabilidade Média -> Gato Idade < 10 e Peso > 10")
    void cenario05(){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Feliz", "11988775564", "abrigofeliz@gmail.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Benny", "RND", 11, "Preto", 5.0f), abrigo);
        CalculadoraProbabilidadeAdocao calculadoraProbabilidadeAdocao = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidadeAdocao = calculadoraProbabilidadeAdocao.calcular(pet);
        assertEquals(ProbabilidadeAdocao.MEDIA, probabilidadeAdocao);
    }
}