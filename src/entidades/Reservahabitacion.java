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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "RESERVAHABITACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservahabitacion.findAll", query = "SELECT r FROM Reservahabitacion r")
    , @NamedQuery(name = "Reservahabitacion.findById", query = "SELECT r FROM Reservahabitacion r WHERE r.reservahabitacionPK.id = :id")
    , @NamedQuery(name = "Reservahabitacion.findByDni", query = "SELECT r FROM Reservahabitacion r WHERE r.reservahabitacionPK.dni = :dni")
    , @NamedQuery(name = "Reservahabitacion.findByLlegada", query = "SELECT r FROM Reservahabitacion r WHERE r.llegada = :llegada")
    , @NamedQuery(name = "Reservahabitacion.findBySalida", query = "SELECT r FROM Reservahabitacion r WHERE r.salida = :salida")
    , @NamedQuery(name = "Reservahabitacion.findByNHabitaciones", query = "SELECT r FROM Reservahabitacion r WHERE r.nHabitaciones = :nHabitaciones")
    , @NamedQuery(name = "Reservahabitacion.findByTipo", query = "SELECT r FROM Reservahabitacion r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "Reservahabitacion.findByFumador", query = "SELECT r FROM Reservahabitacion r WHERE r.fumador = :fumador")
    , @NamedQuery(name = "Reservahabitacion.findByRegimen", query = "SELECT r FROM Reservahabitacion r WHERE r.regimen = :regimen")})
public class Reservahabitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservahabitacionPK reservahabitacionPK;
    @Basic(optional = false)
    @Column(name = "LLEGADA")
    @Temporal(TemporalType.DATE)
    private Date llegada;
    @Basic(optional = false)
    @Column(name = "SALIDA")
    @Temporal(TemporalType.DATE)
    private Date salida;
    @Basic(optional = false)
    @Column(name = "N_HABITACIONES")
    private int nHabitaciones;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "FUMADOR")
    private String fumador;
    @Column(name = "REGIMEN")
    private String regimen;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Reservahabitacion() {
    }

    public Reservahabitacion(ReservahabitacionPK reservahabitacionPK) {
        this.reservahabitacionPK = reservahabitacionPK;
    }

    public Reservahabitacion(ReservahabitacionPK reservahabitacionPK, Date llegada, Date salida, int nHabitaciones) {
        this.reservahabitacionPK = reservahabitacionPK;
        this.llegada = llegada;
        this.salida = salida;
        this.nHabitaciones = nHabitaciones;
    }

    public Reservahabitacion(int id, String dni) {
        this.reservahabitacionPK = new ReservahabitacionPK(id, dni);
    }

    public ReservahabitacionPK getReservahabitacionPK() {
        return reservahabitacionPK;
    }

    public void setReservahabitacionPK(ReservahabitacionPK reservahabitacionPK) {
        this.reservahabitacionPK = reservahabitacionPK;
    }

    public Date getLlegada() {
        return llegada;
    }

    public void setLlegada(Date llegada) {
        this.llegada = llegada;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public int getNHabitaciones() {
        return nHabitaciones;
    }

    public void setNHabitaciones(int nHabitaciones) {
        this.nHabitaciones = nHabitaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFumador() {
        return fumador;
    }

    public void setFumador(String fumador) {
        this.fumador = fumador;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
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
        hash += (reservahabitacionPK != null ? reservahabitacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservahabitacion)) {
            return false;
        }
        Reservahabitacion other = (Reservahabitacion) object;
        if ((this.reservahabitacionPK == null && other.reservahabitacionPK != null) || (this.reservahabitacionPK != null && !this.reservahabitacionPK.equals(other.reservahabitacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "di_t2_apphotel.Reservahabitacion[ reservahabitacionPK=" + reservahabitacionPK + " ]";
    }
    
}
