package com.markswell.easyrh.resource;

import com.markswell.easyrh.model.Pessoas;
import com.markswell.easyrh.model.Setor;
import com.markswell.easyrh.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/setor")
public class SetorResource {

    @Autowired
    private SetorService service;

    @GetMapping
    public ResponseEntity<List<Setor>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Setor> getPessoa(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(service.findById(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Setor setor) {
        try{
            URI uri = getUri(service.save(setor).getId());
            return ResponseEntity.created(uri).build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Setor> update(@RequestBody Setor setor) {
        try{
            return ResponseEntity.ok(service.update(setor));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = "/{id}/pessoas")
    public ResponseEntity<List<Pessoas>> getPessoasBySetor(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.findById(id).getPessoas());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
    }


}
