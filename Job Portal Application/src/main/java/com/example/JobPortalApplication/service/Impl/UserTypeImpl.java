package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.UserType;
import com.example.JobPortalApplication.repository.UserTypeRepository;
import com.example.JobPortalApplication.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    @Autowired
    public UserTypeImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<UserType> getAll() {
        return userTypeRepository.findAll();
    }
}
