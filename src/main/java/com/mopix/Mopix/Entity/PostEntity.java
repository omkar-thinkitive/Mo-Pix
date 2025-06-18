package com.mopix.Mopix.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class PostEntity extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private String title;
    private String fileKey;

    private Long viewsCount;
    private Long likeCount;
    private Long shareCount;
    private Long commentCount;
    private boolean deleted = false;

}
