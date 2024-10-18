package com.example.backend.Service;

import com.example.backend.DTO.ApplicationDTO;
import com.example.backend.Entities.Application;
import com.example.backend.Entities.Posting;
import com.example.backend.Entities.User;
import com.example.backend.Repository.ApplicationRepo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private ApplicationRepo repository;
    private final EntityManager entityManager;

    @Autowired
    public void Repository(ApplicationRepo repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<Application>> getAll(){
        List<Application> applications=repository.findAll();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    public ResponseEntity<Application> getById(int id){
        Optional<Application> applications=repository.findById(id);
        return applications.map(application -> new ResponseEntity<>(application, HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Application> create(ApplicationDTO dto) throws IOException{
        User user=null;
        Posting posting=null;
        if(dto.getApplicant_id()!=null && dto.getApplicantId()!=0){
            user=entityManager.find(User.class, dto.getApplicantId());
        }

        if(dto.getPosting_id()!=null && dto.getPostingId()!=0){
            posting=entityManager.find(Posting.class, dto.getPostingId());
        }

        var applications=Application.builder()
                .applicationDate(dto.getApplicationDate())
                .coverLetter(dto.getCoverLetter())
                .resume(dto.getResume())
                .user(user)
                .posting(posting)
                .build();
        Application application=repository.save(applications);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    public ResponseEntity<Application> update(int id, ApplicationDTO dto){
        Optional<Application> existingApplication=repository.findById(id);

        if(existingApplication.isPresent()){
            Application updateApplication=existingApplication.get();
            updateApplication.setApplicationDate(dto.getApplicationDate());
            updateApplication.setResume(dto.getResume());
            updateApplication.setCoverLetter(dto.getCoverLetter());

            repository.save(updateApplication);
            return new ResponseEntity<>(updateApplication, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> delete(int id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
