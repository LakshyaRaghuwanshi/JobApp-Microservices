package com.sb_JobApp.job_ms.job;

import com.sb_JobApp.job_ms.job.client.CompanyClient;
import com.sb_JobApp.job_ms.job.client.ReviewClient;
import com.sb_JobApp.job_ms.job.dto.JobDTO;
import com.sb_JobApp.job_ms.job.external.Company;
import com.sb_JobApp.job_ms.job.external.Review;
import com.sb_JobApp.job_ms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{
    @Autowired
    JobRepository jobRepository;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job) {
        Company company = null;
        List<Review> reviews = new ArrayList<>();

        // Fetch company
        try {
            company = companyClient.getCompany(job.getCompanyId());
        } catch (Exception e) {
            System.err.println("Error fetching company for job id: " + job.getId());
            e.printStackTrace();
        }

        // Fetch reviews
        try {
            reviews = reviewClient.getReviews(job.getCompanyId());
        } catch (Exception e) {
            System.err.println("Error fetching reviews for job id: " + job.getId());
            e.printStackTrace();
        }


        JobDTO jobDTO = JobMapper.
                    mapToJobWithCompanyDto(job, company, reviews);

        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) return null;
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

            if(jobOptional.isPresent()) {
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                job.setCompanyId(updatedJob.getCompanyId());
                jobRepository.save(job);
                return true;
            }
        return false;
    }


}
