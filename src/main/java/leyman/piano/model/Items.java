
package leyman.piano.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "items"
})
public class Items {

    @JsonProperty("items")
    private List<Item> items = null;

    public Items() {

    }
    public Items(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("items")
    public List<Item> getItems() { return items; }

    @JsonProperty("items")
    public void setItems(List<Item> items) { this.items = items; }

}
