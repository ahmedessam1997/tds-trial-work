package com.example.tdstrialwork.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "e_sims")
@Data
public class ESim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String iccid;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String imsi;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String activationCode;

    @Column(unique = true)
    private UUID eId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @PrePersist
    private void preSet() {
        this.setEId(UUID.randomUUID());
    }

    public String getEId() {
        return this.eId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ESim )) return false;
        return id != null && id.equals(((ESim) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
