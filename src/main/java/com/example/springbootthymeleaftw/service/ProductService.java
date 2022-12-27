package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import com.example.springbootthymeleaftw.repository.BusinessRepository;
import com.example.springbootthymeleaftw.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BusinessRepository businessRepository;
    public List<ProductEntity> getAllProductsofUser(String username){
        var products = productRepository.findAllByUsername(username);
        return products.orElse(null);
    }

    @Transactional
    public void addProduct(ProductEntity productEntity,String username)
    {   ProductEntity newProduct = productEntity;
        var business = businessRepository.findBusinessEntitiesByUsername(username);
        if(business.isPresent())
        {
            newProduct.setBusiness(business.get());
            //repository . save replaces my last information instead of adding a new one
            productRepository.insertProduct(newProduct.getName(),newProduct.getDescription(),newProduct.getStock(),newProduct.getBusiness().getId());
        }
    }
}
