package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.model.Product;
import com.example.hello.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    // 为返回的产品对象添加完整图片URL
    private Product enrichProductWithImageUrls(Product product) {
        if (product != null) {
            // 主图URL
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String mainImageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/")
                    .path(product.getImage())
                    .toUriString();
                product.setImage(mainImageUrl);
            }
            
            // 附图URLs
            if (product.getImages() != null && !product.getImages().isEmpty()) {
                List<String> imageUrls = new ArrayList<>();
                for (String imageName : product.getImages()) {
                    String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/images/")
                        .path(imageName)
                        .toUriString();
                    imageUrls.add(imageUrl);
                }
                product.setImages(imageUrls);
            }
        }
        return product;
    }
    
    // 为返回的产品列表添加完整图片URL
    private List<Product> enrichProductsWithImageUrls(List<Product> products) {
        if (products != null) {
            for (Product product : products) {
                enrichProductWithImageUrls(product);
            }
        }
        return products;
    }
    
    @GetMapping("/products")
    public ApiResponse<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ApiResponse.success(enrichProductsWithImageUrls(products));
    }
    
    @GetMapping("/products/hot")
    public ApiResponse<List<Product>> getHotProducts() {
        List<Product> products = productService.getHotProducts();
        return ApiResponse.success(enrichProductsWithImageUrls(products));
    }
    
    @GetMapping("/products/{id}")
    public ApiResponse<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ApiResponse.success(enrichProductWithImageUrls(product));
    }
    
    @GetMapping("/admin/products")
    public ApiResponse<List<Product>> getAdminProducts() {
        List<Product> products = productService.getAllProducts();
        return ApiResponse.success(enrichProductsWithImageUrls(products));
    }
    
    @PostMapping("/admin/products")
    public ApiResponse<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("isHot") Boolean isHot,
            @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
            @RequestParam(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles
    ) throws IOException {
        Product product = productService.createProduct(name, category, description, price, isHot, mainFile, additionalFiles);
        return ApiResponse.success(enrichProductWithImageUrls(product));
    }
    
    @PutMapping("/admin/products/{id}")
    public ApiResponse<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("isHot") Boolean isHot,
            @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
            @RequestParam(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles
    ) throws IOException {
        Product product = productService.updateProduct(id, name, category, description, price, isHot, mainFile, additionalFiles);
        return ApiResponse.success(enrichProductWithImageUrls(product));
    }
    
    @PutMapping("/admin/products/{id}/images")
    public ApiResponse<Product> updateProductWithImageNames(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("isHot") Boolean isHot,
            @RequestParam(value = "image", required = false) String mainImageName,
            @RequestParam(value = "images", required = false) List<String> additionalImageNames
    ) {
        Product product = productService.updateProductWithImageNames(id, name, category, description, price, isHot, mainImageName, additionalImageNames);
        return ApiResponse.success(enrichProductWithImageUrls(product));
    }
    
    @DeleteMapping("/admin/products/{id}")
    public ApiResponse<?> deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        return ApiResponse.success(data);
    }
} 