package com.sb_JobApp.job_ms.job.mapper;

import com.sb_JobApp.job_ms.job.Job;
import com.sb_JobApp.job_ms.job.dto.JobDTO;
import com.sb_JobApp.job_ms.job.external.Company;
import com.sb_JobApp.job_ms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
