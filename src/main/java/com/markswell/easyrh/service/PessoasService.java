package com.markswell.easyrh.service;

import com.markswell.easyrh.model.Pessoas;
import com.markswell.easyrh.repository.PessoasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoasService {

    @Autowired
    private PessoasRepository repository;

    public List<Pessoas> findAll() {
        return repository.findAll();
    }

    public Pessoas findById(Integer id) {
        return repository.findById(id).get();
    }

    public Pessoas save(Pessoas pessoa) {
        return repository.save(pessoa);
    }

    public Pessoas update(Pessoas pessoa) {
        Pessoas pessoaRetorno = findById(pessoa.getId());
        return repository.save(pessoaRetorno);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
