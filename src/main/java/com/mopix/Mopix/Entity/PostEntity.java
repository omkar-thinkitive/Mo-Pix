package com.mopix.Mopix.Entity;

import com.mopix.Mopix.Dtos.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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

    private UUID postUUID;
    private String caption;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mediaUrl;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private String title;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "post_hashtags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<HashTagEntity> hashtags;

    private Long viewsCount;
    private Long likeCount;
    private Long shareCount;
    private Long commentCount;
    private boolean isNFT = false;
    private boolean deleted = false;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof PostEntity)) return false;
//        PostEntity that = (PostEntity) o;
//        return Objects.equals(postUUID, that.postUUID);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(postUUID);
//    }

}
