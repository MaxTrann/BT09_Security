package vn.maxtrann.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.maxtrann.entity.UserInfo;
import vn.maxtrann.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        if (userInfo.getRoles() == null || userInfo.getRoles().isBlank()) {
            userInfo.setRoles("ROLE_USER");
        }
        repository.save(userInfo);
        return "Thêm user thành công!";
    }
}
