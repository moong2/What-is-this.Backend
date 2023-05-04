package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Analysis a set a.count = :count, a.level = :level, a.successRate1 = :successRate1, a.successRate2 = :successRate2, a.successRate3 = :successRate3 where a.idx = :idx")
    void update(@Param("count") Integer count, @Param("level") Integer level, @Param("successRate1") Double successRate1, @Param("successRate2") Double successRate2, @Param("successRate3") Double successRate3, @Param("idx") Long idx);
}
