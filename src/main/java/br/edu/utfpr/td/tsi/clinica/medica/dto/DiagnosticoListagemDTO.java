package br.edu.utfpr.td.tsi.clinica.medica.dto;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;

public class DiagnosticoListagemDTO {

	private Diagnostico diagnostico;
	private String nomeMedico;
	private String nomePaciente;
	
	public Diagnostico getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}
	public String getNomeMedico() {
		return nomeMedico;
	}
	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}
	public String getNomePaciente() {
		return nomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	
}
