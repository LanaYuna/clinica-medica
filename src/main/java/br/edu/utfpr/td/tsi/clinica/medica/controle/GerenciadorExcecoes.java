//package br.edu.utfpr.td.tsi.clinica.medica.controle;
//
//import br.edu.utfpr.td.tsi.clinica.medica.modelo.Especialidade;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GerenciadorExcecoes {
//	
//	@ExceptionHandler(IllegalArgumentException.class)
//    public String tratarErroValidacao(IllegalArgumentException ex, Model model) {
//        
//        model.addAttribute("mensagemErro", ex.getMessage());
//        model.addAttribute("especialidades", Especialidade.values());
//        return "cadastroMedico";
//    }
//
//    @ExceptionHandler(Exception.class)
//    public String tratarErroGenerico(Exception ex, Model model) {
//        model.addAttribute("mensagemErro", "Ocorreu um erro interno no sistema. Tente novamente.");
//        model.addAttribute("especialidades", Especialidade.values());
//        return "cadastroMedico";
//    }
//}
