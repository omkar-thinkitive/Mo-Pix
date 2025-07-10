package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Dtos.FeedDTO;
import com.mopix.Mopix.Entity.PostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<PostEntity,Long> {

    @Query("SELECT new com.mopix.Mopix.Dtos.FeedDTO(p, c, u) " +
            "FROM PostEntity p " +
            "LEFT JOIN CommentEntity c ON c.postEntity = p " +
            "JOIN p.userEntity u " +
            "WHERE p.userEntity IN :users " +
            "AND p.deleted = false " +
            "AND p.mediaType = com.mopix.Mopix.Dtos.enums.MediaType.VIDEO")
    Page<FeedDTO> findByUser(@Param("users") List<UserEntity> users, Pageable pageable);

    @Query("""
            SELECT p FROM PostEntity p
            WHERE p.postUUID = :uuid
            AND p.deleted = false
            """)
    PostEntity getPostByUUID(@Param("uuid") UUID uuid);

    @Query("""
            SELECT p FROM PostEntity p
            WHERE p.userEntity = :user
            AND p.deleted = false
            """)
    Page<PostEntity> findPostOfUser(@Param("user") UserEntity user, Pageable pageable);
}
