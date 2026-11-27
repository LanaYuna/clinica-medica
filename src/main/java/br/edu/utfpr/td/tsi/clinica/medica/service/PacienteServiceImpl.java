package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.controle.excecao.ValidacaoException;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Endereco;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.PacienteRepository;

@Service
public class PacienteServiceImpl implements PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;

	@Override
	public void salvar(Paciente paciente, Endereco endereco) {
		paciente.setEndereco(endereco);
		validarCamposObrigatorios(paciente);
		validarUnicidade(paciente);
		pacienteRepository.save(paciente);
	}

	@Override
	public List<Paciente> listarTodos() {
		return pacienteRepository.findAll();
	}

	@Override
	public Paciente buscarPorId(String id) {
		return pacienteRepository.findById(id).orElse(null);
	}
	@Override
	public void remover(String id) {
		pacienteRepository.deleteById(id);
	}

	@Override
	public void alterar(Paciente paciente) {
		if (paciente.getId() == null || !pacienteRepository.existsById(paciente.getId())) {
			throw new ValidacaoException("Paciente não encontrado para alteração.", null);
		}
		
		Endereco endereco = paciente.getEndereco();
		paciente.setEndereco(endereco);
		validarCamposObrigatorios(paciente);
		validarUnicidade(paciente);
		pacienteRepository.save(paciente);
	}

	private void validarCamposObrigatorios(Paciente paciente) {
		if (paciente == null) {
			throw new ValidacaoException("O objeto paciente não pode ser nulo.", null);
		}
		exigir(estaVazio(paciente.getNome()), "O campo Nome é obrigatório.", paciente);
	    exigir(estaVazio(paciente.getCpf()), "O campo CPF é obrigatório.", paciente);
	    exigir(estaVazio(paciente.getEmail()), "O campo E-mail é obrigatório.", paciente);
	    exigir(estaVazio(paciente.getTelefone()), "O campo Telefone é obrigatório.", paciente);

	    Endereco endereco = paciente.getEndereco();
	    exigir(endereco == null, "O endereço é obrigatório.", paciente);
	    exigir(estaVazio(endereco.getCep()), "O campo CEP é obrigatório.", paciente);
	    exigir(estaVazio(endereco.getLogradouro()), "O campo Logradouro é obrigatório.", paciente);
	    exigir(endereco.getNumero() <= 0, "O campo Número é obrigatório.", paciente);
	    exigir(estaVazio(endereco.getBairro()), "O campo Bairro é obrigatório.", paciente);
	    exigir(estaVazio(endereco.getCidadeUf()), "O campo Cidade é obrigatório.", paciente);
	}
	
	private void validarUnicidade(Paciente paciente) {
		Paciente encontrado = pacienteRepository.findByCpf(paciente.getCpf());

		if (encontrado != null && !encontrado.getId().equals(paciente.getId())) {
			exigir(true, "Já existe um paciente cadastrado com este CPF!", paciente);
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