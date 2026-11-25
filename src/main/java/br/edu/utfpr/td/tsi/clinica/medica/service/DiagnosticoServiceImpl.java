package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.DiagnosticoRepository;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.MedicoRepository;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.PacienteRepository;

@Service
public class DiagnosticoServiceImpl implements DiagnosticoService {

	@Autowired
	private DiagnosticoRepository diagnosticoRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Override
	public void salvar(Diagnostico diagnostico) {
		validarCamposObrigatorios(diagnostico);
		validarMedicoEPaciente(diagnostico);
		diagnosticoRepository.save(diagnostico);
	}

	@Override
	public List<Diagnostico> listarTodos() {
		return diagnosticoRepository.findAll();
	}

	@Override
	public Diagnostico buscarPorId(String id) {
		return diagnosticoRepository.findById(id).orElse(null);
	}

	@Override
	public void remover(String id) {
		diagnosticoRepository.deleteById(id);
	}

	@Override
	public void alterar(Diagnostico diagnostico) {
		if (diagnostico.getId() == null || !diagnosticoRepository.existsById(diagnostico.getId())) {
			throw new IllegalArgumentException("Diagnóstico não encontrado para alteração.");
		}
		validarCamposObrigatorios(diagnostico);
		validarMedicoEPaciente(diagnostico);
		diagnosticoRepository.save(diagnostico);
	}

	@Override
	public List<Diagnostico> listarPorMedico(String idMedico) {
		return diagnosticoRepository.findByIdMedico(idMedico);
	}

	@Override
	public List<Diagnostico> listarPorPaciente(String idPaciente) {
		return diagnosticoRepository.findByIdPaciente(idPaciente);
	}

	@Override
	public List<Diagnostico> listarPorTipo(TipoAtendimento tipo) {
		return diagnosticoRepository.findByTipoAtendimento(tipo);
	}

	private void validarCamposObrigatorios(Diagnostico diagnostico) {
		if (diagnostico == null) {
			throw new IllegalArgumentException("O objeto diagnóstico não pode ser nulo.");
		}
		if (diagnostico.getDataHora() == null) {
			throw new IllegalArgumentException("A data e hora do atendimento são obrigatórias.");
		}
		if (diagnostico.getTipoAtendimento() == null) {
			throw new IllegalArgumentException("O tipo de atendimento é obrigatório.");
		}
		if (estaVazio(diagnostico.getDescricao())) {
			throw new IllegalArgumentException("O campo Descrição é obrigatório.");
		}
		if (estaVazio(diagnostico.getSintomas())) {
			throw new IllegalArgumentException("O campo Sintomas é obrigatório.");
		}
		if (estaVazio(diagnostico.getMedicamentos())) {
			throw new IllegalArgumentException("O campo Medicamentos é obrigatório.");
		}
		if (estaVazio(diagnostico.getObservacoes())) {
			throw new IllegalArgumentException("O campo Observações é obrigatório.");
		}

		String cid = diagnostico.getCid10();
		if (!estaVazio(cid) && !cid.trim().matches("(?i)[A-Z]\\d{2}(\\.\\d{1,2})?")) {
			throw new IllegalArgumentException("Código CID-10 inválido. Use o formato A00 ou A00.0.");
		}
	}

	private void validarMedicoEPaciente(Diagnostico diagnostico) {
		if (estaVazio(diagnostico.getIdMedico())) {
			throw new IllegalArgumentException("É obrigatório selecionar um médico.");
		}
		if (!medicoRepository.existsById(diagnostico.getIdMedico())) {
			throw new IllegalArgumentException("O médico selecionado não está cadastrado.");
		}
		if (estaVazio(diagnostico.getIdPaciente())) {
			throw new IllegalArgumentException("É obrigatório selecionar um paciente.");
		}
		if (!pacienteRepository.existsById(diagnostico.getIdPaciente())) {
			throw new IllegalArgumentException("O paciente selecionado não está cadastrado.");
		}
	}

	private boolean estaVazio(String texto) {
		return texto == null || texto.trim().isEmpty();
	}
}