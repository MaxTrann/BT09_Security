package vn.maxtrann.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.maxtrann.entity.User;
import vn.maxtrann.model.LoginUserModel;
import vn.maxtrann.model.RegisterUserModel;
import vn.maxtrann.repository.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	public User signup(RegisterUserModel input) {
		User user = new User();
		user.setFullName(input.getFullname());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		
		return userRepository.save(user);
	}
	
	public User authenticate(LoginUserModel input) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						input.getEmail(), 
						input.getPassword()
						)
				);
		return userRepository.findByEmail(input.getEmail())
				.orElseThrow();
	}
	
}
