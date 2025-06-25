package com.mopix.Mopix.Entity;

import com.mopix.Mopix.Dtos.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Text;

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

    private Long viewsCount;
    private Long likeCount;
    private Long shareCount;
    private Long commentCount;
    private boolean isNFT = false;
    private boolean deleted = false;

}
