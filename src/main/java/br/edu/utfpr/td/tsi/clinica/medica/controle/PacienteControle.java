package br.edu.utfpr.td.tsi.clinica.medica.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PacienteControle {

	@GetMapping("cadastroPaciente")
	public String mostrarPaginaCadastroPaciente() {
		return "paciente/cadastroPaciente";
	}
	
	@PostMapping("cadastroPaciente")
	public String enviarFormCadastroPaciente() {
		return "paciente/cadastroPaciente";
	}
	
	@GetMapping("listagemPaciente")
	public String mostrarPaginaListagemPaciente() {
		return "paciente/listagemPaciente";
	}
}
