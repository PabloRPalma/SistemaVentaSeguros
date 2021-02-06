package app.cotizador.model;
// Generated 06-feb-2021 8:37:23 by Hibernate Tools 5.2.11.Final

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
 * Comuna generated by hbm2java
 */
@Entity
@Table(name = "comuna", schema = "dbo", catalog = "db_cotizador")
@XmlRootElement
public class Comuna implements java.io.Serializable {

	private int codigo;
	private String nombre;
	

	public Comuna() {
	}

	public Comuna(int codigo) {
		this.codigo = codigo;
	}
	public Comuna(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	
	}

	@Id
	@Column(name = "codigo", unique = true, nullable = false)
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Column(name = "nombre", length = 59)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}