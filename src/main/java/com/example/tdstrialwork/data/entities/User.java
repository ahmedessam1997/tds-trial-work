package com.example.tdstrialwork.data.entities;

import com.example.tdstrialwork.helpers.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

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
@ToString(exclude = "devices")
@EqualsAndHashCode(exclude = "devices")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Length(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH, message = "Requested name length is out of range!")
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
    @Column(nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Status status;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.MERGE
    )
    Set<Device> devices;

    public void addDevice(Device device) {
        this.devices.add(device);
        device.setUser(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.setUser(null);
    }

    public enum Status {
        INITIAL("initial"),
        INACTIVE("inactive"),
        ACTIVE("active");

        private final String id;

        Status(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }
}
