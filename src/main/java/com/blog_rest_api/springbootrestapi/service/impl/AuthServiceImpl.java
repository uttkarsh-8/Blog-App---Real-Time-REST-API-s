package com.blog_rest_api.springbootrestapi.service.impl;

import com.blog_rest_api.springbootrestapi.entity.Role;
import com.blog_rest_api.springbootrestapi.entity.User;
import com.blog_rest_api.springbootrestapi.payload.LoginDto;
import com.blog_rest_api.springbootrestapi.payload.RegisterDto;
import com.blog_rest_api.springbootrestapi.repository.RoleRepositoy;
import com.blog_rest_api.springbootrestapi.repository.UserRepository;
import com.blog_rest_api.springbootrestapi.security.JwtTokenProvider;
import com.blog_rest_api.springbootrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    // This is used to perform authentication operations.
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepositoy roleRepositoy;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepositoy roleRepositoy, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepositoy = roleRepositoy;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        // Attempt to authenticate the user with the provided username (or email) and password.
        // UsernamePasswordAuthenticationToken is a specific type of Authentication implementation used for this purpose.
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        // If authentication is successful, set the Authentication object in the SecurityContextHolder.
        // This effectively signs the user in, associating the authentication with the current security context (e.g., the current session).
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        // Return a success message indicating the user has been logged in successfully.
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        // checking if user exits in database or not
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new BadCredentialsException("Username already exits!!");
        }
        // checking if email exists or not
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new BadCredentialsException("Email already exits!!");
        }

        User user = new User();

        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepositoy.findByName("ROLE_USER");

        // Check if userRole is present
        userRole.ifPresent(roles::add);
        // Now you can set the roles to the user, assuming 'user' is an instance of a User class
// that has a setRoles method expecting a Set<Role>
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}

