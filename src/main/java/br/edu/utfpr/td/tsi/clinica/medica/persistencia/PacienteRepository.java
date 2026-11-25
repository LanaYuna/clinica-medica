package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
	Paciente findByCpf(String cpf);
}
