package jdatest.utils.data;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlSubcommandData {
  @JacksonXmlProperty() private String name;
  @JacksonXmlProperty() private String description;
  @JacksonXmlProperty(localName = "option") @JacksonXmlElementWrapper(localName = "options") private List<XmlOptionData> options;
  public List<XmlOptionData> getOptions() {
    return options;
  }
  public void setOptions(List<XmlOptionData> options) {
    this.options = options;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
