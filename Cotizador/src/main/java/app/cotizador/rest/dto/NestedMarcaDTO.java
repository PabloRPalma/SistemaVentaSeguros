package app.cotizador.rest.dto;

import java.io.Serializable;
import app.cotizador.model.Marca;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedMarcaDTO implements Serializable {

	private Integer marcaId;
	private String descripcion;

	public NestedMarcaDTO() {
	}

	public NestedMarcaDTO(final Marca entity) {
		if (entity != null) {
			this.marcaId = entity.getMarcaId();
			this.descripcion = entity.getDescripcion();
		}
	}

	public Marca fromDTO(Marca entity, EntityManager em) {
		if (entity == null) {
			entity = new Marca();
		}
		if (this.marcaId != null) {
			TypedQuery<Marca> findByIdQuery = em
					.createQuery(
							"SELECT DISTINCT m FROM Marca m WHERE m.marcaId = :entityId",
							Marca.class);
			findByIdQuery.setParameter("entityId", this.marcaId);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
		}
		entity.setDescripcion(this.descripcion);
		entity = em.merge(entity);
		return entity;
	}

	public Integer getMarcaId() {
		return this.marcaId;
	}

	public void setMarcaId(final Integer marcaId) {
		this.marcaId = marcaId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}
}