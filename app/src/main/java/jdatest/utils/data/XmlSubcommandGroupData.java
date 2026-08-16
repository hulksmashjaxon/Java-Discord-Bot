package jdatest.utils.data;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlSubcommandGroupData {
  @JacksonXmlProperty() private String name;
  @JacksonXmlProperty() private String description;
  @JacksonXmlProperty(localName = "subcommandGroup") @JacksonXmlElementWrapper(localName = "subcommandGroups") private List<XmlSubcommandData> subcommandGroups;
  public List<XmlSubcommandData> getSubcommandGroups() {
    return subcommandGroups;
  }
  public void setSubcommandGroups(List<XmlSubcommandData> subcommandGroups) {
    this.subcommandGroups = subcommandGroups;
  }
  @JacksonXmlProperty(localName = "subcommand") @JacksonXmlElementWrapper(localName = "subcommands") private List<XmlSubcommandData> subcommands;
  public List<XmlSubcommandData> getSubcommands() { return subcommands; }
  public void setSubcommands(List<XmlSubcommandData> subcommands) { this.subcommands = subcommands; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return name; }
  public void setDescription(String description) { this.description = description; }
}
