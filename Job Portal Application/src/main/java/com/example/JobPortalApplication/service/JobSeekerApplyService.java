package com.example.JobPortalApplication.service;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.JobSeekerApply;
import com.example.JobPortalApplication.entity.JobSeekerProfile;

import java.util.List;

public interface JobSeekerApplyService {

    List<JobSeekerApply>getCandidatesJobs(JobSeekerProfile userAccountId);
    List<JobSeekerApply>getJobCandidates(JobPostActivity job);

    void addNew(JobSeekerApply jobSeekerApply);
}
