package com.example.portfolio.Controller;

import com.example.portfolio.DTO.User.FindUserList;
import com.example.portfolio.DTO.User.LoginDto;
import com.example.portfolio.DTO.User.SignUpDto;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.Exception.User.EMAIL_IS_DUPLICATED;
import com.example.portfolio.Exception.User.EMAIL_IS_VALID;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.Service.RedisService;
import com.example.portfolio.Service.UserService;
import com.example.portfolio.response.SuccessResponse;
import com.example.portfolio.response.User.*;
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
@Tag(name = "유저 API", description = "유저 API입니다")
@RequestMapping("/user")
public class UserController {

    SuccessResponse successResponse = new SuccessResponse();

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "[[닉네임 || 이메일]]이 중복되었습니다",
                    content = {@Content(schema = @Schema(implementation = EMAIL_IS_DUPLICATED.class))}),

            @ApiResponse(responseCode = "403", description = "[[비밀번호, 닉네임, 이메일]] 유효성 검사에 실패하였습니다.",
                    content = {@Content(schema = @Schema(implementation = EMAIL_IS_VALID.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup (@RequestBody SignUpDto signUpDto) {
        System.out.println(signUpDto);
        User user = userService.signUp(signUpDto);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = LoginResponse.class))}),
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

    @Operation(summary = "유저 모두 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = FindUserList.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/list")
    public ResponseEntity<FindUserList> findUserList (@RequestHeader("Authorization") String token) {
        jwtTokenProvider.validateToken(token);
        List<User> users = userService.findUserList();

        FindUserList findUserList = new FindUserList();
        List<FindUserList.FindUserListUserDto> userList = new ArrayList<>();

        for (User user : users) {
            FindUserList.FindUserListUserDto userDto = new FindUserList.FindUserListUserDto(user);
            userList.add(userDto);
        }

        findUserList.setUserList(userList);

        return ResponseEntity.ok(findUserList);
    }

    @Operation(summary = "구글 소셜 로그인 url 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = GoogleSocialRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/login/google")
    public ResponseEntity<GoogleSocialRes> googleLogin() {
        String url = userService.googleLogin();
        GoogleSocialRes response = new GoogleSocialRes();
        response.setUrl(url);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "구글 소셜 로그인 callback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SocialLoginRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/login/google/callback")
    @ResponseBody
    public ResponseEntity<?> googleLoginCallback(@RequestParam(name = "code")String code) throws Exception {
        SocialLoginRes responseBody = userService.googleLoginCallBack(code);
        return ResponseEntity.ok(responseBody);
    }
}
