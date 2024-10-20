package com.example.backend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="applicant_id", referencedColumnName = "id")
    private User applicant;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posting_id", referencedColumnName = "id")
    private Posting posting;

    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    private String resume;

    private Date applicationDate;
}
