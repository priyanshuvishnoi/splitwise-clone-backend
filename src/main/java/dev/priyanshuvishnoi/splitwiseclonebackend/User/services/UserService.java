package dev.priyanshuvishnoi.splitwiseclonebackend.User.services;

import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.AdditionalDetailsReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.CreateUserReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.dtos.GetUserImageReq;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.entities.UserEntity;
import dev.priyanshuvishnoi.splitwiseclonebackend.User.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final String uploadDirectoryPath = System.getProperty("user.home") + File.separator + "Pictures";

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public boolean register(CreateUserReq reqDto) {
        boolean userExists = userRepo.existsByEmail(reqDto.getEmail());

        if (userExists) {
            return false;
        }

        UserEntity user = modelMapper.map(reqDto, UserEntity.class);
        UserEntity savedUser = userRepo.save(user);
        return savedUser != null;
    }

    @Override
    public boolean updateAdditionalDetails(AdditionalDetailsReq req) throws IOException {
        Optional<UserEntity> userOptional = userRepo.findById(req.userId());

        if (userOptional.isEmpty()) {
            return false;
        }

        UserEntity user = userOptional.get();

        if (!req.userImage().isEmpty()) {
            File directory = new File(uploadDirectoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String filePath = uploadDirectoryPath + File.separator + System.currentTimeMillis();
            req.userImage().transferTo(new File(filePath));
            user.setImage(filePath);
        }

        user.setPhone(req.phone());
        user.setCurrency(req.currency());
        user.setLanguage(req.language());
        user.setTimezone(req.timezone());

        userRepo.save(user);
        return true;
    }

    @Override
    public Resource getUserImage(GetUserImageReq req) {
        try {
            Optional<UserEntity> user = userRepo.findById(req.userId());

            if (user.isEmpty()) {
                return null;
            }

            return new FileSystemResource(user.get().getImage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
