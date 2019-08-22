package py.com.fuentepy.appfinanzasBackend.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank
    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @NotBlank
    @JsonProperty("username")
    @SerializedName("username")
    private String userName;

    @NotBlank
    @JsonProperty("password")
    @SerializedName("password")
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(@NotBlank String name, @NotBlank String userName, @NotBlank String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
