package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.RecruiterProfile;
import com.example.JobPortalApplication.entity.Users;
import com.example.JobPortalApplication.repository.RecruiterProfileRepository;
import com.example.JobPortalApplication.repository.UsersRepository;
import com.example.JobPortalApplication.service.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;
    @Autowired
    public RecruiterProfileServiceImpl(RecruiterProfileRepository recruiterProfileRepository,
                                       UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository=usersRepository;
    }

    @Override
    public Optional<RecruiterProfile> getOne(Integer id) {
        return recruiterProfileRepository.findById(id);
    }

    @Override
    public RecruiterProfile addNew(RecruiterProfile profile) {
        return recruiterProfileRepository.save(profile);
    }

    @Override
    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            Users user=usersRepository.findByEmail(currentUsername)
                    .orElseThrow(()->new UsernameNotFoundException("user not found"));
            Optional<RecruiterProfile>recruiterProfileOptional=getOne(user.getUserId());
            return recruiterProfileOptional.orElse(null);
        }else {
            return null;
        }
    }
}
