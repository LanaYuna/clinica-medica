package br.edu.utfpr.td.tsi.clinica.medica.controle;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.clinica.medica.dto.DiagnosticoListagemDTO;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Especialidade;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;
import br.edu.utfpr.td.tsi.clinica.medica.service.DiagnosticoService;
import br.edu.utfpr.td.tsi.clinica.medica.service.MedicoService;
import br.edu.utfpr.td.tsi.clinica.medica.service.PacienteService;

@Controller
public class DiagnosticoControle {
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private DiagnosticoService diagnosticoService;

	@GetMapping("cadastroDiagnostico")
	public String mostrarPaginaCadastroDiagnostico(Model model) {
		List<Medico> medicos = medicoService.listarTodos();
		List<Paciente> pacientes = pacienteService.listarTodos();
		List<TipoAtendimento> atendimentos = List.of(TipoAtendimento.values());
		
		model.addAttribute("medicos", medicos);
		model.addAttribute("pacientes", pacientes);
		model.addAttribute("tiposAtendimento", atendimentos);
		return "diagnostico/cadastroDiagnostico";
	}
	
	@PostMapping("cadastroDiagnostico")
	public String receberFormDiagnostico(Diagnostico diagnostico) {
		diagnostico.setId(UUID.randomUUID().toString());
		diagnosticoService.salvar(diagnostico);
		return "paginaPrincipal";
	}
	
	@GetMapping("listagemDiagnostico")
	public String mostrarPaginaListagemMedicos(Model model) {
		List<Medico> medicos = medicoService.listarTodos();
		List<Paciente> pacientes = pacienteService.listarTodos();
		List<TipoAtendimento> atendimentos = List.of(TipoAtendimento.values());
		List<DiagnosticoListagemDTO> diagnosticos = diagnosticoService.listarParaListagem();
		
		model.addAttribute("diagnosticos", diagnosticos);
		model.addAttribute("medicos", medicos);
		model.addAttribute("pacientes", pacientes);
		model.addAttribute("tiposAtendimento", atendimentos);
		return "diagnostico/listagemDiagnostico";
	}
	
	@GetMapping("/removerDiagnostico")
	public String removerMedico(@RequestParam("id") String id){		
		diagnosticoService.remover(id);
		return "redirect:/listagemDiagnostico";
	}
	 
	@GetMapping("/editarDiagnostico")
	public String mostraPaginaEdicaoDiagnostico(@RequestParam("id") String id, Model model) {
		Diagnostico diagnosticoEsperado = diagnosticoService.buscarPorId(id);
		// falta exibir paciente e medico
		model.addAttribute("diagnosticoEncontrado", diagnosticoEsperado);
		return "diagnostico/edicaoDiagnostico";
	}
	
	@PostMapping("edicaoDiagnostico")
	public String processarEdicaoDiagnostico(Diagnostico diagnostico, Model model) {
		diagnosticoService.alterar(diagnostico);
		return "redirect:/listagemDiagnostico";
	}
	
}
