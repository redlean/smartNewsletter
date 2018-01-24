package com.redlean.news.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "objet", nullable = false)
    private String objet;

    @NotNull
    @Column(name = "contenu", columnDefinition="text", nullable = false)
    private String contenu;

    @Column(name = "piece_joint")
    private String pieceJoint;

    @OneToMany(mappedBy = "planifForEmail")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Planification_emails> planifForEmails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjet() {
        return objet;
    }

    public Email objet(String objet) {
        this.objet = objet;
        return this;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public Email contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getPieceJoint() {
        return pieceJoint;
    }

    public Email pieceJoint(String pieceJoint) {
        this.pieceJoint = pieceJoint;
        return this;
    }

    public void setPieceJoint(String pieceJoint) {
        this.pieceJoint = pieceJoint;
    }

    public Set<Planification_emails> getPlanifForEmails() {
        return planifForEmails;
    }

    public Email planifForEmails(Set<Planification_emails> planification_emails) {
        this.planifForEmails = planification_emails;
        return this;
    }

    public Email addPlanifForEmail(Planification_emails planification_emails) {
        this.planifForEmails.add(planification_emails);
        planification_emails.setPlanifForEmail(this);
        return this;
    }

    public Email removePlanifForEmail(Planification_emails planification_emails) {
        this.planifForEmails.remove(planification_emails);
        planification_emails.setPlanifForEmail(null);
        return this;
    }

    public void setPlanifForEmails(Set<Planification_emails> planification_emails) {
        this.planifForEmails = planification_emails;
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
        Email email = (Email) o;
        if (email.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), email.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", objet='" + getObjet() + "'" +
            ", contenu='" + getContenu() + "'" +
            ", pieceJoint='" + getPieceJoint() + "'" +
            "}";
    }
}
