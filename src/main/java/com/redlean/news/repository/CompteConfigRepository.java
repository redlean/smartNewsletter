package com.redlean.news.repository;

import com.redlean.news.domain.CompteConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompteConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteConfigRepository extends JpaRepository<CompteConfig, Long> {

}
