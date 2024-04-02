package com.example.portfolio.Controller;

import com.example.portfolio.DTO.Comment.CreateCommentDto;
import com.example.portfolio.DTO.Comment.DeleteCommentDto;
import com.example.portfolio.DTO.Comment.UpdateCommentDto;
import com.example.portfolio.DTO.User.FindUserList;
import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Service.CommentService;
import com.example.portfolio.response.Comment.CommentListResponseDto;
import com.example.portfolio.response.Comment.CommentResponseDto;
import com.example.portfolio.response.Comment.FindCommentList;
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
@RequestMapping("/comment")
@Tag(name = "댓글 API", description = "댓글 API입니다")
public class CommentController {

    SuccessResponse successResponse = new SuccessResponse();

    @Autowired
    CommentService commentService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "댓글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CommentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> createComment (@RequestHeader("Authorization") String token, @RequestBody CreateCommentDto createCommentDto) {
        User user = jwtTokenProvider.validateToken(token);
        Comment comment = commentService.createComment(user, createCommentDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return ResponseEntity.ok(commentResponseDto);
    }

    @Operation(summary = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CommentResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PutMapping("/update")
    public ResponseEntity<CommentResponseDto> updateComment (@RequestHeader("Authorization") String token, @RequestBody UpdateCommentDto updateCommentDto) {
        User user = jwtTokenProvider.validateToken(token);
        Comment comment = commentService.updateComment(user, updateCommentDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return ResponseEntity.ok(commentResponseDto);
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SuccessResponse.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment (@RequestHeader("Authorization") String token, @RequestBody DeleteCommentDto deleteCommentDto) {
        User user = jwtTokenProvider.validateToken(token);
        commentService.deleteComment(deleteCommentDto);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "댓글 모두 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CommentListResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/list")
    public ResponseEntity<CommentListResponseDto> findCommentList () {
        List<Comment> comments = commentService.findCommentList();
        CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }

        commentListResponseDto.setComments(commentResponseDtoList);
        return ResponseEntity.ok(commentListResponseDto);
    }
}
