package fr.paniniapiv2.utils;

import fr.paniniapiv2.services.CodeService;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscordUtils {

    CodeService codeService;

    private DiscordApi api;
    private Server server;

    public DiscordUtils(CodeService codeService) {
        this.api = new DiscordApiBuilder().setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50").login().join();
        this.server = this.api.getServerById("616730464321011739").orElseThrow();
        this.codeService = codeService;
    }

    public void listenMessage(String message, MessageAuthor messageAuthor, TextChannel channel) {
        if (message.contains("!") && !messageAuthor.getName().contains("Cards.io")) {
            this.listenCommand(message, messageAuthor, channel);
        }
    }

    public void listenCommand(String command, MessageAuthor messageAuthor, TextChannel channel) {
        switch (command) {
            case "!cards.io":
                channel.sendMessage("https://cards-io-app.herokuapp.com");
                break;
            case "!membercount":
                channel.sendMessage("Le serveur totalise actuellement " + this.server.getMemberCount() + " membres !");
                break;
            case "jdl!rules":
                String msg = "**Jeu du Logo**\n\n" +
                        "Le __Jeu du Logo__ est un petit jeu proposé par Kemy tous les jours à __19h__. Le but est très simple : deviner des logos d'équipes (clubs, sélections, anciens) sur lesquels il manquera des informations.\n\n" +
                        "Pour participer, il vous suffira d'envoyer un MP à <@185790407156826113> avec votre réponse. Les points sont comptés en fonction de la rapidité par rapport aux joueurs de votre Ligue.\n\n" +
                        "Vous pouvez consulter rapidement les classements sur __Cards.io__ (lien avec la commande *!cards.io*). Une saison dure 14 journées (donc 14 logos).\n\n" +
                        "Bonne chance !";
                channel.sendMessage(msg);
                break;
            case "!codes":
                String discordId = messageAuthor.getIdAsString();
                List<String> playerCodes = this.codeService.getPlayerCodesByDiscordId(discordId);
                String codesMessage = "";

                if (playerCodes.size() == 0) {
                    codesMessage = "Pas de codes trouvés";
                } else {
                    for (String code : playerCodes) {
                        codesMessage += "- " + code + "\n";
                    }
                }

                try {
                    CompletableFuture<User> user = this.api.getUserById(messageAuthor.getId());
                    PrivateChannel pc = user.get().openPrivateChannel().get();

                    pc.sendMessage(codesMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                channel.sendMessage("Commande non reconnue");
                break;
        }
    }
}
