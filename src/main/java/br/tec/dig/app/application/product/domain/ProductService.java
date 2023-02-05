package br.tec.dig.app.application.product.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.tec.dig.app.application.product.dto.ProductDto;

@Service
@Transactional
class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<ProductDto> getAllProducts() {
		return productRepository.findAll().stream()
				.map(ProductMapper::mapToDto)
				.collect(toList());
	}
	
	public ProductDto create(ProductDto productDto){
		return Optional.ofNullable(productDto)
				.map(ProductMapper::mapToEntity)
				.map(productRepository::save)
				.map(ProductMapper::mapToDto)
				.orElseThrow(() -> new PersistenceException("Não foi possível gravar as informações"));
	}
}
