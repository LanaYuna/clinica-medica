package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Endereco;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;

public interface PacienteService {
	void salvar(Paciente paciente, Endereco endereco);
	List<Paciente> listarTodos();
	Paciente buscarPorId(String id);
	void remover(String id);
	void alterar(Paciente paciente);
}