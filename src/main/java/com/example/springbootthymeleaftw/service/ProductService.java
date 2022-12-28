package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import com.example.springbootthymeleaftw.repository.BusinessRepository;
import com.example.springbootthymeleaftw.repository.ProductRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ProductEntity> getAllProductsofUserApproved(String username) {
        var products = productRepository.findAllByUsernameApproved(username);
        return products.orElse(null);
    }

    public List<ProductEntity> getAllProductsofUser(String username) {
        var products = productRepository.findAllByUsername(username);
        return products.orElse(null);
    }

    @Transactional
    public void addProduct(ProductEntity productEntity, String username) {
        ProductEntity newProduct = productEntity;
        var business = businessRepository.findBusinessEntitiesByUsername(username);
        if (business.isPresent()) {
            newProduct.setBusiness(business.get());
            //repository . save replaces my last information instead of adding a new one
            productRepository.insertProduct(newProduct.getName(), newProduct.getDescription(), newProduct.getStock(), newProduct.getBusiness().getId(), null);
        }
    }

    @Transactional
    public void updateStock(long id, int stock) {
        int stockSaved = productRepository.getStockWithId(id);
        int newStock = stockSaved + stock;
        productRepository.updateStock(newStock, id);
    }

    public List<ProductEntity> getAllBBProducts()
    {
        var products = productRepository.findAllProductsFromBB();
        return products.orElse(Collections.emptyList());
    }

    public boolean ValidateProduct(ProductEntity productEntity) {
        boolean isNameValid = productEntity.getName().length() > 0 && productEntity.getName().length() < 20;
        boolean isDescriptionValid = productEntity.getDescription().length() > 0 && productEntity.getName().length() < 40;
        boolean isStockValid = productEntity.getStock() < 10000000;

        return isStockValid && isDescriptionValid && isNameValid;
    }

    public void buyFromBBToBC(Long productId,int amount,String username)
    {
        var business = businessRepository.findBusinessEntitiesByUsername(username);
        var product = productRepository.findProductEntityById(productId);
        if(business.isPresent()&&product.isPresent())
        {
            String companyName = business.get().getCompanyName();

                productRepository.insertProduct(
                        product.get().getName(),
                        product.get().getDescription(),
                        amount,
                        business.get().getId(),
                        companyName
                );
        }
    }
}
