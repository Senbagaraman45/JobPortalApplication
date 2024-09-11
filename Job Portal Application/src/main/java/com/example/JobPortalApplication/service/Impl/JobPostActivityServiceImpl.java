package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.*;
import com.example.JobPortalApplication.repository.JobPostActivityRepository;
import com.example.JobPortalApplication.service.JobPostActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobPostActivityServiceImpl implements JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;
    @Autowired
    public JobPostActivityServiceImpl(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    @Override
    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    @Override
    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {
        List<RecruiterJobs>recruiterJobs=jobPostActivityRepository.getRecruiterJobs(recruiter);
        List<RecruiterJobsDto>recruiterJobsDtoList=new ArrayList<>();
        for(RecruiterJobs rec:recruiterJobs){
            JobLocation loc=new JobLocation(rec.getLocationId(),rec.getCity(),rec.getState(),rec.getCountry());
            JobCompany company=new JobCompany(rec.getCompanyId(),rec.getName(),"");
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(),rec.getJob_post_id(),rec.getJob_title(),loc,company));
        }
        return recruiterJobsDtoList;
    }

    @Override
    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Job not found"));
    }

    @Override
    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }

    @Override
    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {

        return Objects.isNull(searchDate)?jobPostActivityRepository.searchWithoutDate(job,location,type,remote)
                :jobPostActivityRepository.search(job,location,type,remote,searchDate);
    }
}
