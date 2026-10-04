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

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

@Repository
public class MedicoDAOMongoDB implements MedicoDAO{
	
	@Value("${mongodb.connection-string:mongodb://localhost:27017/}")
	private String connectionString;

	@Value("${mongodb.database:clinica-medica}")
	private String databaseName;
	
//	@Value("${mongodb.connection-string}")
//	private String connectionString;
//	
//	@Value("${mongodb.database}")
//	private String databaseName;

	@Override
	public void salvar(Medico medico) {
		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection("medico");

			Document documentMedico = new Document();
			medico.setId(UUID.randomUUID().toString());
			documentMedico.append("_id", medico.getId());
			documentMedico.append("nome", medico.getNome());
			documentMedico.append("email", medico.getEmail());
			documentMedico.append("especialidades", 
				    medico.getEspecialidades().stream().map(Enum::name).toList());

			collection.insertOne(documentMedico);
		}
	}

	@Override
	public Medico encontrar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Medico medico) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Medico> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
