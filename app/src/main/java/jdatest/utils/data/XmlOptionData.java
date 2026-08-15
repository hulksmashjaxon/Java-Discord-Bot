package jdatest.utils.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlOptionData {
    @JacksonXmlProperty() private String type;
    @JacksonXmlProperty() private String name;
    @JacksonXmlProperty() private String description;
    @JacksonXmlProperty() private boolean required;
    @JacksonXmlProperty() private boolean autocomplete;

    public boolean isAutocomplete() {
        return autocomplete;
    }
    public void setAutocomplete(boolean autocomplete) {
        this.autocomplete = autocomplete;
    }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
}
