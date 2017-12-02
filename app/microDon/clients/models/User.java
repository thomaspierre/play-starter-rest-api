package microDon.clients.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String uuid;

    private String email;

    @JsonProperty("resource_uri")
    private String resourceUri;

    @JsonProperty("resource_type")
    private String resourceType;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
