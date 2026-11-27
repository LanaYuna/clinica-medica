package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			throw new IllegalArgumentException("Paciente não encontrado para alteração.");
		}
		
		Endereco endereco = paciente.getEndereco();
		paciente.setEndereco(endereco);
		validarCamposObrigatorios(paciente);
		validarUnicidade(paciente);
		pacienteRepository.save(paciente);
	}

	private void validarCamposObrigatorios(Paciente paciente) {
		if (paciente == null) {
			throw new IllegalArgumentException("O objeto paciente não pode ser nulo.");
		}
		if (estaVazio(paciente.getNome())) {
			throw new IllegalArgumentException("O campo Nome é obrigatório.");
		}
		if (estaVazio(paciente.getCpf())) {
			throw new IllegalArgumentException("O campo CPF é obrigatório.");
		}
		if (estaVazio(paciente.getEmail())) {
			throw new IllegalArgumentException("O campo E-mail é obrigatório.");
		}
		if (estaVazio(paciente.getTelefone())) {
			throw new IllegalArgumentException("O campo Telefone é obrigatório.");
		}

		Endereco endereco = paciente.getEndereco();
		if (endereco == null) {
			throw new IllegalArgumentException("O endereço é obrigatório.");
		}
		if (estaVazio(endereco.getCep())) {
			throw new IllegalArgumentException("O campo CEP é obrigatório.");
		}
		if (estaVazio(endereco.getLogradouro())) {
			throw new IllegalArgumentException("O campo Logradouro é obrigatório.");
		}
		if (endereco.getNumero() <= 0) {
			throw new IllegalArgumentException("O campo Número é obrigatório e deve ser maior que zero.");
		}
		if (estaVazio(endereco.getBairro())) {
			throw new IllegalArgumentException("O campo Bairro é obrigatório.");
		}
		if (estaVazio(endereco.getCidadeUf())) {
		    throw new IllegalArgumentException("O campo Cidade é obrigatório.");
		}
	}

	private void validarUnicidade(Paciente paciente) {
		Paciente encontrado = pacienteRepository.findByCpf(paciente.getCpf());

		if (encontrado != null && !encontrado.getId().equals(paciente.getId())) {
			throw new IllegalArgumentException("Já existe um paciente cadastrado com este CPF!");
		}
	}

	private boolean estaVazio(String texto) {
		return texto == null || texto.trim().isEmpty();
	}
}