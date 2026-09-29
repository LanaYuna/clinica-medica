package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

public class BancoDados {
	
	private List<Medico> lista = new ArrayList<Medico>();

	public void salvar(Medico medico) {
		String stringConexao = "mongodb://localhost:27017/";
		String dataBase = "clinica-medica";
		
		MongoClient clientClient = MongoClients.create(stringConexao);
		MongoDatabase mongoDataBase = clientClient.getDatabase(dataBase);
		MongoCollection<Document> colecaoMedicos = mongoDataBase.getCollection("medicos");
		
		Document documentoMongo = new Document();
		documentoMongo.append("nome", medico.getNome());
		documentoMongo.append("cpf", medico.getCpf());
		documentoMongo.append("email", medico.getEmail());
		documentoMongo.append("crm", medico.getCrm());
		
		colecaoMedicos.insertOne(documentoMongo);
	}
	
	public void alterar(Medico medico) {
		remover(medico.getCpf());
		salvar(medico);
	}
	
	public void remover(String cpf) {
		int indexCorrente = 0;
		int indexDesejado = 0;
		for(Medico medico : lista) {
			if(medico.getCpf().equals(cpf)) {
				indexDesejado = indexCorrente;
			} else {
				indexCorrente++;
			}
		}
		lista.remove(indexDesejado);
	}
	
	public List<Medico> listarTodos() {
		return lista;
	}
	
	public Medico encontrar(String cpf) {
		for(Medico medico : lista) {
			if(medico.getCpf().equals(cpf)) {
				return medico;
			}
		}
		return null;
	}
}
