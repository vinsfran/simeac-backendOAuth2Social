package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;
import py.com.fuentepy.appfinanzasBackend.repository.MamusuarioRepository;
import py.com.fuentepy.appfinanzasBackend.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private MamusuarioRepository mamusuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public Mamusuario findById(String id) {
        Mamusuario mamusuario = null;
        Optional<Mamusuario> optional = mamusuarioRepository.findById(id);
        if (optional.isPresent()) {
            mamusuario = optional.get();
        }
        return mamusuario;
    }
}
