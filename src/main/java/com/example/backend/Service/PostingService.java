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
                .user(user)
                .build();
        Posting posting=repository.save(postings);
        return new ResponseEntity<>(posting, HttpStatus.CREATED);
    }

    public ResponseEntity<Posting> update(int id, PostingDTO dto){
        Optional<Posting> existingPost=repository.findById(id);

        if(existingPost.isPresent()){
            Posting updatedPost=existingPost.get();
            updatedPost.setPostingDate(dto.getPostingDate());
            updatedPost.setDescription(dto.getDescription());
            updatedPost.setQualifications(dto.getQualifications());
            updatedPost.setPostType(dto.getPostType());
            updatedPost.setRequirements(dto.getRequirements());
            updatedPost.setClosingDate(dto.getClosingDate());
            updatedPost.setSalary(dto.getSalary());
            updatedPost.setTitle(dto.getTitle());

            repository.save(updatedPost);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
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
