package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashTagRepo extends JpaRepository<HashTagEntity,Long> {

    @Query("""
            SELECT h FROM HashTagEntity h
            where h.name = :hashTagEntity
            """)
    HashTagEntity findHashTagByName(@Param("hashTagEntity") String hashTagEntity);

    <T> Optional<T> findByName(String tagName);
}
