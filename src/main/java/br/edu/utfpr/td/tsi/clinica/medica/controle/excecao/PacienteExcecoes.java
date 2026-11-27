package br.edu.utfpr.td.tsi.clinica.medica.controle.excecao;

import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.utfpr.td.tsi.clinica.medica.controle.PacienteControle;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Endereco;

@Order(1)
@ControllerAdvice(assignableTypes = PacienteControle.class)
public class PacienteExcecoes {

    @ExceptionHandler(ValidacaoException.class)
    public String tratar(ValidacaoException ex, Model model) {
    	
    	System.out.println("ENTROU NO PACIENTE EXCECOES!");
        System.out.println("ERRO: " + ex.getMessage());
        
        Paciente paciente = ex.getDados() instanceof Paciente p ? p : new Paciente();

        if (paciente.getEndereco() == null) {
            paciente.setEndereco(new Endereco()); 
        }

        model.addAttribute("pacienteEncontrado", paciente);  
        model.addAttribute("mensagemErro", ex.getMessage());

        return paciente.getId() == null
                ? "paciente/cadastroPaciente"
                : "paciente/edicaoPaciente";
    }
}