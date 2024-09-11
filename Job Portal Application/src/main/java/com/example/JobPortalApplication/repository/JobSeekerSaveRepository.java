package com.example.JobPortalApplication.repository;

import com.example.JobPortalApplication.entity.JobPostActivity;
import com.example.JobPortalApplication.entity.JobSeekerProfile;
import com.example.JobPortalApplication.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave,Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);
    List<JobSeekerSave> findByJob(JobPostActivity job);
}
