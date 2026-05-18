package org.example.interfaces;

import org.example.enums.TipoPerfil;

public interface UsuarioAutenticavel {
    String getEmail();
    String getSenha();
    TipoPerfil getTipoPerfil();
}
