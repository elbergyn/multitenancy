package br.tec.dig.app.application.product.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import br.tec.dig.app.application.product.dto.ProductDto;

@Component
class ProductDomainImpl implements ProductDomain {
	
	private ProductService productService;
	
	public ProductDomainImpl(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public List<ProductDto> getAllProducts() {
		return productService.getAllProducts();
	}

	@Override
	public ProductDto create(ProductDto productDto) {
		return productService.create(productDto);
	}
}
