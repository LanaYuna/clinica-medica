package br.edu.utfpr.td.tsi.clinica.medica.controle.excecao;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class GerenciadorExcecoes {
	
    @ExceptionHandler(Exception.class)
    public String tratarErroGenerico(Exception ex, Model model) {

    	ex.printStackTrace();
        model.addAttribute("mensagemErro", "Ocorreu um erro interno no sistema. Tente novamente.");
        return "paginaPrincipal";
    }
    
}
