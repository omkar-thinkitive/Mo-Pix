package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.PostShareEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostShareRepo extends JpaRepository<PostShareEntity,Long> {
}
