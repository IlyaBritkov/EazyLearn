package eazy.learn.controller;

import eazy.learn.dto.request.AuthenticationRequestDTO;
import eazy.learn.dto.response.AuthenticationResponseDTO;
import eazy.learn.entity.User;
import eazy.learn.security.jwt.JwtTokenProvider;
import eazy.learn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Arrays.asList;

@AllArgsConstructor(onConstructor_ = @Autowired)

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("login")    // todo add global exception handling
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            String email = requestDTO.getEmail();
            String password = requestDTO.getPassword();
            System.out.println("email = " + email); // // TODO: 6/5/2021  
            System.out.println("password = " + password); // // TODO: 6/5/2021
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            String.format("User with email %s not found", email))); // todo maybe delete

            System.out.println("user = " + user); // // TODO: 6/5/2021
            String token = jwtTokenProvider.createToken(email, asList(user.getRole()));
            AuthenticationResponseDTO responseDTO = AuthenticationResponseDTO.builder()
                    .email(email)
                    .token(token)
                    .build();
            return ResponseEntity.ok(responseDTO);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
