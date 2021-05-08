package com.example.tdstrialwork.data.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Data
@ToString(exclude = "eSims")
@EqualsAndHashCode(exclude = "eSims")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID deviceUniqueId = UUID.randomUUID();

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String type;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String os;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String metaTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "device",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.MERGE
    )
    Set<ESim> eSims = new HashSet<>();

    public void addESim(ESim eSim) {
        this.eSims.add(eSim);
        eSim.setDevice(this);
    }

    public void removeESim(ESim eSim) {
        this.eSims.remove(eSim);
        eSim.setDevice(null);
    }

    @PrePersist
    private void preSet() {
        this.setDeviceUniqueId(UUID.randomUUID());
    }

    public String getDeviceUniqueId() {
        return this.deviceUniqueId.toString();
    }
}
