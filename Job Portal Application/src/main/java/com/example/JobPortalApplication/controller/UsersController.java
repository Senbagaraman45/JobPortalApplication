package com.example.JobPortalApplication.controller;

import com.example.JobPortalApplication.entity.UserType;
import com.example.JobPortalApplication.entity.Users;
import com.example.JobPortalApplication.service.UserTypeService;
import com.example.JobPortalApplication.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {
    private final UserTypeService userTypeService;
    private final UsersService usersService;
    @Autowired
    public UsersController(UserTypeService userTypeService,UsersService usersService1) {
        this.userTypeService = userTypeService;
        this.usersService=usersService1;
    }
    @GetMapping("/register")
    public String register(Model model){
        List<UserType> userTypeList=userTypeService.getAll();
        System.out.println(userTypeList);
        model.addAttribute("getAllTypes",userTypeList);
        model.addAttribute("user",new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users,Model model){
//        System.out.println("User:: "+users);
        Optional<Users>optionalUsers=usersService.getUserByEmail(users.getEmail());
        if(optionalUsers.isPresent()){
            model.addAttribute("error","Email already registered,try to login or register with other email");
            List<UserType> userTypeList=userTypeService.getAll();
            model.addAttribute("getAllTypes",userTypeList);
            model.addAttribute("user",new Users());
            return "register";
        }
        usersService.addNew(users);
        return "redirect:/dashboard/";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}
