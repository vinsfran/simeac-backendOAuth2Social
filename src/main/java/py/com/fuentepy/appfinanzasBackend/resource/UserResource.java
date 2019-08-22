package py.com.fuentepy.appfinanzasBackend.resource;

import py.com.fuentepy.appfinanzasBackend.exception.ResourceNotFoundException;
import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;
import py.com.fuentepy.appfinanzasBackend.repository.MamusuarioRepository;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

    @Autowired
    private MamusuarioRepository mamusuarioRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public Mamusuario getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return mamusuarioRepository.findByLogUsuario(userPrincipal.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Mamusuario", "userName", userPrincipal.getUsername()));
    }
}
