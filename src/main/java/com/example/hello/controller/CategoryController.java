package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.dto.CategoryDTO;
import com.example.hello.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    
    /**
     * 获取所有产品分类列表（公开接口）
     */
    @GetMapping("/categories")
    public ApiResponse<List<CategoryDTO>> getAllCategories() {
        logger.info("公开接口请求：获取所有产品分类");
        List<CategoryDTO> categories = categoryService.getAllCategories();
        logger.info("返回{}个分类", categories.size());
        return ApiResponse.success(categories);
    }
    
    /**
     * 管理员获取所有产品分类列表
     */
    @GetMapping("/admin/categories")
    public ApiResponse<List<CategoryDTO>> getAdminCategories() {
        logger.info("管理员请求：获取所有产品分类");
        List<CategoryDTO> categories = categoryService.getAllCategories();
        logger.info("返回{}个分类", categories.size());
        return ApiResponse.success(categories);
    }
    
    /**
     * 添加新的产品分类
     */
    @PostMapping("/admin/categories")
    public ApiResponse<?> addCategory(@RequestBody Map<String, String> request) {
        String categoryName = request.get("name");
        logger.info("管理员请求：添加新产品分类：{}", categoryName);
        
        if (categoryName == null || categoryName.trim().isEmpty()) {
            logger.warn("分类名称为空");
            return ApiResponse.error(400, "分类名称不能为空");
        }
        
        try {
            CategoryDTO category = categoryService.addCategory(categoryName);
            logger.info("分类添加成功：{} (ID: {})", category.getName(), category.getId());
            return ApiResponse.success(category);
        } catch (Exception e) {
            logger.error("添加分类失败", e);
            return ApiResponse.error(500, "添加分类失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除产品分类
     */
    @DeleteMapping("/admin/categories/{id}")
    public ApiResponse<?> deleteCategory(@PathVariable Integer id) {
        logger.info("管理员请求：删除产品分类，ID：{}", id);
        try {
            categoryService.deleteCategory(id);
            logger.info("分类删除成功，ID：{}", id);
            return ApiResponse.success(null, "分类删除成功");
        } catch (RuntimeException e) {
            logger.warn("删除分类失败：{}", e.getMessage());
            return ApiResponse.error(400, e.getMessage());
        }
    }
} 