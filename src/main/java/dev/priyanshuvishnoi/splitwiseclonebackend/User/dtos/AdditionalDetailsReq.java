package dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public record AdditionalDetailsReq(
        int userId,
        @Pattern(regexp = "^(\\+\\d{1,3})?\\s?\\d{10,15}$", message = "Invalid mobile number") String phone,
        @NotBlank String currency, String language, String timezone, MultipartFile userImage) {
}
