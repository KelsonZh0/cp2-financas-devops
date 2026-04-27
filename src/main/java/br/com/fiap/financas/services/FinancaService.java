package br.com.fiap.financas.services;

import br.com.fiap.financas.models.Despesas;
import br.com.fiap.financas.repositories.FinancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FinancaService {

    @Autowired
    private FinancaRepository repository;

    public List<Despesas> getAllFinanca(){
        return repository.findAll();
    }

    public Despesas addFinanca(Despesas financa){
        return repository.save(financa);
    }

    public Despesas getMovieById(Long id){
        return findFinancaById(id);
    }

    public void deleteMovie(Long id) {
        findFinancaById(id);
        repository.deleteById(id);
    }

    public Despesas updateFinanca(Long id, Despesas newMovie) {
        findFinancaById(id);
        newMovie.setId(id);
        return repository.save(newMovie);
    }

    private Despesas findFinancaById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa com id " + id + " não encontrado")
        );
    }
}
