package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Especialidade;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

@Repository
public class MedicoDAOMongoDB implements MedicoDAO {

	private final MongoClient mongoClient;
	private final MongoDatabase database;
	private final MongoCollection<Document> collection;

	public MedicoDAOMongoDB() {
	this.mongoClient = MongoClients.create("mongodb://localhost:27017");
	this.database = mongoClient.getDatabase("clinica-medica");
	this.collection = database.getCollection("medico");
	}

	public void salvar(Medico medico) {
		Document doc = toDocument(medico);
		collection.insertOne(doc);
	}

	public void alterar(Medico medicoAtualizado) {
		Document filtro = new Document("cpf", medicoAtualizado.getCpf());
		Document novo = new Document("$set", toDocument(medicoAtualizado));
		collection.updateOne(filtro, novo);
	}

//	public void remover(String cpf) {
//		collection.deleteOne(new Document("cpf", cpf));
//	}
//
//	public Medico encontrar(String cpf) {
//		Document doc = collection.find(new Document("cpf", cpf)).first();
//		return doc != null ? fromDocument(doc) : null;
//	}

	public List<Medico> listarTodos() {
		List<Medico> lista = new ArrayList<>();
		for (Document doc : collection.find()) {
			lista.add(fromDocument(doc));
		}
		return lista;
	}

	private Document toDocument(Medico medico) {
		return new Document()
			.append("nome", medico.getNome())
			.append("email", medico.getEmail())
			.append("cpf", medico.getCpf())
			.append("crm", medico.getCrm())
			.append("especialidades",
				medico.getEspecialidades() != null
					? medico.getEspecialidades()
						.stream()
						.map(Enum::name)
						.toList()
					: new ArrayList<>());
	}


	private Medico fromDocument(Document doc) {
		Medico medico = new Medico();
		medico.setNome(doc.getString("nome"));
		medico.setEmail(doc.getString("email"));
		medico.setCpf(doc.getString("cpf"));
		medico.setCrm(doc.getString("crm"));
		
		@SuppressWarnings("unchecked")
		List<String> esp = (List<String>) doc.get("especialidades");
		if (esp != null) {
			medico.setEspecialidades(
				esp.stream()
					.map(Especialidade::valueOf)
					.toList()
			);
		}
		return medico;
	}
}
