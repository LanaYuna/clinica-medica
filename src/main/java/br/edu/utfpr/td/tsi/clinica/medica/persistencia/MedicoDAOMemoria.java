package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

@Repository
@Profile("memoria")
public class MedicoDAOMemoria implements MedicoDAO {

    private final List<Medico> lista = new ArrayList<>();

    @Override
    public void salvar(Medico medico) {
    	
    	if(buscarPorCpf(medico.getCpf()) != null) {
			throw new IllegalArgumentException("Já existe um médico cadastrado com este CPF!");
		}
		if(buscarPorCrm(medico.getCrm()) != null) {
			throw new IllegalArgumentException("Já existe um médico cadastrado com este CRM!");
		}
		if(buscarPorEmail(medico.getEmail()) != null) {
			throw new IllegalArgumentException("Já existe um médico cadastrado com este E-mail!");
		}
		
        lista.add(medico);
    }
    
	@Override
	public Medico buscarPorId(String id) {
		return lista.stream()
				.filter(m -> m.getId() != null && m.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

    public Medico buscarPorCpf(String cpf) {
        return lista.stream()
                .filter(m -> m.getCpf() != null && m.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    public Medico buscarPorCrm(String crm) {
    	return lista.stream()
                .filter(m -> m.getCrm() != null && m.getCrm().equals(crm))
                .findFirst()
                .orElse(null);
    }
    
    public Medico buscarPorEmail(String email) {
    	return lista.stream()
                .filter(m -> m.getEmail() != null && m.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void remover(String id) {
        lista.removeIf(m -> m.getId() != null && m.getId().equals(id));
    }

    @Override
    public void alterar(Medico medico) {
        Medico medicoExistente = buscarPorId(medico.getId());
        if (medicoExistente != null) {
            int indice = lista.indexOf(medicoExistente);
            lista.set(indice, medico);
        }
    }

    @Override
    public List<Medico> listarTodos() {
        return new ArrayList<>(lista);
    }

}