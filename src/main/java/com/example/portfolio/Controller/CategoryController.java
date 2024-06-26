package com.example.portfolio.Controller;

import com.example.portfolio.DTO.Category.CreateCategoryDto;
import com.example.portfolio.DTO.Category.DeleteCategoryDto;
import com.example.portfolio.DTO.Category.UpdateCategoryDto;
import com.example.portfolio.Domain.Category;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Category.CATEGORY_IS_ALREADY_CREATED;
import com.example.portfolio.Exception.Category.NO_MATCHING_CATEGORY_WITH_ID;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.Exception.Like.ALREADY_LIKED;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Service.CategoryService;
import com.example.portfolio.response.Category.CategoryResponseDto;
import com.example.portfolio.response.Category.findAllCategoryListResponse;
import com.example.portfolio.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Tag(name = "카테고리 API", description = "카테고리 API입니다")
@RequestMapping("/category")
public class CategoryController {


    SuccessResponse successResponse = new SuccessResponse();

    @Autowired
    CategoryService categoryService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Operation(summary = "카테고리 모두 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = findAllCategoryListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/list")
    public ResponseEntity<?> findAllCategoryList () {
        List<Category> categories = categoryService.findAllCategoryList();

        findAllCategoryListResponse findAllCategoryListResponse = new findAllCategoryListResponse();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto(category);
            categoryResponseDtoList.add(categoryResponseDto);
        }
        findAllCategoryListResponse.setCategoryResponseDtoList(categoryResponseDtoList);

        return ResponseEntity.ok(findAllCategoryListResponse);
    }

    @Operation(summary = "카테고리 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "해당 이름으로 이미 만들어진 카테고리가 존재하는 경우",
                    content = {@Content(schema = @Schema(implementation = CATEGORY_IS_ALREADY_CREATED.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> createCategory (@RequestHeader("Authorization") String token, @RequestBody CreateCategoryDto createCategoryDto) {
        User user = jwtTokenProvider.validateToken(token);
        Category category = categoryService.createCategory(createCategoryDto);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(category);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @Operation(summary = "카테고리 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "해당 이름으로 이미 만들어진 카테고리가 존재하는 경우",
                    content = {@Content(schema = @Schema(implementation = CATEGORY_IS_ALREADY_CREATED.class))}),
            @ApiResponse(responseCode = "401", description = "해당 ID를 가진 카테고리가 존재하지 않는 경우",
                    content = {@Content(schema = @Schema(implementation = NO_MATCHING_CATEGORY_WITH_ID.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PutMapping("/update")
    public ResponseEntity<CategoryResponseDto> updateCategory (@RequestHeader("Authorization") String token, @RequestBody UpdateCategoryDto updateCategoryDto) {
        User user = jwtTokenProvider.validateToken(token);
        Category category = categoryService.updateCategory(updateCategoryDto);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(category);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @Operation(summary = "카테고리 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory (@RequestHeader("Authorization") String token, @RequestBody DeleteCategoryDto deleteCategoryDto) {
        User user = jwtTokenProvider.validateToken(token);
        categoryService.deleteCategory(deleteCategoryDto);
        return ResponseEntity.ok(successResponse);
    }


}
