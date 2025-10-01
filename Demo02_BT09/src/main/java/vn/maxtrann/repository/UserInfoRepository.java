package vn.maxtrann.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.maxtrann.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	Optional<UserInfo> findByName(String username);

}
