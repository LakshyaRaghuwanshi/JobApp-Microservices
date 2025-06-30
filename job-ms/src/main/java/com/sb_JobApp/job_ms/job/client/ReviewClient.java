package com.sb_JobApp.job_ms.job.client;

import com.sb_JobApp.job_ms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-ms")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
