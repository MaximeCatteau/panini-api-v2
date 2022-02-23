package fr.paniniapiv2;

import fr.paniniapiv2.controllers.CodeController;
import fr.paniniapiv2.db.Collection;
import fr.paniniapiv2.repositories.CodeRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class PaniniApiV2Application {
	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	CodeRepository codeRepository;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		DiscordApi api = new DiscordApiBuilder().setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50").login().join();
		
		SpringApplication.run(PaniniApiV2Application.class, args);

		api.addSlashCommandCreateListener(event -> {
			SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

			if (slashCommandInteraction.getCommandName().equals("ping")) {
				slashCommandInteraction.createImmediateResponder()
						.setContent("Pong!")
						.setFlags(MessageFlag.EPHEMERAL)
						.respond();
			}
		});

		Server server = api.getServerById("616730464321011739").orElseThrow();

		CompletableFuture<User> bot = api.getUserById("910086142580367380");

		api.updateActivity("Cards.io");

		api.addMessageCreateListener(event -> {
			if (!event.getMessageAuthor().getName().equals("Cards.io")) {
				CompletableFuture<User> admin = api.getUserById("185790407156826113");

				if (event.getMessageContent().equalsIgnoreCase("!cards.io")) {
					event.getChannel().sendMessage("https://cards-io-app.herokuapp.com");
				}

				/*if (event.getMessageContent().equalsIgnoreCase("!codes")) {
					long userId = event.getMessageAuthor().getId();
					CompletableFuture<User> u = api.getUserById(userId);
					try {
						PrivateChannel pc = u.get().openPrivateChannel().get();
						pc.sendMessage(codeController.getUnusedCodes("" + event.getMessageAuthor().getId()));

					} catch(Exception e) {
						e.printStackTrace();
					}
				}*/

				if (event.getMessageContent().equalsIgnoreCase("!membercount")) {
					event.getChannel().sendMessage("Le serveur totalise actuellement " + server.getMemberCount() + " membres !");
				}

				if (event.getMessageContent().equalsIgnoreCase("jdl!rules")) {
					String msg = "**Jeu du Logo**\n\n" +
							"Le __Jeu du Logo__ est un petit jeu proposé par Kemy tous les jours à __19h__. Le but est très simple : deviner des logos d'équipes (clubs, sélections, anciens) sur lesquels il manquera des informations.\n\n" +
							"Pour participer, il vous suffira d'envoyer un MP à <@185790407156826113> avec votre réponse. Les points sont comptés en fonction de la rapidité par rapport aux joueurs de votre Ligue.\n\n" +
							"Vous pouvez consulter rapidement les classements sur __Cards.io__ (lien avec la commande *!cards.io*). Une saison dure 14 journées (donc 14 logos).\n\n" +
							"Bonne chance !";

					event.getChannel().sendMessage(msg);
				}
			}

			if (event.getMessageContent().equalsIgnoreCase("!ping")) {
				event.getChannel().sendMessage("Pong!");
			}
		});
	}
}
