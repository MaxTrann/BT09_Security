package vn.maxtrann.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "users")
public class Users {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;

  @Email
  private String email;
  @Column(length = 60, columnDefinition = "nvarchar(100) not null")
  private String password;

  private boolean enabled;

  @ManyToMany(cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
}
