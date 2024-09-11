package com.example.JobPortalApplication.service;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.JobSeekerProfile;
import com.example.JobPortalApplication.entity.JobSeekerSave;

import java.util.List;

public interface JobSeekerSaveService {
    List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId);

    List<JobSeekerSave>getJobCandidates(JobPostActivity job);

    void addNew(JobSeekerSave jobSeekerSave);
}
