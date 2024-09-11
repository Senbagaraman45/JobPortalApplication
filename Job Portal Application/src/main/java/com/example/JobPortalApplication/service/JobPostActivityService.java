package com.example.JobPortalApplication.service;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.RecruiterJobsDto;

import java.time.LocalDate;
import java.util.List;

public interface JobPostActivityService {

    JobPostActivity addNew(JobPostActivity jobPostActivity);

    List<RecruiterJobsDto>getRecruiterJobs(int recruiter);

    JobPostActivity getOne(int id);

    List<JobPostActivity> getAll();

    List<JobPostActivity> search(String job, String location, List<String> list, List<String> list1, LocalDate searchDate);
}
