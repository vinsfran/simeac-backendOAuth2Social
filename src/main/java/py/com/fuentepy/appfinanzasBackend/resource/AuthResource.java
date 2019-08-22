package py.com.fuentepy.appfinanzasBackend.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;
import py.com.fuentepy.appfinanzasBackend.exception.BadRequestException;
import py.com.fuentepy.appfinanzasBackend.payload.*;
import py.com.fuentepy.appfinanzasBackend.repository.MamusuarioRepository;
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

    private static final Log LOG = LogFactory.getLog(AuthResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MamusuarioRepository mamusuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Mamusuario mamusuario = null;
        Optional<Mamusuario> optional = mamusuarioRepository.findByLogUsuario(loginRequest.getUserName());
        if (optional.isPresent()) {
            mamusuario = optional.get();
            LOG.info("USU: " + mamusuario.getLogUsuario());
            LOG.info("PAS: " + mamusuario.getPasPassword());
        } else {
            throw new BadRequestException("Email no existe.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        //BASE64 antes de enviar
//        token = StringUtil.encodeBase64(token);
        return ResponseEntity.ok(new AuthResponse(token, mamusuario.getLogUsuario()));
    }

    @PutMapping("/activate")
    public ResponseEntity<?> activateUser(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        Mamusuario mamusuario = null;
        Optional<Mamusuario> optional = mamusuarioRepository.findByLogUsuario(resetPasswordRequest.getUserName());
        if (optional.isPresent()) {
            mamusuario = optional.get();
            LOG.info("USU: " + mamusuario.getLogUsuario());
            LOG.info("PAS: " + mamusuario.getPasPassword());
        } else {
            throw new BadRequestException("Mamusuario no existe.");
        }
        // Creating mamusuario's account
//        Mamusuario mamusuario = new Mamusuario();
//        mamusuario.setName(signUpRequest.getName());
//        mamusuario.setUserName(signUpRequest.getUserName());
//        mamusuario.setPassword(signUpRequest.getPassword());

        mamusuario.setPasPassword(passwordEncoder.encode(resetPasswordRequest.getPasswordNew()));

        Mamusuario result = mamusuarioRepository.save(mamusuario);

        return ResponseEntity.ok(new ApiResponse(true, "Mamusuario " + result.getLogUsuario() + " Activado"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        System.out.println("registerUser " + signUpRequest.toString());
        if (mamusuarioRepository.existsByLogUsuario(signUpRequest.getUserName())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating mamusuario's account
        Mamusuario mamusuario = new Mamusuario();
        mamusuario.setLogUsuario(signUpRequest.getUserName());
        mamusuario.setLogUsuario(signUpRequest.getName());
        mamusuario.setPasPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Mamusuario result = mamusuarioRepository.save(mamusuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/mamusuario/me")
                .buildAndExpand(result.getLogUsuario()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Mamusuario registered successfully"));
    }

}
