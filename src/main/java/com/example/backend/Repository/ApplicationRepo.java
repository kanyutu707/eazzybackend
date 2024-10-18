package com.example.backend.Repository;

import com.example.backend.Entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepo extends JpaRepository<Application, Integer> {
}
