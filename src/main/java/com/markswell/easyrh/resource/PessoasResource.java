package com.markswell.easyrh.resource;

import com.markswell.easyrh.model.Pessoas;
import com.markswell.easyrh.service.PessoasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoasResource {

    @Autowired
    private PessoasService service;

    @GetMapping
    public ResponseEntity<List<Pessoas>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoas> getPessoa(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(service.findById(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Pessoas pessoa) {
        try{
            URI uri = getUri(service.save(pessoa).getId());
            return ResponseEntity.created(uri).build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Pessoas> update(@RequestBody Pessoas pessoa) {
        try{
            return ResponseEntity.ok(service.update(pessoa));
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

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
    }

}
