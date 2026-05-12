package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Optional<ProductResponse> getProductByID(Long id) {
       return productRepository.findByIdAndActiveTrue(id).map(this::mapToProductResponse);
    }


    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, productRequest);
            Product savedProduct =  productRepository.save(existingProduct);
            return mapToProductResponse(savedProduct);
        });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                                .map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                                .map(product -> {
                                    product.setActive(false);
                                    productRepository.save(product);
                                    return true;
                                }).orElse(false);

//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found: "+ id));
//        product.setActive(false);
//        productRepository.save(product);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword)
                                .stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
    }


    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setActive(savedProduct.isActive());
        return productResponse;
    }
}
