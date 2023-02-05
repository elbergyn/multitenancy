package br.tec.dig.app.application.product;

import java.util.List;

import br.tec.dig.app.application.product.domain.ProductDomain;
import br.tec.dig.app.application.product.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product")
public class ProductController {
	
	private ProductDomain productDomain;
	
	public ProductController(ProductDomain productDomain) {
		this.productDomain = productDomain;
	}
	
	@GetMapping("/all")
	public List<ProductDto> getAllProductsOfClient(){
		return productDomain.getAllProducts();
	}

	@PostMapping
	public ProductDto create(@RequestBody ProductDto productDto){
		return productDomain.create(productDto);
	}
}
