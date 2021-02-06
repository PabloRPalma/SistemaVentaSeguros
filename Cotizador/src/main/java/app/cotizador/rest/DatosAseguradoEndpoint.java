package app.cotizador.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import app.cotizador.model.DatosAsegurado;
import app.cotizador.rest.dto.DatosAseguradoDTO;

/**
 * 
 */
@Stateless
@Path("/datosasegurados")
public class DatosAseguradoEndpoint {
	@PersistenceContext(unitName = "Cotizador-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(DatosAsegurado entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(DatosAseguradoEndpoint.class)
						.path(String.valueOf(entity.getValorizacionId()))
						.build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") long id) {
		DatosAsegurado entity = em.find(DatosAsegurado.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<DatosAsegurado> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM DatosAsegurado d WHERE d.valorizacionId = :entityId ORDER BY d.valorizacionId",
						DatosAsegurado.class);
		findByIdQuery.setParameter("entityId", id);
		DatosAsegurado entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		DatosAseguradoDTO dto = new DatosAseguradoDTO(entity);
		return Response.ok(dto).build();
	
	}

	@GET
	@Produces("application/json")
	public List<DatosAsegurado> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<DatosAsegurado> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM DatosAsegurado d ORDER BY d.valorizacionId",
						DatosAsegurado.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<DatosAsegurado> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") long id, DatosAseguradoDTO dto) {
		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id != dto.getValorizacionId()) {
			return Response.status(Status.CONFLICT).entity(dto).build();
		}
		DatosAsegurado entity = em.find(DatosAsegurado.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		entity = dto.fromDTO(entity, em);
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Status.CONFLICT).entity(e.getEntity())
					.build();
		}
		return Response.noContent().build();
	}
}