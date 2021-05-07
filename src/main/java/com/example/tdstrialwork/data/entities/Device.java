package com.example.tdstrialwork.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
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
    private User user;

    @OneToMany(
            mappedBy = "device",
            cascade = CascadeType.ALL
    )
    private Set<ESim> eSims = new HashSet<>();

    public void addESim(ESim eSim) {
        this.eSims.add(eSim);
        eSim.setDevice(this);
    }

    public void removeESim(ESim eSim) {
        this.eSims.remove(eSim);
        eSim.setDevice(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device )) return false;
        return deviceId != null && deviceId.equals(((Device) o).getDeviceId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
