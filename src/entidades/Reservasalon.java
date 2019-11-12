/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scorp
 */
@Entity
@Table(name = "RESERVASALON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservasalon.findAll", query = "SELECT r FROM Reservasalon r")
    , @NamedQuery(name = "Reservasalon.findById", query = "SELECT r FROM Reservasalon r WHERE r.id = :id")
    , @NamedQuery(name = "Reservasalon.findByEvento", query = "SELECT r FROM Reservasalon r WHERE r.evento = :evento")
    , @NamedQuery(name = "Reservasalon.findByFecha", query = "SELECT r FROM Reservasalon r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Reservasalon.findByNPersonas", query = "SELECT r FROM Reservasalon r WHERE r.nPersonas = :nPersonas")
    , @NamedQuery(name = "Reservasalon.findByComida", query = "SELECT r FROM Reservasalon r WHERE r.comida = :comida")
    , @NamedQuery(name = "Reservasalon.findByHabitaciones", query = "SELECT r FROM Reservasalon r WHERE r.habitaciones = :habitaciones")
    , @NamedQuery(name = "Reservasalon.findByNDias", query = "SELECT r FROM Reservasalon r WHERE r.nDias = :nDias")})
public class Reservasalon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "EVENTO")
    private String evento;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "N_PERSONAS")
    private int nPersonas;
    @Column(name = "COMIDA")
    private String comida;
    @Column(name = "HABITACIONES")
    private Integer habitaciones;
    @Column(name = "N_DIAS")
    private Integer nDias;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    @ManyToOne(optional = false)
    private Cliente dni;

    public Reservasalon() {
    }

    public Reservasalon(Integer id) {
        this.id = id;
    }

    public Reservasalon(Integer id, Date fecha, int nPersonas) {
        this.id = id;
        this.fecha = fecha;
        this.nPersonas = nPersonas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNPersonas() {
        return nPersonas;
    }

    public void setNPersonas(int nPersonas) {
        this.nPersonas = nPersonas;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getNDias() {
        return nDias;
    }

    public void setNDias(Integer nDias) {
        this.nDias = nDias;
    }

    public Cliente getDni() {
        return dni;
    }

    public void setDni(Cliente dni) {
        this.dni = dni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservasalon)) {
            return false;
        }
        Reservasalon other = (Reservasalon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Reservasalon[ id=" + id + " ]";
    }
    
}
