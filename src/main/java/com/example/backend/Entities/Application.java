package com.example.backend.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String coverLetterurl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    private String resumeurl;

    private String portfoliourl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private Date applicationDate;
}
