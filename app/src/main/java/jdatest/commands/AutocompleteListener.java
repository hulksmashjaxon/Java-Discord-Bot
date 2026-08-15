package jdatest.commands;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jdatest.utils.SLF4J;
import jdatest.utils.SLF4J.logModes;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutocompleteListener extends ListenerAdapter {
  private final Map<String, String> states = Map.ofEntries(
      Map.entry("Alabama", "AL"), Map.entry("Alaska", "AK"), Map.entry("Arizona", "AZ"),
      Map.entry("Arkansas", "AR"), Map.entry("California", "CA"), Map.entry("Colorado", "CO"),
      Map.entry("Connecticut", "CT"), Map.entry("Delaware", "DE"), Map.entry("Florida", "FL"),
      Map.entry("Georgia", "GA"), Map.entry("Hawaii", "HI"), Map.entry("Idaho", "ID"),
      Map.entry("Illinois", "IL"), Map.entry("Indiana", "IN"), Map.entry("Iowa", "IA"),
      Map.entry("Kansas", "KS"), Map.entry("Kentucky", "KY"), Map.entry("Louisiana", "LA"),
      Map.entry("Maine", "ME"), Map.entry("Maryland", "MD"), Map.entry("Massachusetts", "MA"),
      Map.entry("Michigan", "MI"), Map.entry("Minnesota", "MN"), Map.entry("Mississippi", "MS"),
      Map.entry("Missouri", "MO"), Map.entry("Montana", "MT"), Map.entry("Nebraska", "NE"),
      Map.entry("Nevada", "NV"), Map.entry("New Hampshire", "NH"), Map.entry("New Jersey", "NJ"),
      Map.entry("New Mexico", "NM"), Map.entry("New York", "NY"), Map.entry("North Carolina", "NC"),
      Map.entry("North Dakota", "ND"), Map.entry("Ohio", "OH"), Map.entry("Oklahoma", "OK"),
      Map.entry("Oregon", "OR"), Map.entry("Pennsylvania", "PA"), Map.entry("Rhode Island", "RI"),
      Map.entry("South Carolina", "SC"), Map.entry("South Dakota", "SD"), Map.entry("Tennessee", "TN"),
      Map.entry("Texas", "TX"), Map.entry("Utah", "UT"), Map.entry("Vermont", "VT"),
      Map.entry("Virginia", "VA"), Map.entry("Washington", "WA"), Map.entry("West Virginia", "WV"),
      Map.entry("Wisconsin", "WI"), Map.entry("Wyoming", "WY"));

    @SuppressWarnings("null")
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
      final String evtName = event.getFocusedOption().getName();
      switch (evtName) {
        case "state":
          SLF4J.Log("ran autocomplete action on: " + evtName, logModes.DEBUG);
          String typed = event.getFocusedOption().getValue().toLowerCase();
          List<Command.Choice> choices = states.keySet().stream().filter(stateDisplay -> stateDisplay.toLowerCase().contains(typed)).limit(25)
          .map(stateDispaly -> new Command.Choice(stateDispaly, states.get(stateDispaly)))
          .collect(Collectors.toList()); //* stream iterates over objects, great for transformation!!! */

          event.replyChoices(choices).queue();
          break;
        default:
          break;
      }
    }
}
