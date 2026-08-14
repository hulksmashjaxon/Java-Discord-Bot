package jdatest;

import java.util.EnumSet;

import io.github.cdimascio.dotenv.Dotenv;
import jdatest.commands.CommandList;
import jdatest.commands.SlashCommandListener;
import jdatest.utils.SL4J;
import jdatest.utils.SL4J.logModes;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class App {
  @SuppressWarnings("null")
  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    if (dotenv.get("TOKEN").equals(null)) throw new NullPointerException("Could not find token in .env");
    JDA jda = JDABuilder.createLight(dotenv.get("TOKEN"), EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS))
    .addEventListeners(new SlashCommandListener())
    .build();

    try {
      jda.awaitReady();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    CommandListUpdateAction commands = jda.updateCommands();
    commands.addCommands(CommandList.GetCommands());
    commands.queue();
    SL4J.Log("Completed command setup", logModes.INFO, App.class);
  }
}
