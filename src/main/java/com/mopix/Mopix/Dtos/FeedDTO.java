package com.mopix.Mopix.Dtos;

import com.mopix.Mopix.Entity.CommentEntity;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedDTO {
    private PostEntity post;
    private CommentEntity comment;
    private UserEntity user;


}
