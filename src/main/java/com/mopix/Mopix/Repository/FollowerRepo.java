package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.FollowerEntity;
import com.mopix.Mopix.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepo extends JpaRepository<FollowerEntity,Long> {

    @Query("""
            select u.follower from FollowerEntity u
            where u.following.id = :id
            """)
    Page<UserEntity> findUsersEntity(@Param("id") Long id, Pageable pageable);

    @Query("""
            select u.following from FollowerEntity u
            where u.follower.id = :id
            """)
    Page<UserEntity> findFollowerUsersEntity(@Param("id") Long id, Pageable pageable);
}
