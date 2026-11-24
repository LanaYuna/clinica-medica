package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.List;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;

public interface DiagnosticoDAO {

	public void salvar(Diagnostico diagnostico);
	public Diagnostico buscarPorId(String id);
	public void remover(String id);
	public void alterar(Diagnostico diagnostico);
	public List<Diagnostico> listarTodos();
	public List<Diagnostico> listarPorMedico(String idMedico);
	public List<Diagnostico> listarPorPaciente(String idPaciente);
	public List<Diagnostico> listarPorTipo(TipoAtendimento tipo);

}