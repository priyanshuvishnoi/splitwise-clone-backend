package dev.priyanshuvishnoi.splitwiseclonebackend.User.controllers;

import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.AdditionalDetailsReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.CreateUserReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.GetUserImageReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.entities.UserEntity;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.services.UserService;
import dev.priyanshuvishnoi.splitwiseclonebackend.utils.ApiResponse;
import dev.priyanshuvishnoi.splitwiseclonebackend.utils.Msg;
import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<UserEntity>>> getAllUsers() {

        List<UserEntity> users = userService.getAllUsers();

        ApiResponse<List<UserEntity>> res = new ApiResponse<>(true, users, new Msg(200, "Ok"));
        return ResponseEntity.ok(res);
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<Boolean>> register(@Valid @RequestBody CreateUserReq req) {
        boolean result = userService.register(req);

        if (result) {
            HttpStatus status = HttpStatus.CREATED;
            ApiResponse<Boolean> resBody = new ApiResponse<>(true, true, new Msg(status.value(), "USER CREATED"));

            return new ResponseEntity<>(resBody, status);
        } else {
            HttpStatus status = HttpStatus.CONFLICT;
            ApiResponse<Boolean> resBody = new ApiResponse<>(true, false, new Msg(status.value(), "USER CREATED"));

            return new ResponseEntity<>(resBody, status);
        }
    }

    @PostMapping("updateAdditionalDetails")
    public ResponseEntity<ApiResponse<Boolean>> updateAdditionalDetails(@ModelAttribute AdditionalDetailsReq req) {
        try {
            boolean result = userService.updateAdditionalDetails(req);
            ApiResponse<Boolean> res = new ApiResponse<>(result, result, null);
            return new ResponseEntity(res, result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, new Msg(500, "Some error occured!")),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @PostMapping("userImage")
    public ResponseEntity<Resource> getUserImage(@RequestBody GetUserImageReq req) {
        Resource resource = userService.getUserImage(req);

        if (resource == null || !resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(resource.getFile());

            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + resource.getFilename() + "\""
            ).header(HttpHeaders.CONTENT_TYPE, mimeType).body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
