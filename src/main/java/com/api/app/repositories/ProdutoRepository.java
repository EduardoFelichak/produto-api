package com.api.app.repositories;

import com.api.app.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> { }
