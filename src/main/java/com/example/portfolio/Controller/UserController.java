package com.example.portfolio.Controller;

import com.example.portfolio.DTO.User.*;
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
import java.util.Map;

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
    public ResponseEntity<FindUserList> findUserList () {
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

    @Operation(summary = "네이버 소셜 로그인 url 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SocialRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/login/naver")
    public ResponseEntity<SocialRes> naverLogin() {
        System.out.println("???");
        String url = userService.naverLogin();
        SocialRes response = new SocialRes();
        response.setUrl(url);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "naver 소셜 로그인 callback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SocialLoginRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/login/naver/callback")
    @ResponseBody
    public ResponseEntity<?> naverLoginCallback(@RequestBody SocialLoginCallBackDto socialLoginCallBackDto) throws Exception {
        System.out.println("시작");
        SocialLoginRes responseBody = userService.naverLoginCallBack(socialLoginCallBackDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/login/naver/callback")
    public ResponseEntity<?> testNaverCallback(@RequestParam(name = "code") String code) throws Exception {
        SocialLoginRes responseBody = userService.testNaverLoginCallBack(code);
        return ResponseEntity.ok(responseBody);
    }


    @Operation(summary = "카카오 소셜 로그인 url 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SocialRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/login/kakao")
    public ResponseEntity<SocialRes> kakaoLogin() {
        String url = userService.kakaoLogin();
        SocialRes response = new SocialRes();
        response.setUrl(url);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "kakao 소셜 로그인 callback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SocialLoginRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/login/kakao/callback")
    @ResponseBody
    public ResponseEntity<?> kakaoLoginCallback(@RequestBody SocialLoginCallBackDto socialLoginCallBackDto) throws Exception {
        SocialLoginRes responseBody = userService.kakaoLoginCallBack(socialLoginCallBackDto);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "구글 소셜 로그인 url 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SocialRes.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/login/google")
    public ResponseEntity<SocialRes> googleLogin() {
        String url = userService.googleLogin();
        SocialRes response = new SocialRes();
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
    @PostMapping("/login/google/callback")
    @ResponseBody
    public ResponseEntity<?> googleLoginCallback(@RequestBody SocialLoginGoogleDto socialLoginGoogleDto) throws Exception {
        SocialLoginRes responseBody = userService.googleLoginCallBack(socialLoginGoogleDto.getCode());
        return ResponseEntity.ok(responseBody);
    }
}
