package com.mopix.Mopix.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_hashtag_usage", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "hashtag_id"})
})
public class UserHashtagUsageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashtag;

    @Column(name = "usage_count", nullable = false)
    private int usageCount = 1;
}
