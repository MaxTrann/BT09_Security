package vn.maxtrann.entity;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "products")
public class Product {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "nvarchar(255)") 
	private String name;
	@Column(columnDefinition = "nvarchar(255)") 
	private String brand;
	@Column(columnDefinition = "nvarchar(255)") 
	private String madein;
	private float price;
}
