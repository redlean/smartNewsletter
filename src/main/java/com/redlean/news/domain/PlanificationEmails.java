package com.redlean.news.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A PlanificationEmails.
 */
@Entity
@Table(name = "planification_emails")
public class PlanificationEmails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tache_name")
    private String tacheName;

    @Column(name = "expediteur")
    private String expediteur;

    @Column(name = "destinataires")
    private String destinataires;

    @Column(name = "status")
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

    public String getTacheName() {
        return tacheName;
    }

    public PlanificationEmails tacheName(String tacheName) {
        this.tacheName = tacheName;
        return this;
    }

    public void setTacheName(String tacheName) {
        this.tacheName = tacheName;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public PlanificationEmails expediteur(String expediteur) {
        this.expediteur = expediteur;
        return this;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getDestinataires() {
        return destinataires;
    }

    public PlanificationEmails destinataires(String destinataires) {
        this.destinataires = destinataires;
        return this;
    }

    public void setDestinataires(String destinataires) {
        this.destinataires = destinataires;
    }

    public String getStatus() {
        return status;
    }

    public PlanificationEmails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatePlanif() {
        return datePlanif;
    }

    public PlanificationEmails datePlanif(String datePlanif) {
        this.datePlanif = datePlanif;
        return this;
    }

    public void setDatePlanif(String datePlanif) {
        this.datePlanif = datePlanif;
    }

    public Email getPlanifForEmail() {
        return planifForEmail;
    }

    public PlanificationEmails planifForEmail(Email email) {
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
        PlanificationEmails planificationEmails = (PlanificationEmails) o;
        if (planificationEmails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planificationEmails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanificationEmails{" +
            "id=" + getId() +
            ", tacheName='" + getTacheName() + "'" +
            ", expediteur='" + getExpediteur() + "'" +
            ", destinataires='" + getDestinataires() + "'" +
            ", status='" + getStatus() + "'" +
            ", datePlanif='" + getDatePlanif() + "'" +
            "}";
    }
}
