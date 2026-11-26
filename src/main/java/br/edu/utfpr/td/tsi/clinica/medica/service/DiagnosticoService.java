package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import br.edu.utfpr.td.tsi.clinica.medica.dto.DiagnosticoListagemDTO;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;

public interface DiagnosticoService {
	void salvar(Diagnostico diagnostico);
	List<DiagnosticoListagemDTO> listarParaListagem();
	Diagnostico buscarPorId(String id);
	void remover(String id);
	void alterar(Diagnostico diagnostico);
	List<Diagnostico> listarPorMedico(String idMedico);
	List<Diagnostico> listarPorPaciente(String idPaciente);
	List<Diagnostico> listarPorTipo(TipoAtendimento tipo);
}