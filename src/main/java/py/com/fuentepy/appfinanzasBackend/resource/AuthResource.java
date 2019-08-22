package py.com.fuentepy.appfinanzasBackend.resource;

import py.com.fuentepy.appfinanzasBackend.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.exception.BadRequestException;
import py.com.fuentepy.appfinanzasBackend.payload.ApiResponse;
import py.com.fuentepy.appfinanzasBackend.payload.AuthResponse;
import py.com.fuentepy.appfinanzasBackend.payload.LoginRequest;
import py.com.fuentepy.appfinanzasBackend.payload.SignUpRequest;
import py.com.fuentepy.appfinanzasBackend.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/loginSocial")
    public ResponseEntity<?> authenticateUserSocial(@Valid @RequestBody LoginRequest loginRequest) {
        Usuario usuario = null;

        Optional<Usuario> optional = usuarioRepository.findByUserName(loginRequest.getUserName());
        if (optional.isPresent()) {
            usuario = optional.get();
        } else {
            throw new BadRequestException("Email no existe.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(),
                        usuario.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        System.out.println("registerUser " + signUpRequest.toString());
        if (usuarioRepository.existsByUserName(signUpRequest.getUserName())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating usuario's account
        Usuario usuario = new Usuario();
        usuario.setName(signUpRequest.getName());
        usuario.setUserName(signUpRequest.getUserName());
        usuario.setPassword(signUpRequest.getPassword());

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario result = usuarioRepository.save(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/usuario/me")
                .buildAndExpand(result.getUserName()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Usuario registered successfully"));
    }

}
