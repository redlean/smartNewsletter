package com.redlean.news.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Tache.
 */
@Entity
@Table(name = "tache")
public class Tache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_envoie", nullable = false)
    private String dateEnvoie;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "jhi_log")
    private String log;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateEnvoie() {
        return dateEnvoie;
    }

    public Tache dateEnvoie(String dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
        return this;
    }

    public void setDateEnvoie(String dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public String getStatus() {
        return status;
    }

    public Tache status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public Tache log(String log) {
        this.log = log;
        return this;
    }

    public void setLog(String log) {
        this.log = log;
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
        Tache tache = (Tache) o;
        if (tache.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tache.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tache{" +
            "id=" + getId() +
            ", dateEnvoie='" + getDateEnvoie() + "'" +
            ", status='" + getStatus() + "'" +
            ", log='" + getLog() + "'" +
            "}";
    }
}
