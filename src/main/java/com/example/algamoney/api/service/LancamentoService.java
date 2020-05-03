package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.orm.Lancamento;
import com.example.algamoney.api.model.orm.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		if (pessoa.get() == null || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento atualizar(Lancamento lancamento, Long id) {
		Lancamento lancamentoSalvo = buscaLancamentoPeloId(id);
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	private Lancamento buscaLancamentoPeloId(Long id) {
		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(id);
		if (lancamentoSalvo.get() == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo.get();
	}

}
