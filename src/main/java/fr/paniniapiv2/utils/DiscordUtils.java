package fr.paniniapiv2.utils;

import fr.paniniapiv2.rp.db.*;
import fr.paniniapiv2.rp.enums.EPosition;
import fr.paniniapiv2.rp.enums.EPost;
import fr.paniniapiv2.rp.resources.UpdateMentalAttributesResource;
import fr.paniniapiv2.rp.resources.UpdatePhysicalAttributesResource;
import fr.paniniapiv2.rp.resources.UpdateTechnicalFieldPlayerAttributesResource;
import fr.paniniapiv2.rp.resources.UpdateTechnicalGoalKeeperAttributesResource;
import fr.paniniapiv2.rp.services.FootballPlayerService;
import fr.paniniapiv2.rp.services.MentalAttributesService;
import fr.paniniapiv2.rp.services.PhysicalAttributesService;
import fr.paniniapiv2.rp.services.TechnicalAttributesService;
import fr.paniniapiv2.services.CodeService;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.springframework.context.annotation.Lazy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscordUtils {

    CodeService codeService;

    FootballPlayerService footballPlayerService;

    PhysicalAttributesService physicalAttributesService;
    MentalAttributesService mentalAttributesService;
    TechnicalAttributesService technicalAttributesService;

    private DiscordApi api;
    private Server server;

    public DiscordUtils(CodeService codeService,
                        @Lazy FootballPlayerService footballPlayerService,
                        @Lazy PhysicalAttributesService physicalAttributesService,
                        @Lazy MentalAttributesService mentalAttributesService,
                        @Lazy TechnicalAttributesService technicalAttributesService) {
        this.api = new DiscordApiBuilder().setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50").login().join();
        this.server = this.api.getServerById("616730464321011739").orElseThrow();
        this.codeService = codeService;
        this.footballPlayerService = footballPlayerService;
        this.physicalAttributesService = physicalAttributesService;
        this.mentalAttributesService = mentalAttributesService;
        this.technicalAttributesService = technicalAttributesService;
    }

    public void listenMessage(String message, MessageAuthor messageAuthor, TextChannel channel) {
        if (message.contains("rp!") && channel.getIdAsString().equals("753558157523157057") && !messageAuthor.getName().contains("Mascotte du FC Binouze")) {
            this.listenRolePlayBinouzeCommand(message, messageAuthor, channel);
        }
        else if (message.contains("!")  && !messageAuthor.getName().contains("Mascotte du FC Binouze")) {
            this.listenCommand(message, messageAuthor, channel);
        }
    }

    public void listenRolePlayBinouzeCommand(String message, MessageAuthor messageAuthor, TextChannel channel) {
        String[] words = message.split(" ");
        Color color = new Color(17, 103, 59);
        FootballPlayer footballPlayer = new FootballPlayer();
        EmbedBuilder embed = new EmbedBuilder();

        switch(words[0]) {
            case "rp!me":
                if (words.length > 1) {
                    int footballPlayerId = Integer.parseInt(words[1]);
                    footballPlayer = this.footballPlayerService.getFootballPlayerById(footballPlayerId);

                    if (footballPlayer == null) {
                        channel.sendMessage("Erreur : ce joueur n'existe pas");
                        break;
                    }

                    if (!footballPlayer.getOwnerDiscordId().equals(messageAuthor.getIdAsString())) {
                        channel.sendMessage("Erreur : ce n'est pas votre joueur !");
                        break;
                    }

                    embed = new EmbedBuilder();
                    embed.setAuthor("[" + footballPlayer.getId() + "] " + footballPlayer.getNumber() + ". " + footballPlayer.getFirstName() + " " + footballPlayer.getLastName(), "https://cards-io-app.herokuapp.com/", "https://i.ibb.co/m6d1kKy/prototype-fc-binouze.png");
                    embed.setTitle("Niveau " + footballPlayer.getLevel());
                    embed.setDescription(footballPlayer.getExperience() + " xp");
                    embed.setColor(color);
                    embed.addField("Poste", this.stringifyPosition(footballPlayer.getPosition(), footballPlayer.getPost()));
                    embed.addField("Points à distribuer : ", "" + footballPlayer.getPointsToSet());
                    channel.sendMessage(embed);
                    break;
                } else {
                    String discordId = messageAuthor.getIdAsString();
                    List<FootballPlayer> footballPlayers = this.footballPlayerService.findFootballPlayersOfPlayerByDiscordId(discordId);

                    if (footballPlayers.size() > 1) {
                        String msg = "Vous avez plusieurs joueurs à votre actif, veuillez préciser l'id dans la commande :\n";
                        for (FootballPlayer fp : footballPlayers) {
                            msg += "**" + fp.getId() + "** - " + fp.getNumber() + ". " + fp.getFirstName() + " " + fp.getLastName() + "\n";
                        }
                        channel.sendMessage(msg);
                    } else if (footballPlayers.size() == 1) {
                        footballPlayer = footballPlayers.get(0);

                        embed = new EmbedBuilder();
                        embed.setAuthor("[" + footballPlayer.getId() + "]" + footballPlayer.getNumber() + ". " + footballPlayer.getFirstName() + " " + footballPlayer.getLastName(), "https://cards-io-app.herokuapp.com/", "https://i.ibb.co/m6d1kKy/prototype-fc-binouze.png");
                        embed.setTitle("Niveau " + footballPlayer.getLevel());
                        embed.setDescription(footballPlayer.getExperience() + " xp");
                        embed.setColor(color);
                        embed.addField("Poste", this.stringifyPosition(footballPlayer.getPosition(), footballPlayer.getPost()));
                        embed.addField("Points à distribuer : ", "" + footballPlayer.getPointsToSet());
                        channel.sendMessage(embed);
                    }
                    break;
                }
            case "rp!physic":
                if (words.length > 1) {
                    int footballPlayerId = Integer.parseInt(words[1]);
                    footballPlayer = this.footballPlayerService.getFootballPlayerById(footballPlayerId);

                    if (footballPlayer == null) {
                        channel.sendMessage("Erreur : ce joueur n'existe pas");
                        break;
                    }

                    if (!footballPlayer.getOwnerDiscordId().equals(messageAuthor.getIdAsString())) {
                        channel.sendMessage("Erreur : ce n'est pas votre joueur !");
                        break;
                    }
                } else {
                    String discordId = messageAuthor.getIdAsString();
                    List<FootballPlayer> footballPlayers = this.footballPlayerService.findFootballPlayersOfPlayerByDiscordId(discordId);

                    if (footballPlayers.size() > 1) {
                        String msg = "Vous avez plusieurs joueurs à votre actif, veuillez préciser l'id dans la commande :\n";
                        for (FootballPlayer fp : footballPlayers) {
                            msg += "**" + fp.getId() + "** - " + fp.getNumber() + ". " + fp.getFirstName() + " " + fp.getLastName() + "\n";
                        }
                        channel.sendMessage(msg);
                    } else if (footballPlayers.size() == 1) {
                        footballPlayer = footballPlayers.get(0);
                    }
                }

                PhysicalAttributes physicalAttributes = footballPlayer.getPhysicalAttributes();

                embed = new EmbedBuilder();
                embed.setAuthor("[" + footballPlayer.getId() + "] " + footballPlayer.getNumber() + ". " + footballPlayer.getFirstName() + " " + footballPlayer.getLastName(), "https://cards-io-app.herokuapp.com/", "https://i.ibb.co/m6d1kKy/prototype-fc-binouze.png");
                embed.setTitle("Attributs physiques");
                embed.setColor(color);
                embed.addInlineField("Accélération", "*[acceleration]* " + physicalAttributes.getAcceleration());
                embed.addInlineField("Agilité", "*[agility]* " + physicalAttributes.getAgility());
                embed.addInlineField("Détente verticale", "*[vertical_extension]* " + physicalAttributes.getVerticalExtension());
                embed.addInlineField("Endurance", "*[endurance]* " + physicalAttributes.getEndurance());
                embed.addInlineField("Équilibre", "*[equilibrium]* " + physicalAttributes.getEquilibrium());
                embed.addInlineField("Puissance", "*[power]* " + physicalAttributes.getPower());
                embed.addInlineField("Qualités physiques naturelles", "*[natural_physic_abilities]* " + physicalAttributes.getNaturalPhysicAbilities());
                embed.addInlineField("Vitesse", "*[speed]* " + physicalAttributes.getSpeed());

                channel.sendMessage(embed);
                break;
            case "rp!mental":
                if (words.length > 1) {
                    int footballPlayerId = Integer.parseInt(words[1]);
                    footballPlayer = this.footballPlayerService.getFootballPlayerById(footballPlayerId);

                    if (footballPlayer == null) {
                        channel.sendMessage("Erreur : ce joueur n'existe pas");
                        break;
                    }

                    if (!footballPlayer.getOwnerDiscordId().equals(messageAuthor.getIdAsString())) {
                        channel.sendMessage("Erreur : ce n'est pas votre joueur !");
                        break;
                    }
                } else {
                    String discordId = messageAuthor.getIdAsString();
                    List<FootballPlayer> footballPlayers = this.footballPlayerService.findFootballPlayersOfPlayerByDiscordId(discordId);

                    if (footballPlayers.size() > 1) {
                        String msg = "Vous avez plusieurs joueurs à votre actif, veuillez préciser l'id dans la commande :\n";
                        for (FootballPlayer fp : footballPlayers) {
                            msg += "**" + fp.getId() + "** - " + fp.getNumber() + ". " + fp.getFirstName() + " " + fp.getLastName() + "\n";
                        }
                        channel.sendMessage(msg);
                    } else if (footballPlayers.size() == 1) {
                        footballPlayer = footballPlayers.get(0);
                    }
                }

                MentalAttributes mentalAttributes = footballPlayer.getMentalAttributes();

                embed = new EmbedBuilder();
                embed.setAuthor("[" + footballPlayer.getId() + "] " + footballPlayer.getNumber() + ". " + footballPlayer.getFirstName() + " " + footballPlayer.getLastName(), "https://cards-io-app.herokuapp.com/", "https://i.ibb.co/m6d1kKy/prototype-fc-binouze.png");
                embed.setTitle("Attributs mentaux");
                embed.setColor(color);
                embed.addInlineField("Agressivité", "*[aggressiveness]* " + mentalAttributes.getAggressiveness());
                embed.addInlineField("Anticipation", "*[anticipation]* " + mentalAttributes.getAnticipation());
                embed.addInlineField("Appels de balle", "*[ball_calls]* " + mentalAttributes.getBallCalls());
                embed.addInlineField("Concentration", "*[concentration]* " + mentalAttributes.getConcentration());
                embed.addInlineField("Courage", "*[bravery]* " + mentalAttributes.getBravery());
                embed.addInlineField("Décisions", "*[decision_making]* " + mentalAttributes.getDecisionMaking());
                embed.addInlineField("Détermination", "*[determination]* " + mentalAttributes.getDetermination());
                embed.addInlineField("Inspiration", "*[inspiration]* " + mentalAttributes.getInspiration());
                embed.addInlineField("Jeu collectif", "*[collective]* " + mentalAttributes.getCollectiveGame());
                embed.addInlineField("Leadership", "*[leadership]* " + mentalAttributes.getLeadership());
                embed.addInlineField("Placement", "*[placement]* " + mentalAttributes.getPlacement());
                embed.addInlineField("Sang-froid", "*[cold_blood]* " + mentalAttributes.getColdBlood());
                embed.addInlineField("Vision du jeu", "*[game_view]* " + mentalAttributes.getGameView());
                embed.addInlineField("Volume de jeu", "*[game_volume]* " + mentalAttributes.getGameVolume());

                channel.sendMessage(embed);
                break;
            case "rp!technic":
                if (words.length > 1) {
                    int footballPlayerId = Integer.parseInt(words[1]);
                    footballPlayer = this.footballPlayerService.getFootballPlayerById(footballPlayerId);

                    if (footballPlayer == null) {
                        channel.sendMessage("Erreur : ce joueur n'existe pas");
                        break;
                    }

                    if (!footballPlayer.getOwnerDiscordId().equals(messageAuthor.getIdAsString())) {
                        channel.sendMessage("Erreur : ce n'est pas votre joueur !");
                        break;
                    }
                } else {
                    String discordId = messageAuthor.getIdAsString();
                    List<FootballPlayer> footballPlayers = this.footballPlayerService.findFootballPlayersOfPlayerByDiscordId(discordId);

                    if (footballPlayers.size() > 1) {
                        String msg = "Vous avez plusieurs joueurs à votre actif, veuillez préciser l'id dans la commande :\n";
                        for (FootballPlayer fp : footballPlayers) {
                            msg += "**" + fp.getId() + "** - " + fp.getNumber() + ". " + fp.getFirstName() + " " + fp.getLastName() + "\n";
                        }
                        channel.sendMessage(msg);
                    } else if (footballPlayers.size() == 1) {
                        footballPlayer = footballPlayers.get(0);
                    }
                }

                TechnicalAttributes technicalAttributes = footballPlayer.getTechnicalAttributes();

                embed = new EmbedBuilder();

                embed.setAuthor("[" + footballPlayer.getId() + "] " + footballPlayer.getNumber() + ". " + footballPlayer.getFirstName() + " " + footballPlayer.getLastName(), "https://cards-io-app.herokuapp.com/", "https://i.ibb.co/m6d1kKy/prototype-fc-binouze.png");
                embed.setTitle("Attributs techniques");
                embed.setColor(color);
                embed.addInlineField("Passes", "*[passes]* " + technicalAttributes.getPasses());
                embed.addInlineField("Contrôle de balle", "*[ball_control]* " + technicalAttributes.getBallControl());

                if (footballPlayer.getPost().equals(EPost.GOALKEEPER)) {
                    GoalkeeperAttributes goalkeeperAttributes = (GoalkeeperAttributes) technicalAttributes;
                    embed.addInlineField("Communication", "*[communication]* " + goalkeeperAttributes.getCommunication());
                    embed.addInlineField("Dégagements au poing", "*[fist_clearances]* " + goalkeeperAttributes.getFistClearances());
                    embed.addInlineField("Dégagements", "*[clearances]* " + goalkeeperAttributes.getClearances());
                    embed.addInlineField("Détente", "*[extension]* " + goalkeeperAttributes.getExtension());
                    embed.addInlineField("Excentricité", "*[excentricity]* " + goalkeeperAttributes.getExcentricity());
                    embed.addInlineField("Prises de balle", "*[ball_catches]* " + goalkeeperAttributes.getBallCatches());
                    embed.addInlineField("Réflexes", "*[reflexes]* " + goalkeeperAttributes.getReflexes());
                    embed.addInlineField("Relances à la main", "*[hand_throws]* " + goalkeeperAttributes.getHandThrows());
                    embed.addInlineField("Sorties dans la surface", "*[inbox_exits]* " + goalkeeperAttributes.getInboxExits());
                    embed.addInlineField("Sorties dans les pieds", "*[in_feet_exits]* " + goalkeeperAttributes.getInFeetExits());
                    embed.addInlineField("Un contre un", "*[one_versus_one]* " + goalkeeperAttributes.getOneVersusOne());
                } else {
                    FieldPlayerAttributes fieldPlayerAttributes = (FieldPlayerAttributes) technicalAttributes;
                    embed.addInlineField("Centres", "*[centers]* " + fieldPlayerAttributes.getCenters());
                    embed.addInlineField("Corners", "*[corners]* " + fieldPlayerAttributes.getCorners());
                    embed.addInlineField("Coups francs", "*[free_kicks]* " + fieldPlayerAttributes.getFreeKicks());
                    embed.addInlineField("Dribbles", "*[dribbles]* " + fieldPlayerAttributes.getDribbles());
                    embed.addInlineField("Finition", "*[finition]* " + fieldPlayerAttributes.getFinition());
                    embed.addInlineField("Jeu de tête", "*[headers]* " + fieldPlayerAttributes.getHeaders());
                    embed.addInlineField("Marquage", "*[marking]* " + fieldPlayerAttributes.getMarking());
                    embed.addInlineField("Penalty", "*[penalty]* " + fieldPlayerAttributes.getPenalty());
                    embed.addInlineField("Tacles", "*[tackles]* " + fieldPlayerAttributes.getTackles());
                    embed.addInlineField("Technique", "*[technique]* " + fieldPlayerAttributes.getTechnique());
                    embed.addInlineField("Tirs de loin", "*[long_shots]* " + fieldPlayerAttributes.getLongShots());
                    embed.addInlineField("Touches longues", "*[long_throws]* " + fieldPlayerAttributes.getLongThrows());
                }

                channel.sendMessage(embed);
                break;
            case "rp!add":
                if (words.length != 3) {
                    channel.sendMessage("Erreur : Mauvaise utilisation de la commande : `rp!add [id du joueur] [nom de la compétence]`");
                    break;
                } else {
                    footballPlayer = this.footballPlayerService.getFootballPlayerById(Integer.parseInt(words[1]));
                    List<FootballPlayer> footballPlayers = this.footballPlayerService.findFootballPlayersOfPlayerByDiscordId(messageAuthor.getIdAsString());

                    final FootballPlayer footballPlayer1 = footballPlayer;

                    if (footballPlayer == null) {
                        channel.sendMessage("Erreur : ce joueur n'existe pas.");
                        break;
                    } else if (footballPlayers.stream().noneMatch(fp -> fp.getId() == footballPlayer1.getId())) {
                        channel.sendMessage("Erreur : ce joueur ne vous appartient pas.");
                        break;
                    } else if (footballPlayer.getPointsToSet() == 0) {
                        channel.sendMessage("Erreur : vous n'avez plus de points à attribuer.");
                        break;
                    }

                    if (!isCapacityOk(words[2], footballPlayer1)) {
                        channel.sendMessage("Erreur : impossible d'augmenter cette capacité.");
                        break;
                    }

                   this.upgradeCapacity(words[2], footballPlayer1, channel, messageAuthor);

                    channel.sendMessage("Gaspard et Mimi sont les GOAT");
                }
                break;
            default:
                channel.sendMessage("Commande RP Binouze non reconnue. Veuillez vous référer à la commande `rp!help` pour plus d'informations");
                break;
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
                //channel.sendMessage("Commande non reconnue");
                break;
        }
    }

    public String stringifyPosition(EPosition position, EPost post) {
        String stringPost = "";
        switch (post) {
            case GOALKEEPER:
                stringPost += "Gardien de but";
                return stringPost;
            case DEFENDER:
                stringPost += "Défenseur ";
                break;
            case MIDFIELDER:
                stringPost += "Milieu ";
                break;
            case FORWARD:
                stringPost += "Attaquant ";
                break;
            case WINGER:
                stringPost += "Ailier ";
                break;
        }

        switch (position) {
            case CENTER:
                stringPost += "central";
                break;
            case CENTER_OFFENSIVE:
                stringPost += "offensif central";
                break;
            case RIGHT:
                stringPost += "droit";
                break;
            case LEFT:
                stringPost += "gauche";
                break;
        }

        return stringPost;
    }

    private boolean isCapacityOk(String capacity, FootballPlayer footballPlayer) {
        List<String> goalkeeperTechnicalCapacities = new ArrayList<String>(Arrays.asList("communication", "fist_clearances",
                "clearances", "extension", "excentricity", "ball_catches", "reflexes", "hand_throws", "inbox_exits",
                "in_feet_exits", "one_versus_one"));
        List<String> fieldPlayerTechnicCapacities = new ArrayList<String>(Arrays.asList("centers", "corners", "free_kicks",
                "dribbles", "finition", "headers", "marking", "penalty", "tackles", "technique", "long_shots",
                "long_throws"));
        List<String> physicalCapacities = new ArrayList<String>(Arrays.asList("acceleration", "agility", "vertical_extension",
                "endurance", "endurance", "equilibrium", "power", "natural_physic_abilities", "speed"));
        List<String> mentalCapacities = new ArrayList<>(Arrays.asList("aggressiveness", "anticipation", "ball_calls",
                "concentration", "bravery", "decision_making", "determination", "inspiration", "collective", "leadership",
                "placement", "cold_blood", "game_view", "game_volume"));
        List<String> generalTechnicCapacities = new ArrayList<>(Arrays.asList("passes", "ball_control"));
        List<String> allCapacities = new ArrayList<>();
        allCapacities.addAll(goalkeeperTechnicalCapacities);
        allCapacities.addAll(fieldPlayerTechnicCapacities);
        allCapacities.addAll(goalkeeperTechnicalCapacities);
        allCapacities.addAll(physicalCapacities);
        allCapacities.addAll(generalTechnicCapacities);
        allCapacities.addAll(mentalCapacities);

        if (!allCapacities.contains(capacity)) {
            return false;
        }

        if (footballPlayer.getPost().equals(EPost.GOALKEEPER) && fieldPlayerTechnicCapacities.contains(capacity)) {
            return false;
        } else if (!footballPlayer.getPost().equals(EPost.GOALKEEPER) && goalkeeperTechnicalCapacities.contains(capacity)) {
            return false;
        }

        return true;
    }

    private void upgradeCapacity(String capacity, FootballPlayer footballPlayer, TextChannel channel, MessageAuthor author) {
        List<String> goalkeeperTechnicalCapacities = new ArrayList<String>(Arrays.asList("communication", "fist_clearances",
                "clearances", "extension", "excentricity", "ball_catches", "reflexes", "hand_throws", "inbox_exits",
                "in_feet_exits", "one_versus_one"));
        List<String> fieldPlayerTechnicCapacities = new ArrayList<String>(Arrays.asList("centers", "corners", "free_kicks",
                "dribbles", "finition", "headers", "marking", "penalty", "tackles", "technique", "long_shots",
                "long_throws"));
        List<String> physicalCapacities = new ArrayList<String>(Arrays.asList("acceleration", "agility", "vertical_extension",
                "endurance", "endurance", "equilibrium", "power", "natural_physic_abilities", "speed"));
        List<String> mentalCapacities = new ArrayList<>(Arrays.asList("aggressiveness", "anticipation", "ball_calls",
                "concentration", "bravery", "decision_making", "determination", "inspiration", "collective", "leadership",
                "placement", "cold_blood", "game_view", "game_volume"));
        List<String> generalTechnicCapacities = new ArrayList<>(Arrays.asList("passes", "ball_control"));

        if (goalkeeperTechnicalCapacities.contains(capacity) || fieldPlayerTechnicCapacities.contains(capacity) ||
            generalTechnicCapacities.contains(capacity)) {
            upgradeTechnicalCapacity(capacity, footballPlayer, channel, author);
        } else if (physicalCapacities.contains(capacity)) {
            upgradePhysicalCapacity(capacity, footballPlayer, channel, author);
        } else if (mentalCapacities.contains(capacity)) {
            upgradeMentalCapacity(capacity, footballPlayer, channel, author);
        }
    }

    private void upgradePhysicalCapacity(String capacity, FootballPlayer footballPlayer, TextChannel channel, MessageAuthor author) {
        PhysicalAttributes physicalAttributes = footballPlayer.getPhysicalAttributes();
        UpdatePhysicalAttributesResource updatePhysicalAttributesResource = new UpdatePhysicalAttributesResource();

        updatePhysicalAttributesResource.setFootballPlayerId(footballPlayer.getId());
        updatePhysicalAttributesResource.setAcceleration(physicalAttributes.getAcceleration());
        updatePhysicalAttributesResource.setAgility(physicalAttributes.getAgility());
        updatePhysicalAttributesResource.setVerticalExtension(physicalAttributes.getVerticalExtension());
        updatePhysicalAttributesResource.setEndurance(physicalAttributes.getEndurance());
        updatePhysicalAttributesResource.setEquilibrium(physicalAttributes.getEquilibrium());
        updatePhysicalAttributesResource.setPower(physicalAttributes.getPower());
        updatePhysicalAttributesResource.setNaturalPhysicAbilities(physicalAttributes.getNaturalPhysicAbilities());
        updatePhysicalAttributesResource.setSpeed(physicalAttributes.getSpeed());

        switch (capacity) {
            case "acceleration":
                if (!canUpgrade(physicalAttributes.getAcceleration())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setAcceleration(physicalAttributes.getAcceleration() + 1);
                channel.sendMessage("Félicitations, votre **accélération** a augmenté de **1** !");

                break;
            case "agility":
                if (!canUpgrade(physicalAttributes.getAgility())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setAgility(physicalAttributes.getAgility() + 1);
                channel.sendMessage("Félicitations, votre **agilité** a augmenté de **1** !");
                break;
            case "vertical_extension":
                if (!canUpgrade(physicalAttributes.getVerticalExtension())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setVerticalExtension(physicalAttributes.getVerticalExtension() + 1);
                channel.sendMessage("Félicitations, votre **détente verticale** a augmenté de **1** !");
                break;
            case "endurance":
                if (!canUpgrade(physicalAttributes.getEndurance())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setEndurance(physicalAttributes.getEndurance() + 1);
                channel.sendMessage("Félicitations, votre **endurance** a augmenté de **1** !");
                break;
            case "equilibrium":
                if (!canUpgrade(physicalAttributes.getEquilibrium())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setEquilibrium(physicalAttributes.getEquilibrium() + 1);
                channel.sendMessage("Félicitations, votre **équilibre** a augmenté de **1** !");
                break;
            case "power":
                if (!canUpgrade(physicalAttributes.getPower())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setPower(physicalAttributes.getPower() + 1);
                channel.sendMessage("Félicitations, votre **puissance** a augmenté de **1** !");
                break;
            case "natural_physic_abilities":
                if (!canUpgrade(physicalAttributes.getNaturalPhysicAbilities())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setNaturalPhysicAbilities(physicalAttributes.getNaturalPhysicAbilities() + 1);
                channel.sendMessage("Félicitations, vos **qualités physiques naturelles** ont augmenté de **1** !");
                break;
            case "speed":
                if (!canUpgrade(physicalAttributes.getSpeed())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updatePhysicalAttributesResource.setAttributeUpdated(capacity);
                updatePhysicalAttributesResource.setSpeed(physicalAttributes.getSpeed() + 1);
                channel.sendMessage("Félicitations, votre **vitesse** a augmenté de **1** !");
                break;
        }

        this.physicalAttributesService.updatePhysicalAttributes(updatePhysicalAttributesResource);
    }

    private void upgradeTechnicalCapacity(String capacity, FootballPlayer footballPlayer, TextChannel channel, MessageAuthor author) {
        if (footballPlayer.getPost().equals(EPost.GOALKEEPER)) {
            GoalkeeperAttributes goalkeeperAttributes = (GoalkeeperAttributes) footballPlayer.getTechnicalAttributes();
            UpdateTechnicalGoalKeeperAttributesResource updateTechnicalGoalKeeperAttributesResource = new UpdateTechnicalGoalKeeperAttributesResource();

            updateTechnicalGoalKeeperAttributesResource.setFootballPlayerId(footballPlayer.getId());
            updateTechnicalGoalKeeperAttributesResource.setCommunication(goalkeeperAttributes.getCommunication());
            updateTechnicalGoalKeeperAttributesResource.setFistClearances(goalkeeperAttributes.getFistClearances());
            updateTechnicalGoalKeeperAttributesResource.setClearances(goalkeeperAttributes.getClearances());
            updateTechnicalGoalKeeperAttributesResource.setExtension(goalkeeperAttributes.getExtension());
            updateTechnicalGoalKeeperAttributesResource.setExcentricity(goalkeeperAttributes.getExcentricity());
            updateTechnicalGoalKeeperAttributesResource.setBallCatches(goalkeeperAttributes.getBallCatches());
            updateTechnicalGoalKeeperAttributesResource.setReflexes(goalkeeperAttributes.getReflexes());
            updateTechnicalGoalKeeperAttributesResource.setHandThrows(goalkeeperAttributes.getHandThrows());
            updateTechnicalGoalKeeperAttributesResource.setInboxExits(goalkeeperAttributes.getInboxExits());
            updateTechnicalGoalKeeperAttributesResource.setInFeetExits(goalkeeperAttributes.getInFeetExits());
            updateTechnicalGoalKeeperAttributesResource.setOneVersusOne(goalkeeperAttributes.getOneVersusOne());
            updateTechnicalGoalKeeperAttributesResource.setPasses(goalkeeperAttributes.getPasses());
            updateTechnicalGoalKeeperAttributesResource.setBallControl(goalkeeperAttributes.getBallControl());

            switch (capacity) {
                case "communication":
                    if (!canUpgrade(goalkeeperAttributes.getCommunication())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setCommunication(goalkeeperAttributes.getCommunication() + 1);
                    channel.sendMessage("Félicitations, votre **communication** a augmenté de **1** !");

                    break;
                case "fist_clearances":
                    if (!canUpgrade(goalkeeperAttributes.getFistClearances())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setFistClearances(goalkeeperAttributes.getFistClearances() + 1);
                    channel.sendMessage("Félicitations, vos **dégagements au poing** ont augmenté de **1** !");
                    break;
                case "clearances":
                    if (!canUpgrade(goalkeeperAttributes.getClearances())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setClearances(goalkeeperAttributes.getClearances() + 1);
                    channel.sendMessage("Félicitations, vos **dégagements** ont augmenté de **1** !");
                    break;
                case "extension":
                    if (!canUpgrade(goalkeeperAttributes.getExtension())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setExtension(goalkeeperAttributes.getExtension() + 1);
                    channel.sendMessage("Félicitations, votre **détente** a augmenté de **1** !");
                    break;
                case "excentricity":
                    if (!canUpgrade(goalkeeperAttributes.getExcentricity())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setExcentricity(goalkeeperAttributes.getExcentricity() + 1);
                    channel.sendMessage("Félicitations, votre **excentricité** a augmenté de **1** !");
                    break;
                case "ball_catches":
                    if (!canUpgrade(goalkeeperAttributes.getBallCatches())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setBallCatches(goalkeeperAttributes.getBallCatches() + 1);
                    channel.sendMessage("Félicitations, vos **prises de balle** ont augmenté de **1** !");
                    break;
                case "reflexes":
                    if (!canUpgrade(goalkeeperAttributes.getReflexes())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setReflexes(goalkeeperAttributes.getReflexes() + 1);
                    channel.sendMessage("Félicitations, vos **réflexes** ont augmenté de **1** !");
                    break;
                case "hand_throws":
                    if (!canUpgrade(goalkeeperAttributes.getHandThrows())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setHandThrows(goalkeeperAttributes.getHandThrows() + 1);
                    channel.sendMessage("Félicitations, vos **relances à la main** ont augmenté de **1** !");
                    break;
                case "inbox_exits":
                    if (!canUpgrade(goalkeeperAttributes.getInboxExits())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setInboxExits(goalkeeperAttributes.getInboxExits() + 1);
                    channel.sendMessage("Félicitations, vos **sorties dans la surface** ont augmenté de **1** !");
                    break;
                case "in_feet_exits":
                    if (!canUpgrade(goalkeeperAttributes.getInFeetExits())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setInFeetExits(goalkeeperAttributes.getInFeetExits() + 1);
                    channel.sendMessage("Félicitations, vos **sorties dans les pieds** ont augmenté de **1** !");
                    break;
                case "one_versus_one":
                    if (!canUpgrade(goalkeeperAttributes.getOneVersusOne())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setOneVersusOne(goalkeeperAttributes.getOneVersusOne() + 1);
                    channel.sendMessage("Félicitations, votre **un contre un** a augmenté de **1** !");
                    break;
                case "passes":
                    if (!canUpgrade(goalkeeperAttributes.getPasses())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setPasses(goalkeeperAttributes.getPasses() + 1);
                    channel.sendMessage("Félicitations, vos **passes** ont augmenté de **1** !");
                    break;
                case "ball_control":
                    if (!canUpgrade(goalkeeperAttributes.getBallControl())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalGoalKeeperAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalGoalKeeperAttributesResource.setBallControl(goalkeeperAttributes.getBallControl() + 1);
                    channel.sendMessage("Félicitations, votre **contrôle de balle** a augmenté de **1** !");
                    break;
            }

            this.technicalAttributesService.updateGoalkeeperTechnicalAttributes(updateTechnicalGoalKeeperAttributesResource);
        } else {
            FieldPlayerAttributes fieldPlayerAttributes = (FieldPlayerAttributes) footballPlayer.getTechnicalAttributes();
            UpdateTechnicalFieldPlayerAttributesResource updateTechnicalFieldPlayerAttributesResource = new UpdateTechnicalFieldPlayerAttributesResource();

            updateTechnicalFieldPlayerAttributesResource.setFootballPlayerId(footballPlayer.getId());
            updateTechnicalFieldPlayerAttributesResource.setCenters(fieldPlayerAttributes.getCenters());
            updateTechnicalFieldPlayerAttributesResource.setCorners(fieldPlayerAttributes.getCorners());
            updateTechnicalFieldPlayerAttributesResource.setFreeKicks(fieldPlayerAttributes.getFreeKicks());
            updateTechnicalFieldPlayerAttributesResource.setDribbles(fieldPlayerAttributes.getDribbles());
            updateTechnicalFieldPlayerAttributesResource.setFinition(fieldPlayerAttributes.getFinition());
            updateTechnicalFieldPlayerAttributesResource.setHeaders(fieldPlayerAttributes.getHeaders());
            updateTechnicalFieldPlayerAttributesResource.setMarking(fieldPlayerAttributes.getMarking());
            updateTechnicalFieldPlayerAttributesResource.setPenalty(fieldPlayerAttributes.getPenalty());
            updateTechnicalFieldPlayerAttributesResource.setTackles(fieldPlayerAttributes.getTackles());
            updateTechnicalFieldPlayerAttributesResource.setTechnique(fieldPlayerAttributes.getTechnique());
            updateTechnicalFieldPlayerAttributesResource.setLongShots(fieldPlayerAttributes.getLongShots());
            updateTechnicalFieldPlayerAttributesResource.setPasses(fieldPlayerAttributes.getPasses());
            updateTechnicalFieldPlayerAttributesResource.setBallControl(fieldPlayerAttributes.getBallControl());

            switch (capacity) {
                case "centers":
                    if (!canUpgrade(fieldPlayerAttributes.getCenters())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setCenters(fieldPlayerAttributes.getCenters() + 1);
                    channel.sendMessage("Félicitations, vos **centres** ont augmenté de **1** !");

                    break;
                case "corners":
                    if (!canUpgrade(fieldPlayerAttributes.getCorners())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setCorners(fieldPlayerAttributes.getCorners() + 1);
                    channel.sendMessage("Félicitations, vos **corners** ont augmenté de **1** !");

                    break;
                case "free_kicks":
                    if (!canUpgrade(fieldPlayerAttributes.getFreeKicks())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setFreeKicks(fieldPlayerAttributes.getFreeKicks() + 1);
                    channel.sendMessage("Félicitations, vos **coups-franc** ont augmenté de **1** !");

                    break;
                case "dribbles":
                    if (!canUpgrade(fieldPlayerAttributes.getDribbles())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setDribbles(fieldPlayerAttributes.getDribbles() + 1);
                    channel.sendMessage("Félicitations, vos **dribbles** ont augmenté de **1** !");

                    break;
                case "finition":
                    if (!canUpgrade(fieldPlayerAttributes.getFinition())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setFinition(fieldPlayerAttributes.getFinition() + 1);
                    channel.sendMessage("Félicitations, votre **finition** a augmenté de **1** !");

                    break;
                case "headers":
                    if (!canUpgrade(fieldPlayerAttributes.getHeaders())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setHeaders(fieldPlayerAttributes.getHeaders() + 1);
                    channel.sendMessage("Félicitations, votre **jeu de tête** a augmenté de **1** !");

                    break;
                case "marking":
                    if (!canUpgrade(fieldPlayerAttributes.getMarking())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setMarking(fieldPlayerAttributes.getMarking() + 1);
                    channel.sendMessage("Félicitations, votre **marquage** a augmenté de **1** !");

                    break;
                case "penalty":
                    if (!canUpgrade(fieldPlayerAttributes.getPenalty())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setPenalty(fieldPlayerAttributes.getPenalty() + 1);
                    channel.sendMessage("Félicitations, vos **penalties** ont augmenté de **1** !");

                    break;
                case "tackles":
                    if (!canUpgrade(fieldPlayerAttributes.getTackles())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setTackles(fieldPlayerAttributes.getTackles() + 1);
                    channel.sendMessage("Félicitations, vos **tacles** ont augmenté de **1** !");

                    break;
                case "technique":
                    if (!canUpgrade(fieldPlayerAttributes.getTechnique())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setTechnique(fieldPlayerAttributes.getTechnique() + 1);
                    channel.sendMessage("Félicitations, votre **technique** a augmenté de **1** !");

                    break;
                case "long_shots":
                    if (!canUpgrade(fieldPlayerAttributes.getLongShots())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setLongShots(fieldPlayerAttributes.getLongShots() + 1);
                    channel.sendMessage("Félicitations, vos **tirs de loin** ont augmenté de **1** !");

                    break;
                case "passes":
                    if (!canUpgrade(fieldPlayerAttributes.getPasses())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setPasses(fieldPlayerAttributes.getPasses() + 1);
                    channel.sendMessage("Félicitations, vos **passes** ont augmenté de **1** !");

                    break;
                case "ball_control":
                    if (!canUpgrade(fieldPlayerAttributes.getBallControl())) {
                        channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                        return;
                    }

                    updateTechnicalFieldPlayerAttributesResource.setAttributeUpdated(capacity);
                    updateTechnicalFieldPlayerAttributesResource.setBallControl(fieldPlayerAttributes.getBallControl() + 1);
                    channel.sendMessage("Félicitations, votre **contrôle de balle** a augmenté de **1** !");

                    break;
            }

            this.technicalAttributesService.updateFieldPlayerTechnicalAttributes(updateTechnicalFieldPlayerAttributesResource);
        }
    }

    private void upgradeMentalCapacity(String capacity, FootballPlayer footballPlayer, TextChannel channel, MessageAuthor author) {
        MentalAttributes mentalAttributes = footballPlayer.getMentalAttributes();
        UpdateMentalAttributesResource updateMentalAttributesResource = new UpdateMentalAttributesResource();

        updateMentalAttributesResource.setFootballPlayerId(footballPlayer.getId());
        updateMentalAttributesResource.setAggressiveness(mentalAttributes.getAggressiveness());
        updateMentalAttributesResource.setAnticipation(mentalAttributes.getAnticipation());
        updateMentalAttributesResource.setBallCalls(mentalAttributes.getBallCalls());
        updateMentalAttributesResource.setConcentration(mentalAttributes.getConcentration());
        updateMentalAttributesResource.setBravery(mentalAttributes.getBravery());
        updateMentalAttributesResource.setDecisionMaking(mentalAttributes.getDecisionMaking());
        updateMentalAttributesResource.setDetermination(mentalAttributes.getDetermination());
        updateMentalAttributesResource.setInspiration(mentalAttributes.getInspiration());
        updateMentalAttributesResource.setCollectiveGame(mentalAttributes.getCollectiveGame());
        updateMentalAttributesResource.setLeadership(mentalAttributes.getLeadership());
        updateMentalAttributesResource.setPlacement(mentalAttributes.getPlacement());
        updateMentalAttributesResource.setColdBlood(mentalAttributes.getColdBlood());
        updateMentalAttributesResource.setGameView(mentalAttributes.getGameView());
        updateMentalAttributesResource.setGameVolume(mentalAttributes.getGameVolume());

        switch (capacity) {
            case "aggressiveness":
                if (!canUpgrade(mentalAttributes.getAggressiveness())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setAggressiveness(mentalAttributes.getAggressiveness() + 1);
                channel.sendMessage("Félicitations, votre **agressivité** a augmenté de **1** !");
                break;
            case "anticipation":
                if (!canUpgrade(mentalAttributes.getAnticipation())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setAnticipation(mentalAttributes.getAnticipation() + 1);
                channel.sendMessage("Félicitations, votre **anticipation** a augmenté de **1** !");
                break;

            case "ball_calls":
                if (!canUpgrade(mentalAttributes.getBallCalls())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setBallCalls(mentalAttributes.getBallCalls() + 1);
                channel.sendMessage("Félicitations, vos **appels de balle** ont augmenté de **1** !");
                break;
            case "concentration":
                if (!canUpgrade(mentalAttributes.getConcentration())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setConcentration(mentalAttributes.getConcentration() + 1);
                channel.sendMessage("Félicitations, votre **concentration** a augmenté de **1** !");
                break;
            case "bravery":
                if (!canUpgrade(mentalAttributes.getBravery())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setBravery(mentalAttributes.getBravery() + 1);
                channel.sendMessage("Félicitations, votre **courage** a augmenté de **1** !");
                break;
            case "decision_making":
                if (!canUpgrade(mentalAttributes.getDecisionMaking())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setDecisionMaking(mentalAttributes.getDecisionMaking() + 1);
                channel.sendMessage("Félicitations, vos **décisions** ont augmenté de **1** !");
                break;
            case "determination":
                if (!canUpgrade(mentalAttributes.getDetermination())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setDetermination(mentalAttributes.getDetermination() + 1);
                channel.sendMessage("Félicitations, votre **détermination** a augmenté de **1** !");
                break;
            case "inspiration":
                if (!canUpgrade(mentalAttributes.getInspiration())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setInspiration(mentalAttributes.getInspiration() + 1);
                channel.sendMessage("Félicitations, votre **inspiration** a augmenté de **1** !");
                break;
            case "collective":
                if (!canUpgrade(mentalAttributes.getCollectiveGame())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setCollectiveGame(mentalAttributes.getCollectiveGame() + 1);
                channel.sendMessage("Félicitations, votre **jeu collectif** a augmenté de **1** !");
                break;
            case "leadership":
                if (!canUpgrade(mentalAttributes.getLeadership())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setLeadership(mentalAttributes.getLeadership() + 1);
                channel.sendMessage("Félicitations, votre **leadership** a augmenté de **1** !");
                break;
            case "placement":
                if (!canUpgrade(mentalAttributes.getPlacement())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setPlacement(mentalAttributes.getPlacement() + 1);
                channel.sendMessage("Félicitations, votre **placement** a augmenté de **1** !");
                break;
            case "cold_blood":
                if (!canUpgrade(mentalAttributes.getColdBlood())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setColdBlood(mentalAttributes.getAggressiveness() + 1);
                channel.sendMessage("Félicitations, votre **sang-froid** a augmenté de **1** !");
                break;
            case "game_view":
                if (!canUpgrade(mentalAttributes.getGameView())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setGameView(mentalAttributes.getGameView() + 1);
                channel.sendMessage("Félicitations, votre **vision de jeu** a augmenté de **1** !");
                break;
            case "game_volume":
                if (!canUpgrade(mentalAttributes.getGameVolume())) {
                    channel.sendMessage("Erreur : cet attribut est déjà monté au maximum.");
                    return;
                }

                updateMentalAttributesResource.setAttributeUpdated(capacity);
                updateMentalAttributesResource.setGameVolume(mentalAttributes.getGameVolume() + 1);
                channel.sendMessage("Félicitations, votre **volume de jeu** a augmenté de **1** !");
                break;

        }

        this.mentalAttributesService.updateMentalAttributes(updateMentalAttributesResource);
    }

    private boolean canUpgrade(int capacityValue) {
        return capacityValue < 20;
    }
}
