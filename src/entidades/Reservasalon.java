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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "RESERVASALON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservasalon.findAll", query = "SELECT r FROM Reservasalon r")
    , @NamedQuery(name = "Reservasalon.findById", query = "SELECT r FROM Reservasalon r WHERE r.id = :id")
    , @NamedQuery(name = "Reservasalon.findByDni", query = "SELECT r FROM Reservasalon r WHERE r.reservasalonPK.dni = :dni")
    , @NamedQuery(name = "Reservasalon.findByEvento", query = "SELECT r FROM Reservasalon r WHERE r.evento = :evento")
    , @NamedQuery(name = "Reservasalon.findByFecha", query = "SELECT r FROM Reservasalon r WHERE r.reservasalonPK.fecha = :fecha")
    , @NamedQuery(name = "Reservasalon.findByNPersonas", query = "SELECT r FROM Reservasalon r WHERE r.nPersonas = :nPersonas")
    , @NamedQuery(name = "Reservasalon.findByComida", query = "SELECT r FROM Reservasalon r WHERE r.comida = :comida")
    , @NamedQuery(name = "Reservasalon.findByHabitaciones", query = "SELECT r FROM Reservasalon r WHERE r.habitaciones = :habitaciones")
    , @NamedQuery(name = "Reservasalon.findByNDias", query = "SELECT r FROM Reservasalon r WHERE r.nDias = :nDias")})
public class Reservasalon implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservasalonPK reservasalonPK;
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Column(name = "EVENTO")
    private String evento;
    @Basic(optional = false)
    @Column(name = "N_PERSONAS")
    private int nPersonas;
    @Column(name = "COMIDA")
    private String comida;
    @Column(name = "HABITACIONES")
    private Integer habitaciones;
    @Column(name = "N_DIAS")
    private Integer nDias;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Reservasalon() {
    }

    public Reservasalon(ReservasalonPK reservasalonPK) {
        this.reservasalonPK = reservasalonPK;
    }

    public Reservasalon(ReservasalonPK reservasalonPK, int id, int nPersonas) {
        this.reservasalonPK = reservasalonPK;
        this.id = id;
        this.nPersonas = nPersonas;
    }

    public Reservasalon(String dni, Date fecha) {
        this.reservasalonPK = new ReservasalonPK(dni, fecha);
    }

    public ReservasalonPK getReservasalonPK() {
        return reservasalonPK;
    }

    public void setReservasalonPK(ReservasalonPK reservasalonPK) {
        this.reservasalonPK = reservasalonPK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservasalonPK != null ? reservasalonPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservasalon)) {
            return false;
        }
        Reservasalon other = (Reservasalon) object;
        if ((this.reservasalonPK == null && other.reservasalonPK != null) || (this.reservasalonPK != null && !this.reservasalonPK.equals(other.reservasalonPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "di_t2_apphotel.Reservasalon[ reservasalonPK=" + reservasalonPK + " ]";
    }
    
}
