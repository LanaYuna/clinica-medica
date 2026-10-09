package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.MedicoDAO;

@Service
public class MedicoServiceImpl implements MedicoService{
	
	@Autowired
	private MedicoDAO dao;

	@Override
	public void salvar(Medico medico) {
		validarCamposObrigatorios(medico);
		dao.salvar(medico);
	}

	@Override
	public List<Medico> listarTodos() {
		return dao.listarTodos();
	}

	@Override
	public Medico buscarPorId(String id) {
		return dao.buscarPorId(id);
	}

	@Override
	public void remover(String id) {
		dao.remover(id);
	}

	@Override
	public void alterar(Medico medico) {
		dao.alterar(medico);
	}

	private void validarCamposObrigatorios(Medico medico) {
		if (medico == null) {
            throw new IllegalArgumentException("O objeto médico não pode ser nulo.");
        }
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Nome é obrigatório.");
        }
        if (medico.getCpf() == null || medico.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo CPF é obrigatório.");
        }
        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo CRM é obrigatório.");
        }
        if (medico.getEmail() == null || medico.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo E-mail é obrigatório.");
        }
        if (medico.getEspecialidades() == null || medico.getEspecialidades().isEmpty()) {
            throw new IllegalArgumentException("O médico deve possuir pelo menos uma especialidade.");
        }
	}
}
