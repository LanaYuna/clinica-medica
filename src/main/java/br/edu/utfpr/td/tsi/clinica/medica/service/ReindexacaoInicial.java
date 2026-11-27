package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.persistencia.DiagnosticoRepository;

// Ao subir a aplicacao, envia para o Solr os diagnosticos que ja estao no MongoDB
@Component
public class ReindexacaoInicial implements ApplicationRunner {

	@Autowired
	private DiagnosticoRepository diagnosticoRepository;

	@Autowired
	private IndexadorDiagnostico indexador;

	@Override
	public void run(ApplicationArguments args) {
		List<Diagnostico> diagnosticos = diagnosticoRepository.findAll();
		if (diagnosticos.isEmpty()) {
			return;
		}
		try {
			indexador.indexar(diagnosticos);
			System.out.println("Solr: " + diagnosticos.size() + " diagnóstico(s) indexado(s).");
		} catch (IllegalStateException e) {
			System.out.println("Solr indisponível, reindexação ignorada: " + e.getMessage());
		}
	}
}
