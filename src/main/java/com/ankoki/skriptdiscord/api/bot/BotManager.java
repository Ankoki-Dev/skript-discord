package com.ankoki.skriptdiscord.api.bot;

import ch.njol.skript.Skript;
import com.ankoki.skriptdiscord.utils.Console;
import net.dv8tion.jda.api.JDA;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BotManager {

    // Map that stores the DiscordBot object to the name.
    private static final Map<String, DiscordBot> DISCORD_BOT_MAP = new ConcurrentHashMap<>();

    /**
     * Registers a discord with the manager.
     *
     * @param bot the bot to register.
     */
    public static void registerBot(DiscordBot bot) {
        if (DISCORD_BOT_MAP.containsKey(bot.getName())) {
            Skript.warning("A bot with this name (" + bot.getName() + ") already exists! It will be replaced");
            DiscordBot tempBot = DISCORD_BOT_MAP.get(bot.getName());
            BotManager.disable(tempBot);
        }
        DISCORD_BOT_MAP.put(bot.getName(), bot);
    }

    /**
     * Gets the first registered bot.
     *
     * @return the first registered bot with the manager, if there is no bot, null.
     */
    public static DiscordBot getFirstBot() {
        DiscordBot[] array = DISCORD_BOT_MAP.values().toArray(new DiscordBot[0]);
        if (array.length <= 0) {
            Console.debug("There was no bot to perform this action. Please login to a bot!");
            return null;
        }
        return array[0];
    }

    /**
     * Gets a discord bot from its name.
     *
     * @param name name of the bot wanted.
     * @return the bot with said name, if it's not a registered bot, null.
     */
    public static DiscordBot getBot(String name) {
        return DISCORD_BOT_MAP.get(name);
    }

    /**
     * Gets a discord bot from the JDA.
     *
     * @param jda jda of the bot wanted.
     * @return the bot with said JDA, if it's not a registered bot, null.
     */
    public static DiscordBot getBot(JDA jda) {
        for (Map.Entry<String, DiscordBot> entry : DISCORD_BOT_MAP.entrySet()) {
            if (jda == entry.getValue().getJda()) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Disables a bot.
     *
     * @param bot name of the bot that should be disabled.
     */
    public static void disable(String bot) {
        disable(DISCORD_BOT_MAP.get(bot));
    }

    /**
     * Disables a bot.
     *
     * @param bot bot that should be disabled.
     */
    public static void disable(DiscordBot bot) {
        if (bot == null) return;
        bot.getJda().shutdown();
        Console.log("The bot " + bot.getName() + " just got shutdown.");
        DISCORD_BOT_MAP.remove(bot.getName());
    }

    /**
     * Disables all registered bots.
     */
    public static void disableAll() {
        DISCORD_BOT_MAP.forEach((name , bot) -> {
            BotManager.disable(bot);
        });
    }

    /**
     * Checks if a bot is enabled/registered or not.
     *
     * @param bot name of the bot.
     * @return if a bot with the given name is registered.
     */
    public static boolean isRegistered(String bot) {
        return DISCORD_BOT_MAP.containsKey(bot);
    }
}
