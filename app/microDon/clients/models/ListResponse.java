package microDon.clients.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListResponse<T> {

    private List<T> resources;

    private Pagination pagination;

    public ListResponse() {
    }

    public List<T> getResources() {
        return resources;
    }

    public void setResources(List<T> resources) {
        this.resources = resources;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    private class Pagination {

        @JsonProperty("previous_uri")
        private String previousUri;

        @JsonProperty("next_uri")
        private String nextUri;

        public Pagination() {
        }

        public String getPreviousUri() {
            return previousUri;
        }

        public void setPreviousUri(String previousUri) {
            this.previousUri = previousUri;
        }

        public String getNextUri() {
            return nextUri;
        }

        public void setNextUri(String nextUri) {
            this.nextUri = nextUri;
        }
    }

}
