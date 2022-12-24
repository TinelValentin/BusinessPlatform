package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class BusinessValidatorService implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object businessEntity, Errors errors) {
        BusinessEntity business = (BusinessEntity) businessEntity;

        Boolean isValidCompanyNameLength = !(business.getCompanyName().length() >40);
        Boolean isAddressLength = !(business.getAddress().length() >40);
        Boolean isIdCodeLength = !(business.getIdCode().length() >40);

        if (!isValidCompanyNameLength)
            errors.rejectValue("companyName", "business.IncorrectLength");
        if (!isAddressLength)
            errors.rejectValue("address", "business.IncorrectLength");
        if(!isIdCodeLength)
            errors.rejectValue("idCode", "business.IncorrectLength");
    }
}
