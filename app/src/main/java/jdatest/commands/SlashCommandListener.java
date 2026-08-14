package jdatest.commands;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.ImageProxy;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommandListener extends ListenerAdapter {
  @SuppressWarnings("null")
  @Override
  public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
    switch (event.getName()) {
      case "ping" -> {
        long time = System.currentTimeMillis();

        event.reply("Please wait").setEphemeral(true).queue(r -> {
          long latency = System.currentTimeMillis() - time;
          long gateway = event.getJDA().getGatewayPing();

          EmbedBuilder _embed = new EmbedBuilder().setTitle("Bot ping")
              .setDescription(String.format("**GatewayPing**: %s ms\n **Latency:** %s ms", gateway, latency));
          MessageEmbed embed = _embed.build();

          r.editOriginal((String) null).setEmbeds(embed).queue();
        });
      }

      case "util" -> {
        switch (event.getSubcommandName()) {
          case "get-avatar" -> {
            final OptionMapping _userOption = event.getOption("user");
            final User user = _userOption.getAsUser();
            ImageProxy avatar = user.getEffectiveAvatar();
            avatar.download(256).thenAccept(input -> {
              event.replyFiles(FileUpload.fromData(input, "avatar.png")).setEphemeral(true).queue();
            }).exceptionally(t -> {
              t.printStackTrace();
              return null;
            });
          }
        }
      }
    }
  }
}
