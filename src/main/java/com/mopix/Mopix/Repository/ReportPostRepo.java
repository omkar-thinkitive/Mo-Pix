package com.mopix.Mopix.Repository;

import com.mopix.Mopix.Entity.ReportPostEntity;
import com.mopix.Mopix.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

//import java.lang.ScopedValue;
import java.util.UUID;

@Repository
public interface ReportPostRepo extends JpaRepository<ReportPostEntity,Long> {

    @Query("""
            SELECT r FROM ReportPostEntity r
            WHERE
            r.reportedUser = :user
            """)
    Page<ReportPostEntity> findAllReportsByUser(@Param("user") UserEntity user, Pageable pageable);

    @Query("""
            SELECT r FROM ReportPostEntity r
            WHERE r.reportUUID = :reportUUID
            """)
    ReportPostEntity findByReportUUIDAndDeletedFalse(@Param("reportUUID") UUID reportUUID);
}
