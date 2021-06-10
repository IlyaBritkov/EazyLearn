package com.eazylearn.controller;


import com.eazylearn.dto.request.CategoryCreateRequestDTO;
import com.eazylearn.dto.request.CategoryUpdateRequestDTO;
import com.eazylearn.dto.response.CategoryResponseDTO;
import com.eazylearn.exception_handling.exception.EntityAlreadyExistsException;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;
import com.eazylearn.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategories() {
        List<CategoryResponseDTO> allCategories = categoryService.findAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findCategoryById(@PathVariable("id") Long categoryId) throws EntityDoesNotExistException {
        CategoryResponseDTO categoryResponseDTO = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryCreateRequestDTO categoryCreateRequestDTO) throws EntityAlreadyExistsException {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryCreateRequestDTO);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategoryById(@PathVariable("id") Long categoryId,
                                                                  @RequestBody CategoryUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategoryById(categoryId, updateDto);

        return ResponseEntity.ok(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Long categoryId, @RequestParam boolean isDeleteAllCardsInCategory) throws EntityDoesNotExistException {
        categoryService.deleteCategoryById(categoryId, isDeleteAllCardsInCategory);

        return ResponseEntity.noContent().build();
    }
}
