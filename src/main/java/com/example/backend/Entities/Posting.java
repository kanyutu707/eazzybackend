package com.example.backend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int salary;
    private String description;
    private String requirements;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    private String qualifications;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "posting")
    private Set<Application> applications=new HashSet<>();

    @Enumerated(EnumType.STRING)

    private ApplicationStatus postingStatus;

    private Date postingDate;

    private Date closingDate;
}
