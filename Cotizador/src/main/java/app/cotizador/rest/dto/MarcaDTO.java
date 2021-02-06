package app.cotizador.rest.dto;

import java.io.Serializable;
import app.cotizador.model.Marca;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;
import app.cotizador.rest.dto.NestedModeloDTO;
import app.cotizador.model.Modelo;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MarcaDTO implements Serializable {

	private Integer marcaId;
	private String descripcion;
	private List<NestedModeloDTO> modelos = new ArrayList<NestedModeloDTO>();

	public MarcaDTO() {
	}

	public MarcaDTO(final Marca entity) {
		if (entity != null) {
			this.marcaId = entity.getMarcaId();
			this.descripcion = entity.getDescripcion();
			Iterator<Modelo> iterModelos = entity.getModelos().iterator();
			while (iterModelos.hasNext()) {
				Modelo element = iterModelos.next();
				this.modelos.add(new NestedModeloDTO(element));
			}
		}
	}

	public Marca fromDTO(Marca entity, EntityManager em) {
		if (entity == null) {
			entity = new Marca();
		}
		entity.setDescripcion(this.descripcion);
		Iterator<Modelo> iterModelos = entity.getModelos().iterator();
		while (iterModelos.hasNext()) {
			boolean found = false;
			Modelo modelo = iterModelos.next();
			Iterator<NestedModeloDTO> iterDtoModelos = this.getModelos()
					.iterator();
			while (iterDtoModelos.hasNext()) {
				NestedModeloDTO dtoModelo = iterDtoModelos.next();
				if (dtoModelo.getId().equals(modelo.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterModelos.remove();
			}
		}
		Iterator<NestedModeloDTO> iterDtoModelos = this.getModelos().iterator();
		while (iterDtoModelos.hasNext()) {
			boolean found = false;
			NestedModeloDTO dtoModelo = iterDtoModelos.next();
			iterModelos = entity.getModelos().iterator();
			while (iterModelos.hasNext()) {
				Modelo modelo = iterModelos.next();
				if (dtoModelo.getId().equals(modelo.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<Modelo> resultIter = em
						.createQuery("SELECT DISTINCT m FROM Modelo m",
								Modelo.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					Modelo result = resultIter.next();
					if (result.getId().equals(dtoModelo.getId())) {
						entity.getModelos().add(result);
						break;
					}
				}
			}
		}
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

	public List<NestedModeloDTO> getModelos() {
		return this.modelos;
	}

	public void setModelos(final List<NestedModeloDTO> modelos) {
		this.modelos = modelos;
	}
}