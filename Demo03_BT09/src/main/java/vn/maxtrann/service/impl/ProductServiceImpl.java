package vn.maxtrann.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.maxtrann.entity.Product;
import vn.maxtrann.repository.ProductRepository;
import vn.maxtrann.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository repository;
	
	public ProductServiceImpl(ProductRepository repo) {
		this.repository = repo;
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Product get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Product save(Product product) {
		return repository.save(product);
	}

	@Override
	public List<Product> listAll() {
		return repository.findAll();
	}
	
}
