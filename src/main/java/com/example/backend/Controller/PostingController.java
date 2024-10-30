package com.example.backend.Controller;

import com.example.backend.DTO.PostingDTO;
import com.example.backend.Entities.Posting;
import com.example.backend.Service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class PostingController {
    private PostingService service;

    @Autowired
    public void Service(PostingService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity <List<Posting>> getAll(){
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Posting> getById(@PathVariable int id){
        return service.getById(id);
    }

    @PostMapping(value="/create")
    public ResponseEntity<Posting> create(@RequestBody PostingDTO dto) throws IOException {
        return service.create(dto);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Posting> update(@RequestBody Posting postingDTO, @PathVariable int id){
        return service.update(id, postingDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        return service.delete(id);
    }
}
