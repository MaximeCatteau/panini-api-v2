package fr.paniniapiv2;

import fr.paniniapiv2.services.CodeService;
import fr.paniniapiv2.utils.DiscordUtils;
import org.apache.catalina.core.ApplicationContext;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class PaniniApiV2Application {

	public static void main(String[] args) {
		DiscordApi api = new DiscordApiBuilder().setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50").login().join();
		
		ConfigurableApplicationContext applicationContext = SpringApplication.run(PaniniApiV2Application.class, args);

		CodeService codeService = applicationContext.getBean(CodeService.class);

		DiscordUtils discordUtils = new DiscordUtils(codeService);

		api.updateActivity("Cards.io");

		api.addMessageCreateListener(event -> {
			discordUtils.listenMessage(event.getMessageContent(), event.getMessageAuthor(), event.getChannel());
		});
	}
}
