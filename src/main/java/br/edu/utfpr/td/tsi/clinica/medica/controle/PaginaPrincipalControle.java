package br.edu.utfpr.td.tsi.clinica.medica.controle;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.BancoDados;
import ch.qos.logback.core.model.Model;

@Controller
public class PaginaPrincipalControle {
	
	BancoDados bancoDados = new BancoDados();

	@GetMapping("inicio")
	public String mostrarPaginaInicio() {
		return "paginaPrincipal";
	}
	
	@GetMapping("cadastroMedico")
	public String mostrarPaginaCadastroMedico() {
		return "cadastroMedico";
	}
	
	@PostMapping("cadastroMedico")
	public String receberFormMedico(Medico medico) {

		bancoDados.salvar(medico);
		return "pagina";
	}
	
	@GetMapping("listagemMedico")
	public String mostrarPaginaListagemMedicos(Model model) {
		List<Medico> listaGravadaNoBD = bancoDados.listarTodos();
		model.addAttribute("medicos", listaGravadaNoBD);
		return "listagemMedico";
	}
	
	@GetMapping("/removerMedico")
	public String removerMedico(String cpf, Model model){
		System.out.println("remover medico de cpf: " + cpf);
		bancoDados.remover(cpf);
		return "redirect: /listagemMedico";
	}
	
	@GetMapping("/editarMedico")
	public String mostraPaginaEdicaoMedico(String cpf, Model model) {
		Medico medicoEsperado = bancoDados.encontrar(cpf);
		model.addAttribute("medicoEncontrado", medicoEsperado);
		return "edicaoMedico";
	}
	
	public String processarEdicaoMedicos(Medico medico, Model model) {
		bancoDados.alterar(medico);
		List<Medico> medicos = bancoDados.listarTodos();
		return "listagemMedico";
	}
}
