package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;

public interface UsuarioService {

    Mamusuario findById(String id);
}
