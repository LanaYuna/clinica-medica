package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Diagnostico;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.TipoAtendimento;

public interface DiagnosticoRepository extends MongoRepository<Diagnostico, String> {

	List<Diagnostico> findByIdMedico(String idMedico);
	List<Diagnostico> findByIdPaciente(String idPaciente);
	List<Diagnostico> findByTipoAtendimento(TipoAtendimento tipoAtendimento);

}