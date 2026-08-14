package jdatest.commands;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class CommandList {
  static SubcommandData getAvatar = new SubcommandData("get-avatar", "Gets an avatar from the specified user")
  .addOption(OptionType.USER, "user", "Gets the avatar from this person", true);


  public static List<CommandData> GetCommands() {
    List<CommandData> commands = new ArrayList<>();
    commands.add(Commands.slash("ping", "Returns the bot's ping").setContexts(InteractionContextType.GUILD));
    commands.add(Commands.slash("util", "Various utility commands").addSubcommands(getAvatar));
    return commands;
  }
}
