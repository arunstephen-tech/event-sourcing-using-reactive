package com.event.sourcing.product;

import reactor.core.publisher.Flux;
import org.springframework.stereotype.Component;
import com.event.sourcing.product.model.Product;
import org.springframework.boot.CommandLineRunner;
import com.event.sourcing.product.repository.ProductRepository;

@Component
public class BootstrapCLQ implements CommandLineRunner {
	
	private final ProductRepository productRepository;
	
	public BootstrapCLQ(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inserting the Product Details to the DB");
		productRepository.deleteAll()
						 .thenMany(Flux.just(
								 new Product("HeadPhone123", "Sony Headphone", "SONYHP123", "Sony Headphone with sterio sound", 1200.50), 
								 new Product("HeadPhone234", "JBL Headphone", "JBLHP234", "JBL Headphone with base sound", 800.25), 
								 new Product("HeadPhone345", "Boat Headphone", "BOATHP345", "Boat Headphone with ultimate sound", 1000.75), 
								 new Product("MobilePhone123", "Samsung A10 Mobile", "SAMSUNGMP123", "Samsung Mobile with 8GB Ram and 32 GB Storage", 35000.50),
								 new Product("MobilePhone234", "Apple X10 Mobile", "AppleMP234", "Apple Mobile with 32GB Ram and 164 GB Storage", 50000.00), 
								 new Product("MobilePhone345", "Nokia8 Mobile", "NokiaMP345", "Nokia Mobile with 4GB Ram and 16 GB Storage", 12000.25), 
								 new Product("MobilePhone456", "Vivo v15 Mobile", "VIVOMP456", "Vico Mobile with 16GB Ram and 64 GB Storage", 23000.00), 
								 new Product("PowerBank123", "Lenovo Power Bank", "LenovoPB123", "Lenovo Power Bank with 10000mA", 12000.50),
								 new Product("PowerBank234", "Samsung Power Bank", "SamsungPB234", "Samsung Power Bank with 10000mA", 20000.00),
								 new Product("PowerBank345", "Huewai Power Bank", "HuewaiPB345", "Huewai Power Bank with 8000mA", 7000.50)))
						 .flatMap(productRepository::save)
						 .subscribe(null, null, () -> productRepository.findAll().subscribe(System.out::println));
	}

}
