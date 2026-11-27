package br.edu.utfpr.td.tsi.clinica.medica.controle.excecao;

import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.utfpr.td.tsi.clinica.medica.controle.MedicoControle;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Especialidade;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

@Order(1)
@ControllerAdvice(assignableTypes = MedicoControle.class)
public class MedicoExcecoes {

    @ExceptionHandler(ValidacaoException.class)
    public String tratar(ValidacaoException ex, Model model) {

        Medico medico = ex.getDados() instanceof Medico m
                ? m
                : new Medico();


        model.addAttribute("medicoEncontrado", medico);
        model.addAttribute("especialidades", Especialidade.values());
        model.addAttribute("mensagemErro", ex.getMessage());

        return medico.getId() == null
                ? "medico/cadastroMedico"
                : "medico/edicaoMedico";
    }
}