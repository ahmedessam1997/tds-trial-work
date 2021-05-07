package com.example.tdstrialwork.data.entities;

import com.example.tdstrialwork.helpers.Constants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @NotBlank
    @Length(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH, message = "Requested name length is out of range!")
    @Pattern(regexp = Constants.NAME_REGEX, message = "Requested name is not valid!")
    @Column(nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Email(message = "Please provide a valid email address!")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address!")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String password;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private boolean active = false;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Set<Device> devices = new HashSet<>();

    public void addDevice(Device device) {
        this.devices.add(device);
        device.setUser(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.setUser(null);
    }

    public void setPassword(final String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
