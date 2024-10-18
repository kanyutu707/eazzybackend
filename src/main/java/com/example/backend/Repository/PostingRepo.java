package com.example.backend.Repository;

import com.example.backend.Entities.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepo extends JpaRepository<Posting, Integer> {
}
