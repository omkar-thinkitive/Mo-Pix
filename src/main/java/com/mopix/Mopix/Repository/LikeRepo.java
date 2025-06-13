package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<LikeEntity,Long> {
}
