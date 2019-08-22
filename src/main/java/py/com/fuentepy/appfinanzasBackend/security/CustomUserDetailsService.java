package py.com.fuentepy.appfinanzasBackend.security;

import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;
import py.com.fuentepy.appfinanzasBackend.exception.ResourceNotFoundException;
import py.com.fuentepy.appfinanzasBackend.repository.MamusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MamusuarioRepository mamusuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        Mamusuario mamusuario = mamusuarioRepository.findByLogUsuario(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Mamusuario not found with userName : " + userName)
                );

        return UserPrincipal.create(mamusuario);
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        Mamusuario mamusuario = mamusuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Mamusuario", "id", id)
        );

        return UserPrincipal.create(mamusuario);
    }
}
