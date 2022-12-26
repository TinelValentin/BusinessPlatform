package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    public List<BusinessEntity> getAllNotApprovedBusinesses()
    {
        var allBusinesses = businessRepository.findAllNotApprovedBusinesses();
        return allBusinesses.orElse(null);
    }

    public boolean approveBusinessWithId(String id)
    {

    }
}
