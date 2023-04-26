package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface WordRepository extends JpaRepository<Word, Long> {

    Optional<Word> findByMember_Idx(Long idx);

    Optional<Word> findByMember_IdxAndLevelAndSuccessLevel(Long idx, Integer level, Integer sLevel);

    @Modifying(clearAutomatically = true)
    @Query("update Word w set w.word = :word, w.level = :level, w.successLevel = :successLevel where w.idx = :idx")
    void update(@Param("word") String word, @Param("level") Integer level, @Param("successLevel") Integer successLevel, @Param("idx") Long idx);
}
