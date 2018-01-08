package com.redlean.news.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Email.
 */
@Entity
@Table(name = "email")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "destinataires", nullable = false)
    private String destinataires;

    @NotNull
    @Column(name = "expediteur", nullable = false)
    private String expediteur;

    @NotNull
    @Column(name = "objet", nullable = false)
    private String objet;

    @NotNull
    @Column(name = "contenu", nullable = false)
    private String contenu;

    @Column(name = "piece_joint")
    private String pieceJoint;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinataires() {
        return destinataires;
    }

    public Email destinataires(String destinataires) {
        this.destinataires = destinataires;
        return this;
    }

    public void setDestinataires(String destinataires) {
        this.destinataires = destinataires;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public Email expediteur(String expediteur) {
        this.expediteur = expediteur;
        return this;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
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
            ", destinataires='" + getDestinataires() + "'" +
            ", expediteur='" + getExpediteur() + "'" +
            ", objet='" + getObjet() + "'" +
            ", contenu='" + getContenu() + "'" +
            ", pieceJoint='" + getPieceJoint() + "'" +
            "}";
    }
}
