package app.cotizador.rest;

import java.util.Calendar;
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

import app.cotizador.model.EstadoValorizacion;
import app.cotizador.model.Valorizacion;

/**
 * 
 */
@Stateless
@Path("/valorizacions")
public class ValorizacionEndpoint {
	@PersistenceContext(unitName = "Cotizador-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Valorizacion entity) {
		entity.setValorizacionId(null);
		entity.setClienteNuevo(true);
		EstadoValorizacion ev = em.getReference(EstadoValorizacion.class, Integer.valueOf(1));
		entity.setEstadoValorizacion(ev);
		entity.setFecha(Calendar.getInstance().getTime());
		entity.setFechaHora(Calendar.getInstance().getTime());
		entity.setUsuarioId(1);

		em.persist(entity);
		em.flush();
		em.createNativeQuery("{call pa_nueva_cotizacion(?)}").setParameter(1, entity.getValorizacionId()).executeUpdate();
		return Response.created(
				UriBuilder.fromResource(ValorizacionEndpoint.class)
						.path(String.valueOf(entity.getValorizacionId()))
						.build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Valorizacion entity = em.find(Valorizacion.class, id);
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
		TypedQuery<Valorizacion> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT v FROM Valorizacion v LEFT JOIN FETCH v.estadoValorizacion WHERE v.valorizacionId = :entityId ORDER BY v.valorizacionId",
						Valorizacion.class);
		findByIdQuery.setParameter("entityId", id);
		Valorizacion entity;
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
	public List<Valorizacion> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Valorizacion> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT v FROM Valorizacion v LEFT JOIN FETCH v.estadoValorizacion ORDER BY v.valorizacionId",
						Valorizacion.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Valorizacion> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Valorizacion entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getValorizacionId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(Valorizacion.class, id) == null) {
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
