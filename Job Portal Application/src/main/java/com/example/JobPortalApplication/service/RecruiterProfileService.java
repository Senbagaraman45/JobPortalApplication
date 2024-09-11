package com.example.JobPortalApplication.service;

import com.example.JobPortalApplication.entity.RecruiterProfile;

import java.util.Optional;

public interface RecruiterProfileService {

    Optional<RecruiterProfile>getOne(Integer id);

    RecruiterProfile addNew(RecruiterProfile profile);

    RecruiterProfile getCurrentRecruiterProfile();
}
