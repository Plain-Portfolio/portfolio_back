package com.example.portfolio.Controller;

import com.example.portfolio.Dto.User.LoginDto;
import com.example.portfolio.Dto.User.SignUpDto;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.Exception.User.EMAIL_IS_DUPLICATED;
import com.example.portfolio.Exception.User.EMAIL_IS_VALID;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.Service.UserService;
import com.example.portfolio.response.User.LoginResponse;
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

@Controller
@Tag(name = "유저 API", description = "유저 API입니다")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "[[닉네임 || 이메일]]이 중복되었습니다",
                    content = {@Content(schema = @Schema(implementation = EMAIL_IS_DUPLICATED.class))}),

            @ApiResponse(responseCode = "403", description = "[[비밀번호, 닉네임, 이메일]] 유효성 검사에 실패하였습니다.",
                    content = {@Content(schema = @Schema(implementation = EMAIL_IS_VALID.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody SignUpDto signUpDto) {
        System.out.println(signUpDto);
        User user = userService.signUp(signUpDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401", description = "[[닉네임 || 이메일]]이 중복되었습니다",
                    content = {@Content(schema = @Schema(implementation = EMAIL_IS_DUPLICATED.class))}),

            @ApiResponse(responseCode = "403", description = "[[비밀번호, 닉네임, 이메일]] 유효성 검사에 실패하였습니다.",
                    content = {@Content(schema = @Schema(implementation = EMAIL_IS_VALID.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginDto loginDto) throws Exception {
        LoginResponse loginResponse = userService.login(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

}
