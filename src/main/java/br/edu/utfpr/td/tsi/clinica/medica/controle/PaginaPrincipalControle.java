package br.edu.utfpr.td.tsi.clinica.medica.controle;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PaginaPrincipalControle {
	
	@GetMapping("inicio")
	public String mostrarPaginaInicio() {
		return "paginaPrincipal";
	}
	
}
