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
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String id);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.id = :id, m.password = :password, m.name = :name, m.birth = :birth, m.parent_password = :parent_password where m.idx = :idx")
    void update(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("birth") LocalDate birth, @Param("parent_password") String parent_password, @Param("idx") Long idx);
}
