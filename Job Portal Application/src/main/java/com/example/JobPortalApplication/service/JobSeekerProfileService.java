package com.example.JobPortalApplication.service;

import com.example.JobPortalApplication.entity.JobSeekerProfile;

import java.util.Optional;

public interface JobSeekerProfileService {
    Optional<JobSeekerProfile>getOne(Integer id);

    JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile);

    JobSeekerProfile getCurrentSeekerProfile();
}
