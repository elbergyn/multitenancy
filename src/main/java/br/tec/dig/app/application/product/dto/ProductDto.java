package br.tec.dig.app.application.product.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

	private String name;
	private String description;
	private BigDecimal price;
	
}
