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

    public ResponseEntity<List<Application>> getAll() {
        List<Application> applications = repository.findAll();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    public ResponseEntity<Application> getById(int id) {
        Optional<Application> applications = repository.findById(id);
        return applications.map(application -> new ResponseEntity<>(application, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Application> create(ApplicationDTO dto) throws IOException {
        User user = null;
        Posting posting = null;
        if (dto.getApplicant_id() != null && dto.getApplicantId() != 0) {
            user = entityManager.find(User.class, dto.getApplicantId());
        }

        if (dto.getPosting_id() != null && dto.getPostingId() != 0) {
            posting = entityManager.find(Posting.class, dto.getPostingId());
        }

        var applications = Application.builder()
                .applicationDate(dto.getApplicationDate())
                .coverLetter(dto.getCoverLetter())
                .resume(dto.getResume())
                .applicant(user)
                .posting(posting)
                .applicationStatus(dto.getApplicationStatus())
                .build();
        Application application = repository.save(applications);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    public ResponseEntity<Application> update(int id, Application dto) {
        System.out.println("Update method called with id: " + id);
        System.out.println("Received DTO: " + dto);
        Optional<Application> existingApplicationOptional = repository.findById(id);

        if (existingApplicationOptional.isPresent()) {
            Application existingApplication = existingApplicationOptional.get();
            System.out.println("Existing application found: " + existingApplication);

            boolean changed = false;

            if (dto.getApplicationDate() != null && !dto.getApplicationDate().equals(existingApplication.getApplicationDate())) {
                existingApplication.setApplicationDate(dto.getApplicationDate());
                changed = true;
            }

            if (dto.getCoverLetter() != null && !dto.getCoverLetter().isEmpty() && !dto.getCoverLetter().equals(existingApplication.getCoverLetter())) {
                existingApplication.setCoverLetter(dto.getCoverLetter());
                changed = true;
            }

            if (dto.getResume() != null && !dto.getResume().isEmpty() && !dto.getResume().equals(existingApplication.getResume())) {
                existingApplication.setResume(dto.getResume());
                changed = true;
            }

            if (dto.getApplicationStatus() != null && dto.getApplicationStatus() != existingApplication.getApplicationStatus()) {
                existingApplication.setApplicationStatus(dto.getApplicationStatus());
                changed = true;
            }

            if (changed) {
                Application savedApplication = repository.save(existingApplication);
                System.out.println("Updated application: " + savedApplication);
                return new ResponseEntity<>(savedApplication, HttpStatus.OK);
            } else {
                System.out.println("No changes detected. Application not updated.");
                return new ResponseEntity<>(existingApplication, HttpStatus.OK);
            }
        }

        System.out.println("Application not found for id: " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> delete(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}