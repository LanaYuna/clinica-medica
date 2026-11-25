package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.MedicoRepository;

@Service
public class MedicoServiceImpl implements MedicoService{
	
	@Autowired
	private MedicoRepository medicoRepository;

	@Override
	public void salvar(Medico medico) {
		validarCamposObrigatorios(medico);
		validarUnicidade(medico);
		medicoRepository.save(medico);
	}

	@Override
	public List<Medico> listarTodos() {
		return medicoRepository.findAll();
	}

	@Override
	public Medico buscarPorId(String id) {
		return medicoRepository.findById(id).orElse(null);
	}

	@Override
	public void remover(String id) {
		medicoRepository.deleteById(id);
	}

	@Override
	public void alterar(Medico medico) {
		if (medico.getId() == null || !medicoRepository.existsById(medico.getId())) {
			throw new IllegalArgumentException("Médico não encontrado para alteração.");
		}
		validarCamposObrigatorios(medico);
		validarUnicidade(medico);
		
		medicoRepository.save(medico);
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
	private void validarUnicidade(Medico medico) {
        Medico encontrado = medicoRepository.findByCpf(medico.getCpf());

        if (encontrado != null &&
            !encontrado.getId().equals(medico.getId())) {
            throw new IllegalArgumentException(
                "Já existe um médico cadastrado com este CPF!"
            );
        }

        encontrado = medicoRepository.findByCrm(medico.getCrm());

        if (encontrado != null &&
            !encontrado.getId().equals(medico.getId())) {
            throw new IllegalArgumentException(
                "Já existe um médico cadastrado com este CRM!"
            );
        }

        encontrado = medicoRepository.findByEmail(medico.getEmail());

        if (encontrado != null &&
            !encontrado.getId().equals(medico.getId())) {
            throw new IllegalArgumentException(
                "Já existe um médico cadastrado com este E-mail!"
            );
        }
    }
}