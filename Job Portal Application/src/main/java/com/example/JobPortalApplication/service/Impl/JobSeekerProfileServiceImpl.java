package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.JobSeekerProfile;
import com.example.JobPortalApplication.entity.Users;
import com.example.JobPortalApplication.repository.JobSeekerProfileRepository;
import com.example.JobPortalApplication.repository.UsersRepository;
import com.example.JobPortalApplication.service.JobSeekerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UsersRepository usersRepository;
    @Autowired
    public JobSeekerProfileServiceImpl(JobSeekerProfileRepository jobSeekerProfileRepository,
                                       UsersRepository usersRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.usersRepository=usersRepository;
    }

    @Override
    public Optional<JobSeekerProfile> getOne(Integer id) {
        return jobSeekerProfileRepository.findById(id);
    }

    @Override
    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    @Override
    public JobSeekerProfile getCurrentSeekerProfile() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            Users user=usersRepository.findByEmail(currentUsername)
                    .orElseThrow(()->new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile>jobSeekerProfileOptional=getOne(user.getUserId());
            return jobSeekerProfileOptional.orElse(null);
        }else return null;
    }
}
