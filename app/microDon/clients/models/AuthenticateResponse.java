package microDon.clients.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class AuthenticateResponse {

    private User user;

    @JsonProperty("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @JsonProperty("expires_at")
    private ZonedDateTime expiresAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
