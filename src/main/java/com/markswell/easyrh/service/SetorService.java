package com.markswell.easyrh.service;

import java.util.List;
import com.markswell.easyrh.model.Setor;
import org.springframework.stereotype.Service;
import com.markswell.easyrh.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SetorService {

    @Autowired
    private SetorRepository repository;

    public List<Setor> findAll() {
        return repository.findAll();
    }

    public Setor findById(Integer id) {
        return repository.findById(id).get();
    }

    public Setor save(Setor setor) {
        return repository.save(setor);
    }

    public Setor update(Setor setor) throws Exception {
        if(findById(setor.getId()) != null)
            return repository.save(setor);
        else {
            throw new Exception();
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
