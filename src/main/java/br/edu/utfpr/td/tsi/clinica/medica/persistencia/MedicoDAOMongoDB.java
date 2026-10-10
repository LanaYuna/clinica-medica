package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.context.annotation.Profile;
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
@Profile("mongoDB")
public class MedicoDAOMongoDB implements MedicoDAO {

	private final MongoClient mongoClient;
	private final MongoDatabase database;
	private final MongoCollection<Document> collection;

	public MedicoDAOMongoDB() {
		this.mongoClient = MongoClients.create("mongodb://localhost:27017");
		this.database = mongoClient.getDatabase("clinica-medica");
		this.collection = database.getCollection("medico");
		
		
        this.collection.createIndex(Indexes.ascending("cpf"), new IndexOptions().unique(true));
        this.collection.createIndex(Indexes.ascending("crm"), new IndexOptions().unique(true));
        this.collection.createIndex(Indexes.ascending("email"), new IndexOptions().unique(true));

	}

    @Override
    public void salvar(Medico medico) {
        collection.insertOne(toDocument(medico));
    }

	@Override
	public void alterar(Medico medicoAtualizado) {
		Document filtro = new Document("id", medicoAtualizado.getId());
		Document novo = new Document("$set", toDocument(medicoAtualizado));
		collection.updateOne(filtro, novo);
	}

	@Override
	public void remover(String id) {
		collection.deleteOne(new Document("id", id));
	}

	@Override
	public List<Medico> listarTodos() {
		List<Medico> lista = new ArrayList<>();
		for (Document doc : collection.find()) {
			lista.add(fromDocument(doc));
		}
		return lista;
	}
	@Override
    public Medico buscarPorId(String id) {
        return buscarPorCampo("id", id);
    }

    @Override
    public Medico buscarPorCpf(String cpf) {
        return buscarPorCampo("cpf", cpf);
    }

    @Override
    public Medico buscarPorCrm(String crm) {
        return buscarPorCampo("crm", crm);
    }

    @Override
    public Medico buscarPorEmail(String email) {
        return buscarPorCampo("email", email);
    }
    
    private Medico buscarPorCampo(String campo, String valor) {
        Document doc = collection.find(
            new Document(campo, valor)
        ).first();

        return doc != null ? fromDocument(doc) : null;
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
		
}
