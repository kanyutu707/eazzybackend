package com.example.backend.DTO;

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

    private String coverLetter;

    private String resume;

    private Date applicationDate;

    private Integer applicant_id;

    private Integer posting_id;


    public Integer getApplicantId(){
        return applicant_id;
    }

    public  Integer getPostingId(){
        return posting_id;
    }
}
