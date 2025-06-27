package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface HashTagRepo extends JpaRepository<HashTag,Long> {

    @Query("""
            SELECT h FROM HashTag h
            where h.name = :hashTag
            """)
    HashTag findHashTagByName(@Param("hashTag") String hashTag);

    <T> Optional<T> findByName(String tagName);
}
