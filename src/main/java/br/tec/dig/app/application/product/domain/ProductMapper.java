package br.tec.dig.app.application.product.domain;

import java.math.BigDecimal;
import java.util.Optional;

import br.tec.dig.app.application.database.model.entities.Product;
import br.tec.dig.app.application.product.dto.ProductDto;

public class ProductMapper {

	public static ProductDto mapToDto(Product product) {
		return ProductDto.builder()
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}

	public static Product mapToEntity(ProductDto product) {
		return Product.builder()
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
