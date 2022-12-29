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

    public List<ProductEntity> getAllBBProducts() {
        var products = productRepository.findAllProductsFromBB();
        return products.orElse(Collections.emptyList());
    }

    public List<ProductEntity> getAllBCProducts() {
        var products = productRepository.findAllProductsFromBC();
        return products.orElse(Collections.emptyList());
    }

    public boolean ValidateProduct(ProductEntity productEntity) {
        boolean isNameValid = productEntity.getName().length() > 0 && productEntity.getName().length() < 20;
        boolean isDescriptionValid = productEntity.getDescription().length() > 0 && productEntity.getName().length() < 40;
        boolean isStockValid = productEntity.getStock() < 10000000;

        return isStockValid && isDescriptionValid && isNameValid;
    }

    public void buyFromBBToBC(Long productId, int amount, String username) {
        var business = businessRepository.findBusinessEntitiesByUsername(username);
        var product = productRepository.findProductEntityById(productId);
        if (business.isPresent() && product.isPresent()) {
            String productCompany = product.get().getBusiness().getCompanyName();

            productRepository.insertProduct(
                    product.get().getName(),
                    product.get().getDescription(),
                    amount,
                    business.get().getId(),
                    productCompany
            );
        }
    }

    public List<ProductEntity> getAllProductsToApprove(String username) {
        var business = businessRepository.findBusinessEntitiesByUsername(username);
        if (business.isPresent()) {
            var productsExists = productRepository.findAllProductsToApprove(business.get().getCompanyName());
            return productsExists.orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }

    public boolean approveBCCommand(Long productId, String username) {
        var business = businessRepository.findBusinessEntitiesByUsername(username);
        var product = productRepository.findProductEntityById(productId);
        if (product.isPresent() && business.isPresent()) {
            var originalProduct = productRepository.findOriginalProductByNameAndApprove(product.get().getName(), business.get().getId());
            if (originalProduct.isPresent()) {
                if (product.get().getStock() > originalProduct.get().getStock())
                    return false;

                int newStock =originalProduct.get().getStock() - product.get().getStock();
                productRepository.updateStock(newStock,originalProduct.get().getId());

                var storeHasTheItem =productRepository.FindBusinessHasProduct(product.get().getName(),product.get().getBusiness().getId());
                if(storeHasTheItem.isPresent())
                {
                    int stockNew =storeHasTheItem.get().getStock() + product.get().getStock();

                    productRepository.updateStock(stockNew,storeHasTheItem.get().getId());
                    productRepository.deleteById(product.get().getId());
                }
                else {
                    productRepository.approveApplicationBC(product.get().getId());
                }
            }
        }
        return false;
    }


}
