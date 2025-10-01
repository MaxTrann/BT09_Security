package vn.maxtrann.service.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.maxtrann.entity.Role;
import vn.maxtrann.entity.Users;
import vn.maxtrann.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email: " + username));

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
	    return roles.stream()
	                .map(role -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toList());
	}
	

}
