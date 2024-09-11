package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.JobSeekerApply;
import com.example.JobPortalApplication.entity.JobSeekerProfile;
import com.example.JobPortalApplication.repository.JobSeekerApplyRepository;
import com.example.JobPortalApplication.service.JobSeekerApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyServiceImpl implements JobSeekerApplyService {
    private final JobSeekerApplyRepository jobSeekerApplyRepository;
    @Autowired
    public JobSeekerApplyServiceImpl(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    @Override
    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    @Override
    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }

    @Override
    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
