package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, String> {
}
