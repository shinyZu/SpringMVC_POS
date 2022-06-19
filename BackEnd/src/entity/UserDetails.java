package entity;

public class UserDetails {
    private String email;
    private String password;

    public UserDetails() {
    }

    public UserDetails(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
