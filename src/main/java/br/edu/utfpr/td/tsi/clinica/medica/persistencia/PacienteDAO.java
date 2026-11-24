package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.List;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;

public interface PacienteDAO {

	public void salvar(Paciente paciente);
	public Paciente buscarPorId(String id);
	public Paciente buscarPorCpf(String cpf);
	public void remover(String id);
	public void alterar(Paciente paciente);
	public List<Paciente> listarTodos();

}