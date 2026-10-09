package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

public interface MedicoService {
	void salvar(Medico medico);
    List<Medico> listarTodos();
    Medico buscarPorId(String id);
    void remover(String id);
    void alterar(Medico medico);
}
