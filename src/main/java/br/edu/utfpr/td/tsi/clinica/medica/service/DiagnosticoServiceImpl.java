package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.controle.excecao.ValidacaoException;
import br.edu.utfpr.td.tsi.clinica.medica.dto.DiagnosticoListagemDTO;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;
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
	
	@Autowired 
	private PacienteService pacienteService;
	
	@Autowired 
	private MedicoService medicoService;
	
	@Autowired
	private IndexadorDiagnostico indexador;

	@Override
	public void salvar(Diagnostico diagnostico) {
		validarCamposObrigatorios(diagnostico);
		validarMedicoEPaciente(diagnostico);
		diagnosticoRepository.save(diagnostico);
		indexador.indexar(diagnostico);
	}
	
	@Override
	public List<DiagnosticoListagemDTO> listarParaListagem(String idMedico, String idPaciente, TipoAtendimento tipo) {
		
		List<Diagnostico> diagnosticos;
		
		if (estaVazio(idMedico) && estaVazio(idPaciente) && tipo == null) {
			diagnosticos = diagnosticoRepository.findAll();
		} else {
			List<String> ids = indexador.procurar(idMedico, idPaciente, tipo);
			diagnosticos = diagnosticoRepository.findAllById(ids);
		}
		
		List<DiagnosticoListagemDTO> resultado = new ArrayList<>();
		
		for(Diagnostico diagnostico : diagnosticos) {
			
			Medico medico = medicoService.buscarPorId(diagnostico.getIdMedico());
			Paciente paciente = pacienteService.buscarPorId(diagnostico.getIdPaciente());
			
			DiagnosticoListagemDTO dto = new DiagnosticoListagemDTO();
			
			dto.setDiagnostico(diagnostico);
			dto.setNomeMedico(medico.getNome());
			dto.setNomePaciente(paciente.getNome());
			
			resultado.add(dto);
		}
		
		return resultado;
	}

	@Override
	public Diagnostico buscarPorId(String id) {
		return diagnosticoRepository.findById(id).orElse(null);
	}

	@Override
	public void remover(String id) {
		diagnosticoRepository.deleteById(id);
		indexador.remover(id);
	}

	@Override
	public void alterar(Diagnostico diagnostico) {
		if (diagnostico.getId() == null || !diagnosticoRepository.existsById(diagnostico.getId())) {
			throw new IllegalArgumentException("Diagnóstico não encontrado para alteração.");
		}
		validarCamposObrigatorios(diagnostico);
		validarMedicoEPaciente(diagnostico);
		diagnosticoRepository.save(diagnostico);
		indexador.indexar(diagnostico);
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
	        throw new ValidacaoException(
	            "O objeto diagnóstico não pode ser nulo.", null
	        );
	    }

	    exigir(diagnostico.getDataHora() == null,"A data e hora do atendimento são obrigatórias.", diagnostico);

	    exigir(diagnostico.getTipoAtendimento() == null,"O tipo de atendimento é obrigatório.", diagnostico);

	    exigir(estaVazio(diagnostico.getDescricao()),"O campo Descrição é obrigatório.", diagnostico);

	    exigir(estaVazio(diagnostico.getSintomas()),"O campo Sintomas é obrigatório.", diagnostico);

	    exigir(estaVazio(diagnostico.getMedicamentos()),"O campo Medicamentos é obrigatório.", diagnostico);

	    exigir(estaVazio(diagnostico.getObservacoes()), "O campo Observações é obrigatório.", diagnostico);

	    String cid = diagnostico.getCid10();

	    exigir(!estaVazio(cid) && !cid.trim().matches("(?i)[A-Z]\\d{2}(\\.\\d{1,2})?"),"Código CID-10 inválido. Use o formato A00 ou A00.0.",diagnostico);
	}

	private void validarMedicoEPaciente(Diagnostico diagnostico) {
	
	    exigir(estaVazio(diagnostico.getIdMedico()),"É obrigatório selecionar um médico.", diagnostico);
	
	    exigir(!estaVazio(diagnostico.getIdMedico()) && !medicoRepository.existsById(diagnostico.getIdMedico()),"O médico selecionado não está cadastrado.", diagnostico);
	
	    exigir(estaVazio(diagnostico.getIdPaciente()),"É obrigatório selecionar um paciente.", diagnostico);
	
	    exigir(!estaVazio(diagnostico.getIdPaciente()) && !pacienteRepository.existsById(diagnostico.getIdPaciente()),"O paciente selecionado não está cadastrado.", diagnostico);
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