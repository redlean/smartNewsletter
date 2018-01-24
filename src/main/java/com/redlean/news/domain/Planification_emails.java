package com.redlean.news.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Planification_emails.
 */
@Entity
@Table(name = "planification_emails")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Planification_emails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "planif_name", nullable = false)
    private String planifName;

    @NotNull
    @Column(name = "expediteur", nullable = false)
    private String expediteur;

    @NotNull
    @Column(name = "destinataire", nullable = false)
    private String destinataire;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "date_planif", nullable = false)
    private String datePlanif;

    @ManyToOne(optional = false)
    @NotNull
    private Email planifForEmail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanifName() {
        return planifName;
    }

    public Planification_emails planifName(String planifName) {
        this.planifName = planifName;
        return this;
    }

    public void setPlanifName(String planifName) {
        this.planifName = planifName;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public Planification_emails expediteur(String expediteur) {
        this.expediteur = expediteur;
        return this;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public Planification_emails destinataire(String destinataire) {
        this.destinataire = destinataire;
        return this;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getStatus() {
        return status;
    }

    public Planification_emails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatePlanif() {
        return datePlanif;
    }

    public Planification_emails datePlanif(String datePlanif) {
        this.datePlanif = datePlanif;
        return this;
    }

    public void setDatePlanif(String datePlanif) {
        this.datePlanif = datePlanif;
    }

    public Email getPlanifForEmail() {
        return planifForEmail;
    }

    public Planification_emails planifForEmail(Email email) {
        this.planifForEmail = email;
        return this;
    }

    public void setPlanifForEmail(Email email) {
        this.planifForEmail = email;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Planification_emails planification_emails = (Planification_emails) o;
        if (planification_emails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planification_emails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Planification_emails{" +
            "id=" + getId() +
            ", planifName='" + getPlanifName() + "'" +
            ", expediteur='" + getExpediteur() + "'" +
            ", destinataire='" + getDestinataire() + "'" +
            ", status='" + getStatus() + "'" +
            ", datePlanif='" + getDatePlanif() + "'" +
            "}";
    }
}
