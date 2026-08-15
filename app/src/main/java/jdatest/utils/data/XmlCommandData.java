package jdatest.utils.data;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "command")
public class XmlCommandData {
  @JacksonXmlProperty() private String name;
  @JacksonXmlProperty() private String description;
  @JacksonXmlProperty() private String context;
  @JacksonXmlProperty(localName = "subcommand") @JacksonXmlElementWrapper(localName = "subcommands") private List<XmlSubcommandData> subcommands;
  @JacksonXmlProperty(localName = "options") @JacksonXmlElementWrapper(localName = "options") private List<XmlOptionData> options;

  public List<XmlOptionData> getOptions() {
    return options;
  }

  public void setOptions(List<XmlOptionData> options) {
    this.options = options;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

    public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }
    
  public List<XmlSubcommandData> getSubcommands() {
    return subcommands;
  }

  public void setSubcommands(List<XmlSubcommandData> subcommands) {
    this.subcommands = subcommands;
  }
}
