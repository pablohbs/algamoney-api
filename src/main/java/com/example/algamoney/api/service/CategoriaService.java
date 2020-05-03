package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.orm.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	public Categoria atualizar(Categoria categoria, Long id) {
		Categoria categoriaSalva = buscaCategoriaPeloId(id);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaRepository.save(categoriaSalva);
	}
	
	private Categoria buscaCategoriaPeloId(Long id) {
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(id);
		if (categoriaSalva.get() == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva.get();
	}
}
