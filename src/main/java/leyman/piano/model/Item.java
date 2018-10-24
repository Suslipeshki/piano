
package leyman.piano.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "owner",
    "is_answered",
    "creation_date",
    "link",
    "title"
})
public class Item {

    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("is_answered")
    private Boolean isAnswered;
    @JsonProperty("creation_date")
    private Integer creationDate;
    @JsonProperty("link")
    private String link;
    @JsonProperty("title")
    private String title;

    public Item() {
    }

    public Item(String displayName, Boolean isAnswered, Integer creationDate, String link, String title) {
        this.owner = new Owner(displayName);
        this.isAnswered = isAnswered;
        this.creationDate = creationDate;
        this.link = link;
        this.title = title;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("is_answered")
    public Boolean getIsAnswered() {
        return isAnswered;
    }

    @JsonProperty("is_answered")
    public void setIsAnswered(Boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    @JsonProperty("creation_date")
    public Integer getCreationDate() { return creationDate; }

    @JsonProperty("creation_date")
    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

}
