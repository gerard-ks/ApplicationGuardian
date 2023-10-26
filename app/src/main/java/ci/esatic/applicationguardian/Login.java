package ci.esatic.applicationguardian;

import com.google.gson.annotations.SerializedName;

public class Login {

    private Integer status;
    private String nom;
    private String message;
    private String email;
    private String password;
    private String access_token;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getNom() {
        return nom;
    }
}
