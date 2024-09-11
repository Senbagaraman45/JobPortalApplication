package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.JobSeekerProfile;
import com.example.JobPortalApplication.entity.JobSeekerSave;
import com.example.JobPortalApplication.repository.JobSeekerSaveRepository;
import com.example.JobPortalApplication.service.JobSeekerSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveServiceImpl implements JobSeekerSaveService {
    private final JobSeekerSaveRepository jobSeekerSaveRepository;
    @Autowired
    public JobSeekerSaveServiceImpl(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    @Override
    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    @Override
    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }

    @Override
    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}
