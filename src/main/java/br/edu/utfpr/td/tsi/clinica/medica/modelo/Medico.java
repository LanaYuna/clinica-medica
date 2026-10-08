package br.edu.utfpr.td.tsi.clinica.medica.modelo;

import java.util.List;

public class Medico {
	private String id;
	private String nome;
	private String cpf;
	private String email;
	private String crm;
	private List<Especialidade> especialidades;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}	
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	@Override
	public String toString() {
		return "Medico [nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", crm=" + crm + ", especialidades="
				+ especialidades + "]";
	}
	
}
