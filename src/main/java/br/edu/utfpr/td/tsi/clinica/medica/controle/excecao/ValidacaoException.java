package br.edu.utfpr.td.tsi.clinica.medica.controle.excecao;

public class ValidacaoException extends RuntimeException {

    private final transient Object dados;

    public ValidacaoException(String mensagem, Object dados) {
        super(mensagem);
        this.dados = dados;
    }

    public Object getDados() {
        return dados;
    }
}

