package br.edu.utfpr.td.tsi.clinica.medica.modelo;

public enum TipoAtendimento {

	CONSULTA("Consulta"),
	RETORNO("Retorno"),
	EMERGENCIA("Emergência"),
	EXAME("Exame");

	private final String descricao;

	TipoAtendimento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}