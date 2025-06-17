package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.CommentEntity;
import com.mopix.Mopix.Services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity, Long> {

    @Query("""
            select c from CommentEntity c
            where c.deleted = false
              AND c.userEntity.id = :id
            """)
    Page<CommentEntity> findAllByPostEntity(@Param("id") Long id, Pageable pageable);
}
