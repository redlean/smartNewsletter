package com.redlean.news.repository;

import com.redlean.news.domain.PlanificationEmails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlanificationEmails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanificationEmailsRepository extends JpaRepository<PlanificationEmails, Long> {

}
