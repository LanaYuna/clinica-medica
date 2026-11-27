package br.edu.utfpr.td.tsi.clinica.medica.controle;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Endereco;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;
import br.edu.utfpr.td.tsi.clinica.medica.service.PacienteService;

@Controller
public class PacienteControle {
	
	@Autowired
	private PacienteService pacienteService;

	@GetMapping("cadastroPaciente")
	public String mostrarPaginaCadastroPaciente() {
		return "paciente/cadastroPaciente";
	}
	
	@PostMapping("cadastroPaciente")
	public String receberFormPaciente(Paciente paciente, Endereco endereco) {
		paciente.setId(UUID.randomUUID().toString());
		pacienteService.salvar(paciente, endereco);
		return "paginaPrincipal";
	}
	
	@GetMapping("listagemPaciente")
	public String mostrarPaginaListagemPaciente(Model model) {
		List<Paciente> pacientes = pacienteService.listarTodos();
		model.addAttribute("pacientes", pacientes);
		return "paciente/listagemPaciente";
	}
	
	@GetMapping("/removerPaciente")
	public String removerPaciente(@RequestParam("id") String id){		
		pacienteService.remover(id);
		return "redirect:/listagemPaciente";
	}
	 
	@GetMapping("/editarPaciente")
	public String mostraPaginaEdicaoPaciente(@RequestParam("id") String id, Model model) {
		Paciente pacienteEsperado = pacienteService.buscarPorId(id);
		model.addAttribute("pacienteEncontrado", pacienteEsperado);
		model.addAttribute("enderecoEncontrado", pacienteEsperado.getEndereco());
		return "paciente/edicaoPaciente";
	}
	
	@PostMapping("edicaoPaciente")
	public String processarEdicaoPaciente(Paciente paciente, Model model) {
		pacienteService.alterar(paciente);
		return "redirect:/listagemPaciente";
	}
	
}
