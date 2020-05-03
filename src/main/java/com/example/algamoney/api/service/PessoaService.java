package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.orm.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Pessoa pessoa, Long id) {
		Pessoa pessoaSalva = buscaPessoaPeloId(id);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoaSalva);
	}
	
	public void atualizarPropriedadeAtivo(Long id, boolean ativo) {
		Pessoa pessoaSalva = buscaPessoaPeloId(id);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
	
	public Pessoa buscaPessoaPeloId(Long id) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
		if (pessoaSalva.get() == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva.get();
	}
}
