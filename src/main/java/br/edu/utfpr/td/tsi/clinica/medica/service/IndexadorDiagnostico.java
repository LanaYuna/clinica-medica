package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpJdkSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;
import jakarta.annotation.PreDestroy;

@Component
public class IndexadorDiagnostico {

	private static final String URL_SOLR = "http://localhost:8983/solr";
	private static final String CORE = "diagnosticos";

	// Campos dinamicos "*_s" (tipo string, busca exata) ja existem no configset _default
	private static final String CAMPO_MEDICO = "idMedico_s";
	private static final String CAMPO_PACIENTE = "idPaciente_s";
	private static final String CAMPO_TIPO = "tipoAtendimento_s";

	private final SolrClient solr;

	public IndexadorDiagnostico() {
		this.solr = new HttpJdkSolrClient.Builder(URL_SOLR)
				.withDefaultCollection(CORE)
				.build();
	}

	public void indexar(Diagnostico diagnostico) {
		try {
			solr.add(criarDocumento(diagnostico));
			solr.commit();
		} catch (SolrServerException | IOException e) {
			throw new IllegalStateException("Falha ao indexar o diagnóstico no Solr.", e);
		}
	}

	public void indexar(List<Diagnostico> diagnosticos) {
		List<SolrInputDocument> documentos = new ArrayList<>();
		for (Diagnostico diagnostico : diagnosticos) {
			documentos.add(criarDocumento(diagnostico));
		}
		try {
			solr.add(documentos);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			throw new IllegalStateException("Falha ao indexar os diagnósticos no Solr.", e);
		}
	}

	public void remover(String id) {
		try {
			solr.deleteById(id);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			throw new IllegalStateException("Falha ao remover o diagnóstico do Solr.", e);
		}
	}

	public List<String> procurar(String idMedico, String idPaciente, TipoAtendimento tipo) {
		List<String> ids = new ArrayList<>();

		SolrQuery query = new SolrQuery("*:*");
		query.setFields("id");
		query.setRows(10000); // o padrao do Solr e 10 resultados

		adicionarFiltro(query, CAMPO_MEDICO, idMedico);
		adicionarFiltro(query, CAMPO_PACIENTE, idPaciente);
		if (tipo != null) {
			adicionarFiltro(query, CAMPO_TIPO, tipo.name());
		}

		try {
			QueryResponse response = solr.query(query);
			for (SolrDocument doc : response.getResults()) {
				ids.add(doc.getFieldValue("id").toString());
			}
		} catch (SolrServerException | IOException e) {
			throw new IllegalStateException("Falha ao consultar o Solr.", e);
		}
		return ids;
	}

	private void adicionarFiltro(SolrQuery query, String campo, String valor) {
		if (valor != null && !valor.trim().isEmpty()) {
			// {!term} faz comparacao exata, sem precisar escapar o "-" do UUID
			query.addFilterQuery("{!term f=" + campo + "}" + valor.trim());
		}
	}

	private SolrInputDocument criarDocumento(Diagnostico diagnostico) {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", diagnostico.getId());
		document.addField(CAMPO_MEDICO, diagnostico.getIdMedico());
		document.addField(CAMPO_PACIENTE, diagnostico.getIdPaciente());
		document.addField(CAMPO_TIPO, diagnostico.getTipoAtendimento().name());
		return document;
	}

	@PreDestroy
	public void fechar() throws IOException {
		solr.close();
	}
}
