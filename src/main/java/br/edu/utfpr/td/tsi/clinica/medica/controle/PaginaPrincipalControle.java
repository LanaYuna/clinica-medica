package br.edu.utfpr.td.tsi.clinica.medica.controle;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Especialidade;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.MedicoDAO;

@Controller
public class PaginaPrincipalControle {
	
	@Autowired
	private MedicoDAO dao;


	@GetMapping("inicio")
	public String mostrarPaginaInicio() {
		return "paginaPrincipal";
	}
	
	@GetMapping("cadastroMedico")
	public String mostrarPaginaCadastroMedico(Model model) {
		model.addAttribute("especialidades", Especialidade.values());
		return "cadastroMedico";
	}
	
	@PostMapping("cadastroMedico")
	public String receberFormMedico(Medico medico) {
		dao.salvar(medico); 
		return "paginaPrincipal";
	}
	
	@GetMapping("listagemMedico")
	public String mostrarPaginaListagemMedicos(Model model) {
		List<Medico> medicos = dao.listarTodos();
		model.addAttribute("medicos", medicos);
		return "listagemMedico";
	}
	
	@GetMapping("/removerMedico")
	public String removerMedico(String cpf){		
		dao.remover(cpf);
		return "listagemMedico";
	}
	
	@GetMapping("/editarMedico")
	public String mostraPaginaEdicaoMedico(String cpf, Model model) {
		Medico medicoEsperado = dao.buscarPorCpf(cpf);
		model.addAttribute("medicoEncontrado", medicoEsperado);
		model.addAttribute("especialidades", Especialidade.values());
		return "edicaoMedico";
	}
	
	@PostMapping("edicaoMedico")
	public String processarEdicaoMedicos(Medico medico, Model model) {
		dao.alterar(medico);
		return "redirect:/listagemMedico";
	}
}
