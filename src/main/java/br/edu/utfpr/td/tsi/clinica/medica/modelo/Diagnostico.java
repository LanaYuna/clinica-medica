package br.edu.utfpr.td.tsi.clinica.medica.modelo;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class Diagnostico {
	private String id;
	private String idMedico;
	private String idPaciente;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dataHora;

	private String cid10;
	private String descricao;
	private String sintomas;
	private String medicamentos;
	private String observacoes;
	private TipoAtendimento tipoAtendimento;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(String idMedico) {
		this.idMedico = idMedico;
	}
	public String getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public String getCid10() {
		return cid10;
	}
	public void setCid10(String cid10) {
		this.cid10 = cid10;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSintomas() {
		return sintomas;
	}
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}
	public String getMedicamentos() {
		return medicamentos;
	}
	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}
	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	@Override
	public String toString() {
		return "Diagnostico [idMedico=" + idMedico + ", idPaciente=" + idPaciente + ", dataHora=" + dataHora
				+ ", tipoAtendimento=" + tipoAtendimento + "]";
	}
}