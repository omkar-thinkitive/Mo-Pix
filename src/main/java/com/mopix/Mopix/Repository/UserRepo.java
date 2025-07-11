package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    @Query("""
            select u from UserEntity u
            where u.userName = :username
            """)
    UserEntity findByUserName(@Param("username") String username);

    @Query("""
            select u from UserEntity u
            where u.phone = :mobileNumber
            """)
    UserEntity findByMobileNumber(@Param("mobileNumber") String mobileNumber);

    @Query("""
            select u from UserEntity u
            where u.email = :email
            """)
    UserEntity findByEmail(@Param("email") String email);

    @Query(value = """
            select u from UserEntity u
            where u.phone = :email
            """, nativeQuery = true)
    UserEntity findByPhone(@Param("email") String email);

    @Query("""
            select u from UserEntity u
            where u.uuid = :uuid
            """)
    UserEntity findByUUID(@Param("uuid") UUID uuid);
}
