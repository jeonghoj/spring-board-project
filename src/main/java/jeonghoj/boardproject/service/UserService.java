package jeonghoj.boardproject.service;

import jeonghoj.boardproject.domain.User;
import jeonghoj.boardproject.domain.dto.RegisterRequestDto;
import jeonghoj.boardproject.exception.DuplicateUserException;
import jeonghoj.boardproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(RegisterRequestDto registerRequestDto){

        User user = userRepository.findByUsername(registerRequestDto.getUsername());
        if(user != null) throw new DuplicateUserException();
        user = User
                .builder()
                .username(registerRequestDto.getUsername())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .createdDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    public boolean checkUserEmailAvailable(String username){
        return (userRepository.findByUsername(username).getUsername().isEmpty());
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
