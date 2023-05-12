package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Amends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AmendsRepository extends JpaRepository<Amends, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Amends a set a.amends = :amends, a.goal = :goal, a.remain = :remain where a.idx = :idx")
    void update(@Param("amends") String amends, @Param("goal") Integer goal, @Param("remain") Integer remain, @Param("idx") Long idx);
}
