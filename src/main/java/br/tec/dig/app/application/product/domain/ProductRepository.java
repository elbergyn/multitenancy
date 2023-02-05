package br.tec.dig.app.application.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.tec.dig.app.application.database.model.entities.Product;

@Repository
interface ProductRepository extends JpaRepository<Product, Long>{

	
}
