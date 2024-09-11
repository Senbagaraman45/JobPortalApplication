package com.example.JobPortalApplication.repository;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.JobSeekerApply;
import com.example.JobPortalApplication.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);
    List<JobSeekerApply> findByJob(JobPostActivity job);
}
