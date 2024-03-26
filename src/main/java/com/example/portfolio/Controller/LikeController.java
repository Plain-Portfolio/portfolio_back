package com.example.portfolio.Controller;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Like;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Dto.Like.AddLikeDto;
import com.example.portfolio.Dto.Like.CancelLikeDto;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/like")

@Tag(name = "좋아요 API", description = "좋아요 API입니다")
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "프로젝트 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = Like.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/add")
    public ResponseEntity<?> addLike (@RequestHeader("Authorization") String token, @RequestBody AddLikeDto addLikeDto) {
        User user = jwtTokenProvider.validateToken(token);
        Like like = likeService.addLike(user, addLikeDto);
        return ResponseEntity.ok(like);
    }

    @Operation(summary = "프로젝트 좋아요 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelLike (@RequestHeader("Authorization") String token, @RequestBody CancelLikeDto cancelLikeDto)  {
        User user = jwtTokenProvider.validateToken(token);
        likeService.cancelLike(user.getId(), cancelLikeDto);
        return ResponseEntity.ok("success");
    }
}
