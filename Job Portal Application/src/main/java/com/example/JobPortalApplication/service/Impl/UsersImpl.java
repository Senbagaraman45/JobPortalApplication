package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.JobSeekerProfile;
import com.example.JobPortalApplication.entity.RecruiterProfile;
import com.example.JobPortalApplication.entity.Users;
import com.example.JobPortalApplication.repository.JobSeekerProfileRepository;
import com.example.JobPortalApplication.repository.RecruiterProfileRepository;
import com.example.JobPortalApplication.repository.UsersRepository;
import com.example.JobPortalApplication.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired

    public UsersImpl(UsersRepository usersRepository,JobSeekerProfileRepository jobSeekerProfileRepository
    ,RecruiterProfileRepository recruiterProfileRepository,PasswordEncoder passwordEncoder1)
    {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
        this.recruiterProfileRepository=recruiterProfileRepository;
        this.passwordEncoder=passwordEncoder1;
    }

    @Override
    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationData(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser=usersRepository.save(users);
        int usertypeId=users.getUserTypeId().getUserTypeId();
        if(usertypeId==1){
            recruiterProfileRepository.save(new RecruiterProfile(users));
        }else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(users));
        }
        return savedUser;
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Object getCurrentUserProfile() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username=authentication.getName();
            Users user=usersRepository.findByEmail(username).orElseThrow(()->new
                    UsernameNotFoundException("Could not found user"));
            int userId=user.getUserId();
            if(authentication.getAuthorities().contains(new
                    SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiterProfile=recruiterProfileRepository.findById(userId)
                        .orElse(new RecruiterProfile());
                return recruiterProfile;
            }else{
                JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findById(userId)
                        .orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }

    @Override
    public Users getCurrentUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username=authentication.getName();
            Users user=usersRepository.findByEmail(username).orElseThrow(()->new
                    UsernameNotFoundException("Could not found user"));
            return user;
        }
        return null;
    }

    @Override
    public Users findByEmail(String currentUsername) {
        return usersRepository.findByEmail(currentUsername)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
}
