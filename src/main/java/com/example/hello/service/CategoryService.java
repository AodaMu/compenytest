package com.example.hello.service;

import com.example.hello.dto.CategoryDTO;
import com.example.hello.model.Category;
import com.example.hello.model.Product;
import com.example.hello.repository.CategoryRepository;
import com.example.hello.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * 初始化时从产品表中导入已有的分类
     */
    @PostConstruct
    public void init() {
        logger.info("CategoryService初始化，开始导入已有分类");
        try {
            // 从产品表中提取所有唯一分类，并确保它们存在于分类表中
            Set<String> existingCategories = productRepository.findAll().stream()
                    .map(Product::getCategory)
                    .filter(category -> category != null && !category.trim().isEmpty())
                    .collect(Collectors.toSet());
                    
            logger.info("从产品中提取到{}个已有分类: {}", existingCategories.size(), existingCategories);
            
            for (String categoryName : existingCategories) {
                if (!categoryRepository.existsByName(categoryName)) {
                    logger.info("创建新分类: {}", categoryName);
                    Category category = Category.builder()
                            .name(categoryName)
                            .build();
                    Category savedCategory = categoryRepository.save(category);
                    logger.info("新分类已保存: {}", savedCategory);
                } else {
                    logger.info("分类已存在，跳过: {}", categoryName);
                }
            }
            logger.info("分类导入完成");
        } catch (Exception e) {
            logger.error("分类初始化错误", e);
        }
    }
    
    /**
     * 获取所有产品分类列表
     */
    public List<CategoryDTO> getAllCategories() {
        logger.info("查询所有分类");
        List<Category> categories = categoryRepository.findAll();
        logger.info("查询到{}个分类", categories.size());
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 添加新的产品分类
     */
    @Transactional
    public CategoryDTO addCategory(String categoryName) {
        logger.info("尝试添加新分类: {}", categoryName);
        
        // 检查分类是否已存在
        if (categoryRepository.existsByName(categoryName)) {
            logger.warn("分类名称已存在: {}", categoryName);
            throw new RuntimeException("分类名称已存在");
        }
        
        try {
            // 创建并保存新分类
            Category category = Category.builder()
                    .name(categoryName)
                    .build();
            
            logger.info("保存分类: {}", category);
            Category savedCategory = categoryRepository.save(category);
            logger.info("分类已保存: {} (ID: {})", savedCategory.getName(), savedCategory.getId());
            return convertToDTO(savedCategory);
        } catch (Exception e) {
            logger.error("保存分类失败", e);
            throw new RuntimeException("保存分类失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 删除产品分类（仅在没有产品使用该分类时允许删除）
     */
    @Transactional
    public void deleteCategory(Integer categoryId) {
        logger.info("尝试删除分类，ID: {}", categoryId);
        
        // 检查分类是否存在
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.warn("分类不存在，ID: {}", categoryId);
                    return new RuntimeException("分类不存在");
                });
        
        // 检查是否有产品使用此分类
        long count = productRepository.findAll().stream()
                .filter(product -> product.getCategory() != null && 
                       product.getCategory().equals(category.getName()))
                .count();
                
        if (count > 0) {
            logger.warn("无法删除分类 {}，因为有{}个产品正在使用它", category.getName(), count);
            throw new RuntimeException("无法删除此分类，因为有产品正在使用它");
        }
        
        // 删除分类
        logger.info("删除分类: {} (ID: {})", category.getName(), category.getId());
        categoryRepository.deleteById(categoryId);
        logger.info("分类删除成功");
    }
    
    /**
     * 将实体对象转换为DTO
     */
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
        logger.debug("转换Category到DTO: {} -> {}", category, dto);
        return dto;
    }
} 