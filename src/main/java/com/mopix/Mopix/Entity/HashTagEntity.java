package com.mopix.Mopix.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hashtags")
public class HashTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "usage_count")
    private Long usageCount;

    @Column(name = "trend_score")
    private double trendScore;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @ManyToMany(mappedBy = "hashtags")
    private Set<PostEntity> posts = new HashSet<>();

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof HashTagEntity)) return false;
//        HashTagEntity that = (HashTagEntity) o;
//        return Objects.equals(name, that.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }
}

