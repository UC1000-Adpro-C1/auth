package farrel.auth.model;

public class AuthResponse {
    private String token;
    private String message;
    private String username;
    private String password;
    private Role role;
    private long money;

    public AuthResponse(String token, String message, String username, String password, Role role, long money) {
        this.token = token;
        this.message = message;
        this.username = username;
        this.password = password;
        this.role = role;
        this.money = money;
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

    public Role getRole() {
        return role;
    }

    public void Role(Role role) {
        this.role = role;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
