package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
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
		
		try {
	        this.collection.createIndex(Indexes.ascending("cpf"), new IndexOptions().unique(true));
	        this.collection.createIndex(Indexes.ascending("crm"), new IndexOptions().unique(true));
	        this.collection.createIndex(Indexes.ascending("email"), new IndexOptions().unique(true));
	    } catch (Exception e) {
	        System.err.println("Aviso: Não foi possível criar os índices únicos no MongoDB: " + e.getMessage());
	    }
	}

	public void salvar(Medico medico) {
		validarCamposObrigatorios(medico);
		
		if(buscarPorId(medico.getCpf()) != null) {
			throw new IllegalArgumentException("Já existe um médico cadastrado com este CPF!");
		}
		if(buscarPorCrm(medico.getCrm()) != null) {
			throw new IllegalArgumentException("Já existe um médico cadastrado com este CRM!");
		}
		if(buscarPorEmail(medico.getEmail()) != null) {
			throw new IllegalArgumentException("Já existe um médico cadastrado com este E-mail!");
		}
		
		Document doc = toDocument(medico);
		collection.insertOne(doc);
	}

	public void alterar(Medico medicoAtualizado) {
		Document filtro = new Document("id", medicoAtualizado.getId());
		Document novo = new Document("$set", toDocument(medicoAtualizado));
		collection.updateOne(filtro, novo);
	}

	public void remover(String id) {
		collection.deleteOne(new Document("id", id));
	}

	public List<Medico> listarTodos() {
		List<Medico> lista = new ArrayList<>();
		for (Document doc : collection.find()) {
			lista.add(fromDocument(doc));
		}
		return lista;
	}

	private Document toDocument(Medico medico) {
		return new Document()
			.append("id", medico.getId())
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
		
		medico.setId(doc.getString("id"));
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
	
	public Medico buscarPorId(String id) {
		Document doc = collection.find(new Document("id", id)).first();
		return doc != null ? fromDocument(doc) : null;
	}
	
	public Medico buscarPorCrm(String crm) {
		Document doc = collection.find(new Document("crm", crm)).first();
		return doc != null ? fromDocument(doc) : null;
	}
	
	public Medico buscarPorEmail(String email) {
		Document doc = collection.find(new Document("email", email)).first();
		return doc != null ? fromDocument(doc) : null;
	}
	
	private void validarCamposObrigatorios(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("O objeto médico não pode ser nulo.");
        }
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Nome é obrigatório.");
        }
        if (medico.getCpf() == null || medico.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo CPF é obrigatório.");
        }
        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo CRM é obrigatório.");
        }
        if (medico.getEmail() == null || medico.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo E-mail é obrigatório.");
        }
        if (medico.getEspecialidades() == null || medico.getEspecialidades().isEmpty()) {
            throw new IllegalArgumentException("O médico deve possuir pelo menos uma especialidade.");
        }
    }
}
