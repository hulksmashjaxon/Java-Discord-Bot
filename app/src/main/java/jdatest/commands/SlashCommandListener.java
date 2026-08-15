package jdatest.commands;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import jdatest.utils.SLF4J;
import jdatest.utils.SLF4J.logModes;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.ImageProxy;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommandListener extends ListenerAdapter {
  Dotenv dotenv = Dotenv.load();
  @SuppressWarnings("null")
  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
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

          case "get-weather" -> {
            final OptionMapping _state = event.getOption("state"); final OptionMapping _city = event.getOption("city");
            final String state = _state.getAsString(); final String city = _city.getAsString();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s,%s,%s&limit=1&appid=%s", city, state, "USA", dotenv.get("OPENWEATHER_API_KEY"))))
            .header("Accept", "application/json").GET().build();
            try {
              HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
              SLF4J.Log(res.statusCode(), logModes.DEBUG);
              SLF4J.Log(res.body(), logModes.DEBUG);
              
              try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(res.body());
                JsonNode index = root.get(0);

                if (index != null) {
                  String lat = index.get("lat").asText();
                  String lon = index.get("lon").asText();
                  HttpRequest weatherRequest = HttpRequest.newBuilder().uri(URI.create(
                    String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=imperial", lat, lon, dotenv.get("OPENWEATHER_API_KEY"))))
                  .GET().build();

                  HttpResponse<String> weatherRes = client.send(weatherRequest, HttpResponse.BodyHandlers.ofString());
                  try {
                    JsonNode weatherRoot = mapper.readTree(weatherRes.body());
                    JsonNode weatherIndex = weatherRoot; //* i did this wrong and im just going to do this. lmfao */
                    if (weatherIndex != null) {
                      Map<String, Double> main = mapper.convertValue(weatherIndex.get("main"), new TypeReference<Map<String, Double>>() {});
                      String cityName = weatherIndex.get("name").asText();
                      JsonNode wArray = weatherIndex.get("weather");
                      JsonNode weatherItem = wArray.get(0);
                      String temperature = main.get("temp").toString();
                      String min = main.get("temp_min").toString();
                      String max = main.get("temp_max").toString();
                      String humidity = main.get("humidity").toString();
                      if (weatherItem != null) {
                        String mainCond = weatherItem.get("main").asText();
                        String desc = weatherItem.get("description").asText();
                        SLF4J.Log(temperature + "\n" + cityName + "\n" + mainCond, logModes.DEBUG);

                        MessageEmbed embed = new EmbedBuilder().setTitle("Weather for " + cityName + ", " + state).addField(new Field("Condition", "**Main:** " + mainCond + "\n**Description:** " 
                        + desc, true)).addField(new Field("General", String.format("**Temperature:** %s°F\n**Min:** %s°F\n**Max:** %s°F\n**Humidity:** %s%%", temperature, min, max, humidity), false))
                        .build();

                        client.close();
                        event.replyEmbeds(embed).queue();
                      }
                    }
                    
                  } catch (Exception e) {
                    client.close();
                    e.printStackTrace();
                  }
                }
              } catch (Exception e) {
                client.close();
                e.printStackTrace();
              }
            } catch (Exception e) {
              client.close();
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
