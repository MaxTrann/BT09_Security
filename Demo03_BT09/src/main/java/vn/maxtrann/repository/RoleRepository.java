package vn.maxtrann.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.maxtrann.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Role getUserByName(@Param("name") String name);

	Optional<Role> findByName(String name);

}
