package com.mopix.Mopix.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts_share_recipients")
public class PostShareRecipientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_share_id", nullable = false)
    private PostShareEntity postShare;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserEntity recipient;

    private boolean isSeen = false;

    private LocalDateTime seenAt;

}
