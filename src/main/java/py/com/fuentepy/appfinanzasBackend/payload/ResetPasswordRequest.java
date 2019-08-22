package py.com.fuentepy.appfinanzasBackend.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequest {

    @NotBlank
    @JsonProperty("username")
    @SerializedName("username")
    private String userName;

    @NotBlank
    @JsonProperty("password")
    @SerializedName("password")
    private String password;

    @NotBlank
    @JsonProperty("password_new")
    @SerializedName("password_new")
    private String passwordNew;

    @NotBlank
    @JsonProperty("password_repeat")
    @SerializedName("password_repeat")
    private String passwordRepeat;

}
