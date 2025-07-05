package com.sb_JobApp.job_ms.job;

import com.sb_JobApp.job_ms.job.client.CompanyClient;
import com.sb_JobApp.job_ms.job.client.ReviewClient;
import com.sb_JobApp.job_ms.job.dto.JobDTO;
import com.sb_JobApp.job_ms.job.external.Company;
import com.sb_JobApp.job_ms.job.external.Review;
import com.sb_JobApp.job_ms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findAllFallback")
    @Retry(name = "companyRetry", fallbackMethod = "findAllFallback")
    @RateLimiter(name = "companyRateLimiter", fallbackMethod = "findAllFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<JobDTO> findAllFallback(Throwable t) {
        System.err.println("Fallback triggered in findAll(): " + t.getMessage());
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(job -> {
            Company fallbackCompany = new Company(job.getCompanyId(), "Unavailable Company", "N/A");
            List<Review> emptyReviews = new ArrayList<>();
            return JobMapper.mapToJobWithCompanyDto(job, fallbackCompany, emptyReviews);
        }).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId()); // will throw exception if service down
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
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
