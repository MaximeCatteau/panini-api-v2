package fr.paniniapiv2.utils;

import fr.paniniapiv2.db.BanWords;
import fr.paniniapiv2.services.BanWordsService;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;

import java.util.Arrays;
import java.util.List;

public class BanWordsUtils {

    private DiscordApi api;
    private Server server;

    BanWordsService banWordsService;

    public BanWordsUtils(BanWordsService banWordsService) {
        this.api = new DiscordApiBuilder().setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50").login().join();
        this.server = this.api.getServerById("753558157036486667").orElseThrow();
        this.banWordsService = banWordsService;
    }

    public void checkMessage(Message message) {
        String[] messageParts = message.getContent().split(" ");

        List<BanWords> banWordsList = this.banWordsService.getAllBanWords();

        /*if (messageParts[0].equals("bw!remove")) {
            return;
        }*/

        for (String word : messageParts) {
            if (!message.getAuthor().isBotUser() && banWordsList.stream().anyMatch(banWords -> banWords.getWord().equalsIgnoreCase(word))) {
                message.getChannel().sendMessage("Le message précédent contient un mot banni !");
                message.getAuthor().asUser().orElseThrow().openPrivateChannel().join().sendMessage("T'es pas sur Twitter mon reuf !");
                message.delete();
            }
        }
    }

    public void listenCommand(String command, MessageAuthor messageAuthor, TextChannel channel) {
        String[] parts = command.split(" ");
        String part1 = parts[0];
        String arg = parts[1];

        switch (part1) {
            case "bw!add":
                this.banWordsService.addBanWord(arg, messageAuthor.getName());
                channel.sendMessage("Le mot **" + arg + "** a été ajouté au dictionnaire de mots bannis par " + messageAuthor.getName());
                break;
            case "bw!remove":
                this.banWordsService.removeBanWord(arg);
                channel.sendMessage("Le mot **" + arg + "** a été supprimé du dictionnaire de mots bannis par " + messageAuthor.getName());
                break;
        }
    }
}
