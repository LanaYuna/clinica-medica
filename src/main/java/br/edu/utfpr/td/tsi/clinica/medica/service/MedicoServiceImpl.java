package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.controle.excecao.ValidacaoException;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.MedicoRepository;

@Service
public class MedicoServiceImpl implements MedicoService{
	
	@Autowired
	private MedicoRepository medicoRepository;

	@Override
	public void salvar(Medico medico) {
		System.out.println("ID recebido no salvar: " + medico.getId());
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
            throw new ValidacaoException("O objeto médico não pode ser nulo.", null);
        }
		exigir(estaVazio(medico.getNome()), "O campo Nome é obrigatório.", medico);
		exigir(estaVazio(medico.getCpf()), "O campo Cpf é obrigatório.", medico);
		exigir(estaVazio(medico.getEmail()), "O campo Email é obrigatório.", medico);
		exigir(medico.getEspecialidades() == null || medico.getEspecialidades().isEmpty(), "O médico deve possuir pelo menos uma especialidade.", medico);
	}
	
	private void validarUnicidade(Medico medico) {
        Medico encontrado = medicoRepository.findByCpf(medico.getCpf());

        if (encontrado != null && !encontrado.getId().equals(medico.getId())) {
        	exigir(true, "Já existe um médico cadastrado com este CPF!", medico);
        }

        encontrado = medicoRepository.findByCrm(medico.getCrm());

        if (encontrado != null && !encontrado.getId().equals(medico.getId())) {
        	exigir(true, "Já existe um médico cadastrado com este CRM!", medico);
        }

        encontrado = medicoRepository.findByEmail(medico.getEmail());

        if (encontrado != null && !encontrado.getId().equals(medico.getId())) {
        	exigir(true, "Já existe um médico cadastrado com este E-mail", medico);
        }
    }
	
	private void exigir(boolean invalido, String mensagem, Object dados) {
	    if (invalido) {
	        throw new ValidacaoException(mensagem, dados);
	    }
	}
	
	private boolean estaVazio(String texto) {
		return texto == null || texto.trim().isEmpty();
	}
	
}