package py.com.fuentepy.appfinanzasBackend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import py.com.fuentepy.appfinanzasBackend.entity.Mamusuario;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {

    private String logUsuario;
    private String cipUsuario;
    private String apeUsuario;
    private String nomUsuario;
    private String pasPassword;
    private String dirEmail;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Mamusuario mamusuario, Collection<? extends GrantedAuthority> authorities) {
        this.logUsuario = mamusuario.getLogUsuario();
        this.cipUsuario = mamusuario.getCipUsuario();
        this.apeUsuario = mamusuario.getApeUsuario();
        this.nomUsuario = mamusuario.getNomUsuario();
        this.pasPassword = mamusuario.getPasPassword();
        this.dirEmail = mamusuario.getDirEmail();
        this.authorities = authorities;
    }

    public static UserPrincipal create(Mamusuario mamusuario) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new UserPrincipal(mamusuario, authorities);
    }

    public static UserPrincipal create(Mamusuario mamusuario, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(mamusuario);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getUsername() {
        return logUsuario;
    }

    public String getCipUsuario() {
        return this.cipUsuario;
    }

    public String getLastName() {
        return this.apeUsuario;
    }

    @Override
    public String getName() {
        return this.nomUsuario;
    }

    @Override
    public String getPassword() {
        return pasPassword;
    }

    public String getEmail() {
        return dirEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


}
