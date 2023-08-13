package dev.priyanshuvishnoi.splitwiseclonebackend.User.services;

import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.AdditionalDetailsReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.CreateUserReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.GetUserImageReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.entities.UserEntity;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    List<UserEntity> getAllUsers();

    boolean register(CreateUserReq reqDto);

    boolean updateAdditionalDetails(AdditionalDetailsReq req) throws IOException;

    Resource getUserImage(GetUserImageReq req);
}
