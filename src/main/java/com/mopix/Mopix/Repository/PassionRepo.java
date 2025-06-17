package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.PassionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
@Repository
public interface PassionRepo extends JpaRepository<PassionEntity, Long> {
}
