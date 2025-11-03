package com.rarugames.pointuar.repository;

import com.rarugames.pointuar.model.RankingGourmet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingGourmetRepository extends JpaRepository<RankingGourmet, Long> {
}
