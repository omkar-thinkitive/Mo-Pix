package com.mopix.Mopix.Dtos.Response;

import com.mopix.Mopix.Dtos.FeedDTO;
import com.mopix.Mopix.Dtos.dto.CommentDTO;
import com.mopix.Mopix.Dtos.dto.HashTagDTO;
import com.mopix.Mopix.Dtos.dto.PostDTO;
import com.mopix.Mopix.Dtos.dto.UserDTO;
import com.mopix.Mopix.Entity.CommentEntity;
import com.mopix.Mopix.Entity.HashTag;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class UserFeedResponse {

//    private String mediaUrl;
//    Page<PostEntity> postEntities;
//    List <CommentEntity> comments;
//    private Long viewsCount;
//    List<HashTag> hashTags;
//    private UserEntity user;

//    private String mediaUrl;
//    private PostEntity post;
//    private CommentEntity comment;
//    List<HashTag> hashTags;
//    private UserEntity user;

    private String mediaUrl;
    private PostDTO post;
    private CommentDTO comment;
    private List<HashTagDTO> hashTags;
    private UserDTO user;
    private List<FeedDTO> feedDTOS;

}
