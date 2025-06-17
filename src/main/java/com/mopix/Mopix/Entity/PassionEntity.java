package com.mopix.Mopix.Entity;

import com.mopix.Mopix.Dtos.enums.Gender;
import com.mopix.Mopix.Dtos.enums.Passion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passion")
public class PassionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private Passion passion;

    public PassionEntity(UserEntity userEntity, Passion passion) {
        this.userEntity = userEntity;
        this.passion = passion;
    }
}
