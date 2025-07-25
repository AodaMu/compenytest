package com.example.hello.service;

import com.example.hello.model.Product;
import com.example.hello.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ImageStorageService imageStorageService;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<Product> getHotProducts() {
        return productRepository.findByIsHotTrue();
    }
    
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("产品不存在"));
    }
    
    public Product createProduct(String name, String category, String description, String price, Boolean isHot, 
                                MultipartFile mainFile, List<MultipartFile> additionalFiles) throws IOException {
        
        // 保存主图
        String mainImageName = null;
        if (mainFile != null && !mainFile.isEmpty()) {
            mainImageName = imageStorageService.storeImage(mainFile);
        }
        
        // 保存附加图片，最多5个
        List<String> imageNames = new ArrayList<>();
        if (additionalFiles != null) {
            int count = 0;
            for (MultipartFile file : additionalFiles) {
                if (file != null && !file.isEmpty() && count < 5) {
                    String imageName = imageStorageService.storeImage(file);
                    imageNames.add(imageName);
                    count++;
                }
            }
        }
        
        Product product = Product.builder()
                .name(name)
                .category(category)
                .description(description)
                .price(price)
                .isHot(isHot)
                .image(mainImageName)
                .images(imageNames)
                .build();
                
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, String name, String category, String description, String price, Boolean isHot,
                                MultipartFile mainFile, List<MultipartFile> additionalFiles) throws IOException {
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("产品不存在"));
        
        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);
        product.setIsHot(isHot);
        
        // 更新主图
        if (mainFile != null && !mainFile.isEmpty()) {
            // 删除旧图片
            if (product.getImage() != null) {
                imageStorageService.deleteImage(product.getImage());
            }
            String mainImageName = imageStorageService.storeImage(mainFile);
            product.setImage(mainImageName);
        }
        
        // 更新附加图片
        if (additionalFiles != null && !additionalFiles.isEmpty()) {
            // 删除旧图片
            if (product.getImages() != null) {
                for (String imageName : product.getImages()) {
                    imageStorageService.deleteImage(imageName);
                }
            }
            
            // 保存新图片，最多5个
            List<String> imageNames = new ArrayList<>();
            int count = 0;
            for (MultipartFile file : additionalFiles) {
                if (file != null && !file.isEmpty() && count < 5) {
                    String imageName = imageStorageService.storeImage(file);
                    imageNames.add(imageName);
                    count++;
                }
            }
            product.setImages(imageNames);
                }
        
        return productRepository.save(product);
    }
    
    // 支持直接使用图片名称更新产品
    public Product updateProductWithImageNames(Long id, String name, String category, String description, String price, Boolean isHot,
                                String mainImageName, List<String> additionalImageNames) {
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("产品不存在"));
        
        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);
        product.setIsHot(isHot);
        
        // 如果提供了新的主图名称，则更新
        if (mainImageName != null && !mainImageName.isEmpty()) {
            product.setImage(mainImageName);
        }
        
        // 如果提供了新的附图名称列表，则更新
        if (additionalImageNames != null && !additionalImageNames.isEmpty()) {
            // 最多保存5个
            List<String> imageNames = additionalImageNames.size() > 5 
                ? additionalImageNames.subList(0, 5) 
                : additionalImageNames;
            product.setImages(imageNames);
        }
        
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("产品不存在"));
        
        // 删除主图
        if (product.getImage() != null) {
            imageStorageService.deleteImage(product.getImage());
        }
        
        // 删除附加图片
        if (product.getImages() != null) {
            for (String imageName : product.getImages()) {
                imageStorageService.deleteImage(imageName);
            }
        }
        
        // 删除数据
        productRepository.deleteById(id);
    }
} 