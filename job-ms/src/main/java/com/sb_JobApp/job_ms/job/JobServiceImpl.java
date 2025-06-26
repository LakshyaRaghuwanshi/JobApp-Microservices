package com.sb_JobApp.job_ms.job;

import com.sb_JobApp.job_ms.job.dto.JobDTO;
import com.sb_JobApp.job_ms.job.external.Company;
import com.sb_JobApp.job_ms.job.external.Review;
import com.sb_JobApp.job_ms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{
    @Autowired
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

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
            company = restTemplate.getForObject(
                    "http://company-ms/companies/" + job.getCompanyId(),
                    Company.class);
        } catch (Exception e) {
            System.err.println("Error fetching company for job id: " + job.getId());
            e.printStackTrace();
        }

        // Fetch reviews
        try {
            ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                    "http://review-ms:8083/reviews?companyId=" + job.getCompanyId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {});
            reviews = reviewResponse.getBody();
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
