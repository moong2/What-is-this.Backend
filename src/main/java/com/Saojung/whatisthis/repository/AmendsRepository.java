package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Amends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmendsRepository extends JpaRepository<Amends, String> {
}
