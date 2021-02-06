package app.cotizador.rest.dto;

import java.io.Serializable;
import app.cotizador.model.Comuna;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedComunaDTO implements Serializable {

	private int codigo;
	private String nombre;

	public NestedComunaDTO() {
	}

	public NestedComunaDTO(final Comuna entity) {
		if (entity != null) {
			this.codigo = entity.getCodigo();
			this.nombre = entity.getNombre();
		}
	}

	public Comuna fromDTO(Comuna entity, EntityManager em) {
		if (entity == null) {
			entity = new Comuna();
		}
		if (((Integer) this.codigo) != null) {
			TypedQuery<Comuna> findByIdQuery = em
					.createQuery(
							"SELECT DISTINCT c FROM Comuna c WHERE c.codigo = :entityId",
							Comuna.class);
			findByIdQuery.setParameter("entityId", this.codigo);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
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