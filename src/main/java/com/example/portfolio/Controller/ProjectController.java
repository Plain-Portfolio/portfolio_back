package com.example.portfolio.Controller;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Dto.Like.CancelLikeDto;
import com.example.portfolio.Dto.Project.CategorySearchDto;
import com.example.portfolio.Dto.Project.CreateProjectDto;
import com.example.portfolio.Dto.Project.DeleteProjectDto;
import com.example.portfolio.Dto.Project.UpdateProjectDto;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.Exception.User.EMAIL_IS_DUPLICATED;
import com.example.portfolio.Exception.User.EMAIL_IS_VALID;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Request.GetProjectListRequest;
import com.example.portfolio.Service.ProjectService;
import com.example.portfolio.response.Project.GetProjectDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/project")
@Tag(name = "프로젝트 API", description = "프로젝트 API입니다")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "프로젝트 디테일 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = GetProjectDetailResponse.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<GetProjectDetailResponse> getProjectDetails(@PathVariable(name = "projectId") String projectId) {
        GetProjectDetailResponse response = projectService.getProjectDetail(projectId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "소유자 프로젝트 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = GetProjectListRequest.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/{userId}/projects")
    public ResponseEntity<?> getProjectList(@PathVariable(name = "userId") String userId) {
        List<Project> findProjects = projectService.getProjectList(userId);
        return ResponseEntity.ok(findProjects);
    }

    @Operation(summary = "프로젝트 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestHeader("Authorization") String token, @RequestBody CreateProjectDto createProjectDto) throws Exception {
        User user = jwtTokenProvider.validateToken(token);
        Project project = projectService.createProject(user.getId(), createProjectDto);
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "프로젝트 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateProject(@RequestHeader("Authorization") String token, @RequestBody UpdateProjectDto updateProjectDto) throws Exception {
        jwtTokenProvider.validateToken(token);
        Project project = projectService.updateProject(updateProjectDto);
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "프로젝트 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProject (@RequestHeader("Authorization") String token, @RequestBody DeleteProjectDto deleteProjectDto)  {
        User user = jwtTokenProvider.validateToken(token);
        projectService.deleteProject(user.getId(), deleteProjectDto);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "카테고리로 프로젝트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CategorySearchDto.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @GetMapping("/categorySearch")
    public ResponseEntity<List<Project>> categorySearch(
            @Parameter(description = "조회할 카테고리 이름", schema = @Schema(type = "name", example = "spring"))
            @RequestParam(name = "name", value = "name") List<String> names) {
        List<Project> projects = projectService.categorySearch(names);

        return ResponseEntity.ok(projects);
    }
}
