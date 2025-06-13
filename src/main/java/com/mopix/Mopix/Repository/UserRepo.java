package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    @Query("""
            select u from UserEntity u
            where u.userName = :username
            """)
    UserEntity findByUserName(@Param("username") String username);
}
