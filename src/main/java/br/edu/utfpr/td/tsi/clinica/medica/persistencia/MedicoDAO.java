package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

public interface MedicoDAO {

	public void salvar(Medico medico);
	public Medico buscarPorId(String id);
    public Medico buscarPorCpf(String cpf);
    public Medico buscarPorCrm(String crm);
    public Medico buscarPorEmail(String email);
	public void remover(String id);
	public void alterar(Medico medico);
	public List<Medico> listarTodos();
	
}
