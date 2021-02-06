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
import app.cotizador.model.DatosVehiculo;

/**
 * 
 */
@Stateless
@Path("/datosvehiculos")
public class DatosVehiculoEndpoint {
	@PersistenceContext(unitName = "Cotizador-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(DatosVehiculo entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(DatosVehiculoEndpoint.class)
						.path(String.valueOf(entity.getValorizacionId()))
						.build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") long id) {
		DatosVehiculo entity = em.find(DatosVehiculo.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") long id) {
		TypedQuery<DatosVehiculo> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM DatosVehiculo d LEFT JOIN FETCH d.marca LEFT JOIN FETCH d.modelo WHERE d.valorizacionId = :entityId ORDER BY d.valorizacionId",
						DatosVehiculo.class);
		findByIdQuery.setParameter("entityId", id);
		DatosVehiculo entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<DatosVehiculo> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<DatosVehiculo> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM DatosVehiculo d LEFT JOIN FETCH d.marca LEFT JOIN FETCH d.modelo ORDER BY d.valorizacionId",
						DatosVehiculo.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<DatosVehiculo> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") long id, DatosVehiculo entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id != entity.getValorizacionId()) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(DatosVehiculo.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
