package br.edu.utfpr.td.tsi.clinica.medica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.clinica.medica.modelo.Endereco;
import br.edu.utfpr.td.tsi.clinica.medica.modelo.Paciente;

@Service
public class PacienteServiceImpl implements PacienteService{

	@Override
	public void salvar(Paciente paciente, Endereco endereco) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Paciente> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paciente buscarPorId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Paciente paciente, Endereco endereco) {
		// TODO Auto-generated method stub
		
	}
	
	public void validarCamposObrigatorios() {
		
	}
	
	public void validarUnicidade() {
		
	}

}
