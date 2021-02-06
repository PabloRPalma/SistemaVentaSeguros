package app.cotizador.rest.dto;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import app.cotizador.model.Comuna;

@XmlRootElement
public class ComunaDTO implements Serializable {

	private int codigo;
	private String nombre;
	
	public ComunaDTO() {
	}

	public ComunaDTO(final Comuna entity) {
		if (entity != null) {
			this.codigo = entity.getCodigo();
			this.nombre = entity.getNombre();
		
		}
	}

	public Comuna fromDTO(Comuna entity, EntityManager em) {
		if (entity == null) {
			entity = new Comuna();
		}
		entity.setNombre(this.nombre);
		
		entity = em.merge(entity);
		return entity;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}


}