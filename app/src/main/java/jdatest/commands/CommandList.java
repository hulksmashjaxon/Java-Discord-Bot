package jdatest.commands;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import jdatest.utils.SLF4J;
import jdatest.utils.SLF4J.logModes;
import jdatest.utils.data.ListWrap;
import jdatest.utils.data.XmlConversion;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class CommandList {
  public static List<CommandData> GetCommands() {
    List<CommandData> let = null;
    try {
      XmlMapper mapper = new XmlMapper();
      File file = new File("app/src/main/java/jdatest/commands/commands.xml");
      ListWrap parsed = mapper.readValue(file, ListWrap.class);
      List<CommandData> ready = XmlConversion.xmlConversion(parsed);

      let = ready;
    } catch (Exception e) {
      SLF4J.Log("Unhandled exception occurred", logModes.WARN);
      e.printStackTrace();
    }

    return let;
  }
}