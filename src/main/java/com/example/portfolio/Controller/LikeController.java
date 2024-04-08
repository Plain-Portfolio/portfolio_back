package com.example.portfolio.Controller;

import com.example.portfolio.DTO.Like.AddLikeDto;
import com.example.portfolio.DTO.Like.CancelLikeDto;
import com.example.portfolio.Domain.Like;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.Exception.Like.ALREADY_LIKED;
import com.example.portfolio.Exception.Like.PROJECT_IS_NOT_FOUND;
import com.example.portfolio.Exception.Like.PROJECT_NOT_YEY_LIKED;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Service.LikeService;
import com.example.portfolio.response.Like.LikeProjectListRes;
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
@RequestMapping("/like")

@Tag(name = "좋아요 API", description = "좋아요 API입니다")
public class LikeController {


    SuccessResponse successResponse = new SuccessResponse();

    @Autowired
    LikeService likeService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "프로젝트 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "401", description = "입력된 프로젝트 ID를 가진 프로젝트가 존재하지 않을 경우",
                    content = {@Content(schema = @Schema(implementation = PROJECT_IS_NOT_FOUND.class))}),
            @ApiResponse(responseCode = "402", description = "이미 좋아요한 유저일 경우",
                    content = {@Content(schema = @Schema(implementation = ALREADY_LIKED.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addLike (@RequestHeader("Authorization") String token, @RequestBody AddLikeDto addLikeDto) {
        User user = jwtTokenProvider.validateToken(token);
        Like like = likeService.addLike(user, addLikeDto);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "프로젝트 좋아요 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "좋아요하지 않은 프로젝트를 취소하려 할 경우",
                    content = {@Content(schema = @Schema(implementation = PROJECT_NOT_YEY_LIKED.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelLike (@RequestHeader("Authorization") String token, @RequestBody CancelLikeDto cancelLikeDto)  {
        User user = jwtTokenProvider.validateToken(token);
        likeService.cancelLike(user.getId(), cancelLikeDto);
        return ResponseEntity.ok(successResponse);
    }

//    @Operation(summary = "좋아요한 프로젝트 리스트 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공",
//                    content = {@Content(schema = @Schema(implementation = SuccessResponse.class))}),
//            @ApiResponse(responseCode = "500", description = "서버 에러",
//                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
//    })
//    @GetMapping("/my/projects")
//    public ResponseEntity<?> likeProjectList (@RequestHeader("Authorization") String token) {
//        User user = jwtTokenProvider.validateToken(token);
//        List<Project> findProjects = likeService.likeProjectList(user);
//        LikeProjectListRes response = new LikeProjectListRes();
//        for (Project project: findProjects) {
//
//        }
//
//        response.setLikeProject(findProjects);
//        return ResponseEntity.ok(findProjects);
//    }
}
