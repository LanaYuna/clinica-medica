package br.edu.utfpr.td.tsi.clinica.medica.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Medico;

@Repository
@Profile("memoria")
public class MedicoDAOMemoria implements MedicoDAO {

    private final List<Medico> lista = new ArrayList<>();

    @Override
    public void salvar(Medico medico) {
        lista.add(medico);
    }
    
	@Override
	public Medico buscarPorId(String id) {
		return buscarPorCampo(Medico::getId, id);
	}
	
	@Override
    public Medico buscarPorCpf(String cpf) {
		return buscarPorCampo(Medico::getCpf, cpf);
    }
	
	@Override
    public Medico buscarPorCrm(String crm) {
		return buscarPorCampo(Medico::getCrm, crm);
    }
    
	@Override
    public Medico buscarPorEmail(String email) {
    	return buscarPorCampo(Medico::getEmail, email);
    }
	
	private Medico buscarPorCampo(Function<Medico, String> extratorDeCampo, String valorBuscado) {
	    if (valorBuscado == null) {
	        return null;
	    }
	    return lista.stream()
	            .filter(m -> {
	                String valorDoMedico = extratorDeCampo.apply(m);
	                return valorDoMedico != null && valorDoMedico.equals(valorBuscado);
	            })
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