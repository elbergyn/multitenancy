package br.tec.dig.app.application.product.domain;

import java.util.List;

import br.tec.dig.app.application.product.dto.ProductDto;

public interface ProductDomain {

	List<ProductDto> getAllProducts();

	ProductDto create(ProductDto productDto);
}
