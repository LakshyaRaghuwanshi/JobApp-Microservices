package com.sb_JobApp.job_ms.job.external;

import lombok.Data;

@Data
public class Review {

    private Long id;
    private String title;
    private String description;
    private double rating;
}
