package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.userId = :userId, m.password = :password, m.name = :name, m.birth = :birth, m.parentPassword = :parentPassword where m.idx = :idx")
    void update(@Param("userId") String userId, @Param("password") String password, @Param("name") String name, @Param("birth") LocalDate birth, @Param("parentPassword") String parentPassword, @Param("idx") Long idx);
}
