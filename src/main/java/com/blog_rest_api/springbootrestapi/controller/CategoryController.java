package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.CategoryDto;
import com.blog_rest_api.springbootrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "CRUD REST APIs For Category Resource"
)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // adding category
    @Operation(
            summary = "Create Category REST API",
            description = "Used to create a category, can only be done by admin"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
    // getting category by Id
    @Operation(
            summary = "Get Category By Id REST API",
            description = "Used to get a particular category by id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id") long categoryId){
        CategoryDto category = categoryService.getCategory(categoryId);

        return ResponseEntity.ok(category);
    }

    @Operation(
            summary = "Get All Categories REST API",
            description = "Used to get all categories from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Update A Particular Category REST API",
            description = "Used to update a particular category from the database, can only be performed by admin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable(name = "id")long categoryId){

        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @Operation(
            summary = "Delete A Particular Category REST API",
            description = "Used to delete a particular category from the database, can only be performed by admin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id")long categoryId){
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok("category deleted successfully");
    }

}
