package py.com.fuentepy.appfinanzasBackend.payload;

import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String pass;

    public AuthResponse(String token, String password) {
        this.accessToken = token;
        this.pass = password;
    }
}
