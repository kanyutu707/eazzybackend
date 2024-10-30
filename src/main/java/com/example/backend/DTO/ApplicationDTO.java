package com.example.backend.DTO;

import com.example.backend.Entities.ApplicationStatus;
import com.example.backend.Entities.Posting;
import com.example.backend.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    private String coverLetterurl;

    private String resumeurl;

    private Date applicationDate;

    private String portfoliourl;

    private Integer applicant_id;

    private Integer posting_id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="applicant_id", referencedColumnName = "id")
    private User applicant;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posting_id", referencedColumnName = "id")
    private Posting posting;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    private ApplicationStatus applicationStatus;

    public Integer getApplicantId(){
        return applicant_id;
    }

    public  Integer getPostingId(){
        return posting_id;
    }
}
