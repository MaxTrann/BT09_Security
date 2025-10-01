package vn.maxtrann.entity;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "roles")
public class Role {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String name; // ví dụ: ROLE_ADMIN, ROLE_USER
}
