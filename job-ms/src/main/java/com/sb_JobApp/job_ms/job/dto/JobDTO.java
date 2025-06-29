package com.sb_JobApp.job_ms.job.dto;

import com.sb_JobApp.job_ms.job.external.Company;
import com.sb_JobApp.job_ms.job.external.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
