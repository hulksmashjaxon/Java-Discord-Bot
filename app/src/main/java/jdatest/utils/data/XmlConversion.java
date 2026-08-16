package jdatest.utils.data;

import java.util.ArrayList;
import java.util.List;

import jdatest.utils.SLF4J;
import jdatest.utils.SLF4J.logModes;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

public class XmlConversion {
  public static List<CommandData> xmlConversion(ListWrap parsed) {
    List<CommandData> commands = new ArrayList<>();
    if (parsed == null || parsed.getCommands() == null) {
      return commands;
    }

    for (XmlCommandData xmlCmd : parsed.getCommands()) {
      String name = xmlCmd.getName();
      String description = xmlCmd.getDescription();
      if (name == null || description == null) {
        return commands;
      }
      SLF4J.Log("Registered command " + name, logModes.INFO);

      SlashCommandData slashCommand = Commands.slash(name, description);
      if (xmlCmd.getContext() != null && xmlCmd.getContext().equalsIgnoreCase("GUILD")) {
        slashCommand.setContexts(InteractionContextType.GUILD);
      }

      if (xmlCmd.getSubcommandGroups() != null && xmlCmd.getSubcommands() == null) {
        for (XmlSubcommandGroupData subGroup : xmlCmd.getSubcommandGroups()) {
          String subName = subGroup.getName();
          String subDesc = subGroup.getDescription();
          if (subName == null || subDesc == null) {
            return commands;
          }
          SubcommandGroupData jdaSubGroup = new SubcommandGroupData(subName, description);
          if (subGroup.getSubcommands() != null) {
            for (XmlSubcommandData subcommand : subGroup.getSubcommands()) {
              String sbName = subcommand.getName();
              String sbDesc = subcommand.getDescription();
              if (sbName == null || sbDesc == null) { return commands; }
              SubcommandData jdaSubcommand = new SubcommandData(sbName, sbDesc);
              if (subcommand.getOptions() != null) {
                for (XmlOptionData opt : subcommand.getOptions()) {
                  String optName = opt.getName();
                  String optDesc = opt.getDescription();
                  OptionType optType = OptionType.valueOf(opt.getType());
                  if (optName == null || optDesc == null) {
                    return commands;
                  }
                  jdaSubcommand.addOption(optType, optName, optDesc);
                }
              }
              jdaSubGroup.addSubcommands(jdaSubcommand);
            }
          }
          slashCommand.addSubcommandGroups(jdaSubGroup);
        }
      } 

      if (xmlCmd.getSubcommands() != null && xmlCmd.getSubcommandGroups() == null) {
        for (XmlSubcommandData subcommand : xmlCmd.getSubcommands()) {
          String scName = subcommand.getName();
          String scDesc = subcommand.getDescription();
          if (scName == null || scDesc == null) {
            return commands;
          }
          SubcommandData sCmd = new SubcommandData(scName, scDesc);

          if (subcommand.getOptions() != null) {
            for (XmlOptionData option : subcommand.getOptions()) {
              OptionType opt = OptionType.valueOf(option.getType());
              String optName = option.getName();
              String optDesc = option.getDescription();
              if (optName == null || optDesc == null) {
                return commands;
              }
              sCmd.addOption(opt, optName, optDesc, option.isRequired(), option.isAutocomplete());
              
            }
          }
          slashCommand.addSubcommands(sCmd);
        }
      } else if (xmlCmd.getOptions() != null && !xmlCmd.getOptions().isEmpty()) {
        for (XmlOptionData opt : xmlCmd.getOptions()) {
          OptionType type = OptionType.valueOf(opt.getType().toUpperCase());
          String optName = opt.getName();
          String optDesc = opt.getDescription();
          if (optName == null || optDesc == null) {
            return commands;
          }
          slashCommand.addOption(type, optName, optDesc, opt.isRequired(), opt.isAutocomplete());
        }
      }
      commands.add(slashCommand);
    }
    return commands;
  }
}
