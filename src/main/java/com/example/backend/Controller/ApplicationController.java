package com.example.backend.Controller;

import com.example.backend.DTO.ApplicationDTO;
import com.example.backend.Entities.Application;
import com.example.backend.Service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private ApplicationService service;

    @Autowired
    public void Service(ApplicationService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Application>> getAll(){
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Application> getById(@PathVariable int id){
        return service.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Application> create(@RequestBody ApplicationDTO dto) throws IOException{
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Application> update(@RequestBody Application dto, @PathVariable int id) {
        System.out.println("Update request received for id: " + id);
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        return service.delete(id);
    }
}
