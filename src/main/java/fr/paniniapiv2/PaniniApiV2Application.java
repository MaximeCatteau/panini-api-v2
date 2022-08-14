package fr.paniniapiv2;

import fr.paniniapiv2.rp.services.FootballPlayerService;
import fr.paniniapiv2.rp.services.MentalAttributesService;
import fr.paniniapiv2.rp.services.PhysicalAttributesService;
import fr.paniniapiv2.rp.services.TechnicalAttributesService;
import fr.paniniapiv2.services.BanWordsService;
import fr.paniniapiv2.services.CodeService;
import fr.paniniapiv2.utils.BanWordsUtils;
import fr.paniniapiv2.utils.DiscordUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PaniniApiV2Application {

	public static void main(String[] args) {
		DiscordApi api = new DiscordApiBuilder().setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50").login().join();
		
		ConfigurableApplicationContext applicationContext = SpringApplication.run(PaniniApiV2Application.class, args);

		CodeService codeService = applicationContext.getBean(CodeService.class);
		FootballPlayerService footballPlayerService = applicationContext.getBean(FootballPlayerService.class);
		PhysicalAttributesService physicalAttributesService = applicationContext.getBean(PhysicalAttributesService.class);
		MentalAttributesService mentalAttributesService = applicationContext.getBean(MentalAttributesService.class);
		TechnicalAttributesService technicalAttributesService = applicationContext.getBean(TechnicalAttributesService.class);

		BanWordsService banWordsService = applicationContext.getBean(BanWordsService.class);

		DiscordUtils discordUtils = new DiscordUtils(codeService, footballPlayerService, physicalAttributesService,
				mentalAttributesService, technicalAttributesService);
		BanWordsUtils banWordsUtils = new BanWordsUtils(banWordsService);

		api.updateActivity("Cards.io");

		api.addMessageCreateListener(event -> {
			banWordsUtils.checkMessage(event.getMessage());

			if (event.getMessageContent().contains("bw!")) {
				banWordsUtils.listenCommand(event.getMessageContent(), event.getMessageAuthor(), event.getChannel());
			}

			discordUtils.listenMessage(event.getMessageContent(), event.getMessageAuthor(), event.getChannel());
		});
	}
}
