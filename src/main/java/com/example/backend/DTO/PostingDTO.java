package com.example.backend.DTO;

import com.example.backend.Entities.Application;
import com.example.backend.Entities.PostType;
import com.example.backend.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostingDTO {
    private String title;
    private int salary;
    private String description;

    private String requirements;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    private String qualifications;

    private Integer owner_id;


    private Date postingDate;

    private Date closingDate;

    public Integer getOwnerId(){
        return owner_id;
    }
}
