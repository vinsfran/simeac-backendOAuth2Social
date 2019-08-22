package py.com.fuentepy.appfinanzasBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "mamusuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "dir_email")
})
public class Mamusuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "log_usuario")
    private String logUsuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cip_usuario")
    private String cipUsuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ape_usuario")
    private String apeUsuario;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_depen")
    private Integer codDepen;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nom_usuario")
    private String nomUsuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ind_activo")
    private String indActivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "usu_alta")
    private String usuAlta;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecAlta;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "usu_ultmod")
    private String usuUltmod;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_ultmod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUltmod;

    @Basic(optional = false)
    @JsonIgnore
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "pas_password")
    private String pasPassword;

    @Basic(optional = false)
    @Email
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "dir_email")
    private String dirEmail;


}
