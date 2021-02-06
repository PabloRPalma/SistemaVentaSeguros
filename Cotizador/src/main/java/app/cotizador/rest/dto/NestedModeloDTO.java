package app.cotizador.rest.dto;

import java.io.Serializable;
import app.cotizador.model.Modelo;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedModeloDTO implements Serializable {

	private Long id;
	private String descripcion;
	private Integer modeloId;

	public NestedModeloDTO() {
	}

	public NestedModeloDTO(final Modelo entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.descripcion = entity.getDescripcion();
			this.modeloId = entity.getModeloId();
		}
	}

	public Modelo fromDTO(Modelo entity, EntityManager em) {
		if (entity == null) {
			entity = new Modelo();
		}
		if (this.id != null) {
			TypedQuery<Modelo> findByIdQuery = em.createQuery(
					"SELECT DISTINCT m FROM Modelo m WHERE m.id = :entityId",
					Modelo.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
		}
		entity.setDescripcion(this.descripcion);
		entity.setModeloId(this.modeloId);
		entity = em.merge(entity);
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getModeloId() {
		return this.modeloId;
	}

	public void setModeloId(final Integer modeloId) {
		this.modeloId = modeloId;
	}
}