package app.cotizador.rest.dto;

import java.io.Serializable;
import app.cotizador.model.DatosAsegurado;
import javax.persistence.EntityManager;
import app.cotizador.rest.dto.NestedComunaDTO;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatosAseguradoDTO implements Serializable {

	private long valorizacionId;
	private NestedComunaDTO comuna;
	private Integer codigo;
	private Long rut;
	private String rutDv;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String razonSocial;
	private Date fechaNacimiento;
	private String calle;
	private String numero;
	private String sexo;
	private String telefonoFijoArea;
	private String telefonoFijo;
	private String celularArea;
	private String celular;
	private String email;
	private Boolean contratanteEsMismo;

	public DatosAseguradoDTO() {
	}

	public DatosAseguradoDTO(final DatosAsegurado entity) {
		if (entity != null) {
			this.valorizacionId = entity.getValorizacionId();
			this.comuna = new NestedComunaDTO(entity.getComuna());
			this.codigo = entity.getCodigo();
			this.rut = entity.getRut();
			this.rutDv = entity.getRutDv();
			this.nombres = entity.getNombres();
			this.apellidoPaterno = entity.getApellidoPaterno();
			this.apellidoMaterno = entity.getApellidoMaterno();
			this.razonSocial = entity.getRazonSocial();
			this.fechaNacimiento = entity.getFechaNacimiento();
			this.calle = entity.getCalle();
			this.numero = entity.getNumero();
			this.sexo = entity.getSexo();
			this.telefonoFijoArea = entity.getTelefonoFijoArea();
			this.telefonoFijo = entity.getTelefonoFijo();
			this.celularArea = entity.getCelularArea();
			this.celular = entity.getCelular();
			this.email = entity.getEmail();
			this.contratanteEsMismo = entity.getContratanteEsMismo();
		}
	}

	public DatosAsegurado fromDTO(DatosAsegurado entity, EntityManager em) {
		if (entity == null) {
			entity = new DatosAsegurado();
		}
		if (this.comuna != null) {
			entity.setComuna(this.comuna.fromDTO(entity.getComuna(), em));
		}
		entity.setCodigo(this.codigo);
		entity.setRut(this.rut);
		entity.setRutDv(this.rutDv);
		entity.setNombres(this.nombres);
		entity.setApellidoPaterno(this.apellidoPaterno);
		entity.setApellidoMaterno(this.apellidoMaterno);
		entity.setRazonSocial(this.razonSocial);
		entity.setFechaNacimiento(this.fechaNacimiento);
		entity.setCalle(this.calle);
		entity.setNumero(this.numero);
		entity.setSexo(this.sexo);
		entity.setTelefonoFijoArea(this.telefonoFijoArea);
		entity.setTelefonoFijo(this.telefonoFijo);
		entity.setCelularArea(this.celularArea);
		entity.setCelular(this.celular);
		entity.setEmail(this.email);
		entity.setContratanteEsMismo(this.contratanteEsMismo);
		entity = em.merge(entity);
		return entity;
	}

	public long getValorizacionId() {
		return this.valorizacionId;
	}

	public void setValorizacionId(final long valorizacionId) {
		this.valorizacionId = valorizacionId;
	}

	public NestedComunaDTO getComuna() {
		return this.comuna;
	}

	public void setComuna(final NestedComunaDTO comuna) {
		this.comuna = comuna;
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Integer codigo) {
		this.codigo = codigo;
	}

	public Long getRut() {
		return this.rut;
	}

	public void setRut(final Long rut) {
		this.rut = rut;
	}

	public String getRutDv() {
		return this.rutDv;
	}

	public void setRutDv(final String rutDv) {
		this.rutDv = rutDv;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(final String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(final String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(final String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(final String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(final Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(final String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(final String numero) {
		this.numero = numero;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(final String sexo) {
		this.sexo = sexo;
	}

	public String getTelefonoFijoArea() {
		return this.telefonoFijoArea;
	}

	public void setTelefonoFijoArea(final String telefonoFijoArea) {
		this.telefonoFijoArea = telefonoFijoArea;
	}

	public String getTelefonoFijo() {
		return this.telefonoFijo;
	}

	public void setTelefonoFijo(final String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getCelularArea() {
		return this.celularArea;
	}

	public void setCelularArea(final String celularArea) {
		this.celularArea = celularArea;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(final String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Boolean getContratanteEsMismo() {
		return this.contratanteEsMismo;
	}

	public void setContratanteEsMismo(final Boolean contratanteEsMismo) {
		this.contratanteEsMismo = contratanteEsMismo;
	}
}