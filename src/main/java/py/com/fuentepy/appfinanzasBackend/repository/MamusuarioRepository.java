package py.com.fuentepy.appfinanzasBackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;

import java.util.Optional;

@Repository
public interface MamusuarioRepository extends JpaRepository<Mamusuario, String> {

    Optional<Mamusuario> findByLogUsuario(String logUsuario);

    Boolean existsByLogUsuario(String logUsuario);

}
