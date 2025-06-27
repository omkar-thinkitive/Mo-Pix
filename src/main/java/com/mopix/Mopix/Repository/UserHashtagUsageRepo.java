package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.HashTag;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Entity.UserHashtagUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHashtagUsageRepo extends JpaRepository<UserHashtagUsageEntity,Long> {

    Optional<UserHashtagUsageEntity> findByUserAndHashtag(UserEntity user, HashTag hashtag);

}
