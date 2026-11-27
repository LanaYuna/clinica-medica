package br.edu.utfpr.td.tsi.clinica.medica.controle.excecao;

import java.util.List;

import org.springframework.core.annotation.Order;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.utfpr.td.tsi.clinica.medica.controle.DiagnosticoControle;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;
import br.edu.utfpr.td.tsi.clinica.medica.service.MedicoService;
import br.edu.utfpr.td.tsi.clinica.medica.service.PacienteService;

@Order(1)
@ControllerAdvice(assignableTypes = DiagnosticoControle.class)
public class DiagnosticoExcecoes {

    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    public DiagnosticoExcecoes(PacienteService pacienteService, MedicoService medicoService) {
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }

    @ExceptionHandler(ValidacaoException.class)
    public String tratar(ValidacaoException ex, Model model) {
        Diagnostico diagnostico = ex.getDados() instanceof Diagnostico d ? d : new Diagnostico();

        model.addAttribute("diagnosticoEncontrado", diagnostico);
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("medicos", medicoService.listarTodos());
        model.addAttribute("tiposAtendimento", List.of(TipoAtendimento.values()));
        model.addAttribute("mensagemErro", ex.getMessage());

        return diagnostico.getId() == null
                ? "diagnostico/cadastroDiagnostico"
                : "diagnostico/edicaoDiagnostico";
    }
}
