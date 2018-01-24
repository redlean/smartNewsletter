package com.redlean.news.repository;

import com.redlean.news.domain.Planification_emails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Planification_emails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Planification_emailsRepository extends JpaRepository<Planification_emails, Long> {

}
