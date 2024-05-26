package farrel.auth.model;

public class AuthResponse {
    private String token;
    private String message;
    private String username;
    private String password;

    public AuthResponse(String token, String message, String username, String password) {
        this.token = token;
        this.message = message;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
