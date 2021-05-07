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
    private Long eSimId;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String ICCID;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String IMSI;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String activationCode;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private UUID eId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ESim )) return false;
        return eSimId != null && eSimId.equals(((ESim) o).getESimId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
