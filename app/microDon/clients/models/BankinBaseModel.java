package microDon.clients.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankinBaseModel {

    private Integer id;

    @JsonProperty("resource_uri")
    private String resourceUri;

    @JsonProperty("resource_type")
    private String resourceType;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
