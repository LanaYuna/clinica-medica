package br.edu.utfpr.td.tsi.clinica.medica.modelo;

public enum Especialidade {

    CLINICA_MEDICA("Clínica Médica"),
    PEDIATRIA("Pediatria"),
    GINECOLOGIA("Ginecologia"),
    CIRURGIA_GERAL("Cirurgia Geral"),
    CARDIOLOGIA("Cardiologia"),
    ORTOPEDIA("Ortopedia"),
    NEUROLOGIA("Neurologia"),
    PSIQUIATRIA("Psiquiatria"),
    DERMATOLOGIA("Dermatologia"),
    OFTALMOLOGIA("Oftalmologia");

    private final String descricao;

    Especialidade(String descricao) {
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
