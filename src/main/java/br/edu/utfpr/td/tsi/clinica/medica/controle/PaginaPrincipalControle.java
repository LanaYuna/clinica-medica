package br.edu.utfpr.td.tsi.clinica.medica.controle;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Especialidade;
import br.edu.utfpr.td.tsi.clinica.medica.service.MedicoService;

@Controller
public class PaginaPrincipalControle {
	
	@Autowired
	private MedicoService medicoService;

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
		medico.setId(UUID.randomUUID().toString());
		medicoService.salvar(medico);
		return "paginaPrincipal";
	}
	
	@GetMapping("listagemMedico")
	public String mostrarPaginaListagemMedicos(Model model) {
		List<Medico> medicos = medicoService.listarTodos();
		model.addAttribute("medicos", medicos);
		return "listagemMedico";
	}
	
	@GetMapping("/removerMedico")
	public String removerMedico(@RequestParam("id") String id){		
		medicoService.remover(id);
		return "redirect:/listagemMedico";
	}
	 
	@GetMapping("/editarMedico")
	public String mostraPaginaEdicaoMedico(@RequestParam("id") String id, Model model) {
		Medico medicoEsperado = medicoService.buscarPorId(id);
		model.addAttribute("medicoEncontrado", medicoEsperado);
		model.addAttribute("especialidades", Especialidade.values());
		return "edicaoMedico";
	}
	
	@PostMapping("edicaoMedico")
	public String processarEdicaoMedicos(Medico medico, Model model) {
		medicoService.alterar(medico);
		return "redirect:/listagemMedico";
	}
}
