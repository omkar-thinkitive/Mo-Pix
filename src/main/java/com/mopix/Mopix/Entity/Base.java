package com.mopix.Mopix.Entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Base {

    @CreatedBy
    private String createdBy = "";

    @LastModifiedBy
    private String modifiedBy = "";

    @CreatedDate
    @Builder.Default
    private Instant createdAt = LocalDateTime.now().toInstant(ZoneOffset.UTC);

    @LastModifiedDate
    @Builder.Default
    private Instant updatedAt = LocalDateTime.now().toInstant(ZoneOffset.UTC);
}


