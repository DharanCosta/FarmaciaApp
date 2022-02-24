package com.farmaciaApp.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.farmaciaApp.demo.model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository <ProdutoModel, Long> {
	public List <ProdutoModel> findAllByNomeContainingIgnoreCase (String nome);

}
