package com.redlean.news.repository;

import com.redlean.news.domain.Tache;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tache entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {

}
