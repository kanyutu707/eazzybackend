package com.example.backend.Service;

import com.example.backend.DTO.PostingDTO;
import com.example.backend.Entities.Posting;
import com.example.backend.Entities.User;
import com.example.backend.Repository.PostingRepo;
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
public class PostingService {
    private PostingRepo repository;
    private final EntityManager entityManager;

    @Autowired
    public void Repository(PostingRepo repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<Posting>> getAll(){
        List<Posting> postings=repository.findAll();
        return new ResponseEntity<>(postings, HttpStatus.OK);
    }

    public ResponseEntity<Posting> getById(int id){
        Optional<Posting> postings=repository.findById(id);
        return postings.map(posting -> new ResponseEntity<>(posting, HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Posting> create(PostingDTO dto) throws IOException{
        User user=null;

        if(dto.getOwnerId()!=null && dto.getOwnerId()!=0){
            user=entityManager.find(User.class, dto.getOwnerId());
        }

        var postings=Posting.builder()
                .title(dto.getTitle())
                .postType(dto.getPostType())
                .description(dto.getDescription())
                .closingDate(dto.getClosingDate())
                .postingDate(dto.getPostingDate())
                .requirements(dto.getRequirements())
                .qualifications(dto.getQualifications())
                .postType(dto.getPostType())
                .salary(dto.getSalary())
                .postingStatus(dto.getPostingStatus())
                .owner(user)
                .build();
        Posting posting=repository.save(postings);
        return new ResponseEntity<>(posting, HttpStatus.CREATED);
    }

    public ResponseEntity<Posting> update(int id, Posting dto){
        Optional<Posting> existingPost=repository.findById(id);

        if(existingPost.isPresent()){

            boolean changed=false;
            Posting updatedPost=existingPost.get();

            if(dto.getPostingDate()!=null && !dto.getPostingDate().equals(updatedPost.getPostingDate())) {
                updatedPost.setPostingDate(dto.getPostingDate());
                changed=true;
            }
            if(dto.getDescription()!=null && !dto.getDescription().isEmpty() && !dto.getDescription().equals(updatedPost.getDescription())) {
                updatedPost.setDescription(dto.getDescription());
                changed=true;
            }
            if(dto.getQualifications()!=null && !dto.getQualifications().isEmpty() && !dto.getQualifications().equals(updatedPost.getQualifications())) {
                updatedPost.setQualifications(dto.getQualifications());
                changed=true;
            }
            if(dto.getPostType()!=null && !dto.getPostType().equals(updatedPost.getPostType())){
            updatedPost.setPostType(dto.getPostType());
            changed=true;
            }
            if(dto.getPostingStatus()!=null && !dto.getPostingStatus().equals(updatedPost.getPostingStatus())){
                updatedPost.setPostingStatus(dto.getPostingStatus());
                changed=true;
            }
            if(dto.getRequirements()!=null && !dto.getRequirements().isEmpty()&& !dto.getRequirements().equals(updatedPost.getRequirements())) {
                updatedPost.setRequirements(dto.getRequirements());
                changed=true;
            }
            if(dto.getClosingDate()!=null &&  !dto.getClosingDate().equals(updatedPost.getClosingDate())) {
                updatedPost.setClosingDate(dto.getClosingDate());
                changed=true;
            }
            if(dto.getSalary()!=updatedPost.getSalary() && dto.getSalary()!=0) {
                updatedPost.setSalary(dto.getSalary());
                changed=true;
            }
            if(dto.getTitle()!=null && !dto.getTitle().isEmpty()&& !dto.getTitle().equals(updatedPost.getTitle())) {
                updatedPost.setTitle(dto.getTitle());
                changed=true;
            }
            if(changed) {
                repository.save(updatedPost);
                System.out.println("Updated application: " + updatedPost);
                return new ResponseEntity<>(updatedPost, HttpStatus.OK);
            }
            else {
                System.out.println("No changes detected. Application not updated.");
                return new ResponseEntity<>(updatedPost, HttpStatus.OK);
            }
        }
        System.out.println("Application not found for id: " + id);
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
