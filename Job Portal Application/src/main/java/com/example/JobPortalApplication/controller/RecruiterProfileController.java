package com.example.JobPortalApplication.controller;

import com.example.JobPortalApplication.entity.RecruiterProfile;
import com.example.JobPortalApplication.entity.Users;
import com.example.JobPortalApplication.repository.UsersRepository;
import com.example.JobPortalApplication.service.RecruiterProfileService;
import com.example.JobPortalApplication.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;
    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository,
                                      RecruiterProfileService profileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService=profileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            Users user=usersRepository.findByEmail(currentUsername)
                    .orElseThrow(()->new UsernameNotFoundException("Could not found user"));
            Optional<RecruiterProfile>recruiterProfileOptional=recruiterProfileService.getOne(user.getUserId());
            if(!recruiterProfileOptional.isEmpty()){
                model.addAttribute("profile",recruiterProfileOptional.get());
            }
        }
        return "recruiter_profile";
    }
    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile file,
                         Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            Users user=usersRepository.findByEmail(currentUsername)
                    .orElseThrow(()->new UsernameNotFoundException("Could not found user"));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile",recruiterProfile);
        String fileName="";
        if(!file.getOriginalFilename().equals("")){
            fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile profile=recruiterProfileService.addNew(recruiterProfile);
        String uploadDir="photos/recruiter/"+profile.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir,fileName,file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
