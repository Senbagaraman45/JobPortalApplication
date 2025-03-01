package com.example.JobPortalApplication.controller;

import com.example.JobPortalApplication.entity.*;
import com.example.JobPortalApplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;
    @Autowired
    public JobSeekerApplyController(JobPostActivityService jobPostActivityService,
                                    UsersService usersService,
                                    JobSeekerApplyService seekerApplyService,
                                    JobSeekerSaveService seekerSaveService,
                                    RecruiterProfileService profileService,
                                    JobSeekerProfileService seekerProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
        this.jobSeekerApplyService=seekerApplyService;
        this.jobSeekerSaveService=seekerSaveService;
        this.recruiterProfileService=profileService;
        this.jobSeekerProfileService=seekerProfileService;
    }
    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id")int id, Model model){
        JobPostActivity jobDetails=jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList=
                jobSeekerApplyService.getJobCandidates(jobDetails);
        List<JobSeekerSave>jobSeekerSaveList=
                jobSeekerSaveService.getJobCandidates(jobDetails);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiter=recruiterProfileService.getCurrentRecruiterProfile();
                if(recruiter!=null){
                    model.addAttribute("applyList",jobSeekerApplyList);
                }
            }else{
                JobSeekerProfile jobSeeker=jobSeekerProfileService.getCurrentSeekerProfile();
                if(jobSeeker!=null){
                    boolean exists=false;
                    boolean saved=false;
                    for(JobSeekerApply jobSeekerApply:jobSeekerApplyList){
                        if(jobSeekerApply.getUserId().getUserAccountId()==jobSeeker.getUserAccountId()){
                            exists=true;
                            break;
                        }
                    }
                    for (JobSeekerSave jobSeekerSave:jobSeekerSaveList){
                        if(jobSeekerSave.getUserId().getUserAccountId()==jobSeeker.getUserAccountId()){
                            saved=true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied",exists);
                    model.addAttribute("alreadySaved",saved);
                }
            }
        }
        JobSeekerApply jobSeekerApply=new JobSeekerApply();
        model.addAttribute("applyJob",jobSeekerApply);

        model.addAttribute("jobDetails",jobDetails);
        model.addAttribute("user",usersService.getCurrentUserProfile());
        return "job-details";
    }
    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id")int id,JobSeekerApply jobSeekerApply){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            Users user=usersService.findByEmail(currentUsername);
            Optional<JobSeekerProfile>jobSeekerProfileOptional =jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity=jobPostActivityService.getOne(id);
            if(jobSeekerProfileOptional.isPresent()&&jobPostActivity!=null){
                jobSeekerApply=new JobSeekerApply();
                jobSeekerApply.setUserId(jobSeekerProfileOptional.get());
                jobSeekerApply.setJob(jobPostActivity);
                jobSeekerApply.setApplyDate(new Date());
            }else{
                throw new RuntimeException("user not found");
            }
            jobSeekerApplyService.addNew(jobSeekerApply);
        }
        return "redirect:/dashboard/";
    }
}
