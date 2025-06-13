package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.CommentEntity;
import com.mopix.Mopix.Services.CommentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity, Long> {

}
