package microDon.models;

public class User {

    private String uuid;

    private String email;

    private String password;

    public String getUuid() {
        return uuid;
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

    public void setUuid(String uuid) {

        this.uuid = uuid;
    }
}
