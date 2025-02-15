package utils.helpers.elementHelper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementResponse {

    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("type")
    @Expose
    public String type;
}