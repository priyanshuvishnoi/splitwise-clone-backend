package dev.priyanshuvishnoi.splitwiseclonebackend.User.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String currency;

    @Column
    private String language;

    @Column
    private String timezone;

    @Column
    private String image;
}
