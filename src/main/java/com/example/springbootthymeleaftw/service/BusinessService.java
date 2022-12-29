package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
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

    public List<String> findAllBuinessesBB()
    {
        var businesses = businessRepository.findAllBBBusinesses();
        return businesses.orElse(Collections.emptyList());
    }

    @Transactional
    public void approveBusinessWithId(String id)
    {
        businessRepository.approveBusinessWithId(Long.parseLong(id));
    }
}
