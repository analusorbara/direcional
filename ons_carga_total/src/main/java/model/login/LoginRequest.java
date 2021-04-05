package model.login;

public class LoginRequest {
    private String usuario;
    private String senha;


    // Getter Methods

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    // Setter Methods

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}