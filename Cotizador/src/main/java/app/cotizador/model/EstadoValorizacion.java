package app.cotizador.model;
// Generated 05-feb-2021 7:00:47 by Hibernate Tools 5.2.11.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * EstadoValorizacion generated by hbm2java
 */
@Entity
@Table(name = "estado_valorizacion", schema = "dbo", catalog = "db_cotizador")
@XmlRootElement
public class EstadoValorizacion implements java.io.Serializable {

	private int estadoValorizacionId;
	private String descripcion;
	private Set<Valorizacion> valorizacions = new HashSet<Valorizacion>(0);

	public EstadoValorizacion() {
	}

	public EstadoValorizacion(int estadoValorizacionId) {
		this.estadoValorizacionId = estadoValorizacionId;
	}
	public EstadoValorizacion(int estadoValorizacionId, String descripcion,
			Set<Valorizacion> valorizacions) {
		this.estadoValorizacionId = estadoValorizacionId;
		this.descripcion = descripcion;
		this.valorizacions = valorizacions;
	}

	@Id
	@Column(name = "estado_valorizacion_id", unique = true, nullable = false)
	public int getEstadoValorizacionId() {
		return this.estadoValorizacionId;
	}

	public void setEstadoValorizacionId(int estadoValorizacionId) {
		this.estadoValorizacionId = estadoValorizacionId;
	}

	@Column(name = "descripcion", length = 50)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoValorizacion")
	public Set<Valorizacion> getValorizacions() {
		return this.valorizacions;
	}

	public void setValorizacions(Set<Valorizacion> valorizacions) {
		this.valorizacions = valorizacions;
	}

}
