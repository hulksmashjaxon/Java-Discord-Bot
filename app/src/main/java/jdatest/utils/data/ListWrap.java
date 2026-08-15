package jdatest.utils.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JacksonXmlRootElement(localName = "commands")
public class ListWrap {
  @JacksonXmlProperty(localName = "command") @JacksonXmlElementWrapper(useWrapping = false)
  private List<XmlCommandData> commands;

  public List<XmlCommandData> getCommands() { return commands; }
  public void setCommands(List<XmlCommandData> commands) { this.commands = commands; }
}
