package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

public interface MedicoRepository extends MongoRepository<Medico, String> {

	Medico findByCpf(String cpf);
	Medico findByCrm(String crm);
	Medico findByEmail(String email);

}
