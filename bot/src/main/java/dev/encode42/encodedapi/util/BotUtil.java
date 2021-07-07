package dev.encode42.encodedapi.util;

import dev.encode42.encodedapi.util.SeededRandom;
import dev.encode42.encodedapi.util.Util;
import dev.encode42.encodedapi.config.Language;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
//import org.reflections.Reflections;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Set;

public class BotUtil extends Util {
	/* todo: dynamic this
	/ **
	 * Registers all listeners annotated with the Listener interface
	 * @author YouHaveTrouble
	 * @author https://github.com/YouHaveTrouble/ServerBasics
	 * /
	public static void registerListeners() {
		JDA jda = Tixits.getJDA();
		Logger logger = Tixits.getLogger();
		Language lang = Tixits.getLang();

		Reflections reflections = new Reflections((Object) new String[]{"dev.encode42.tixits.listeners"});
		Set<Class<?>> listeners = reflections.getTypesAnnotatedWith(Listener.class);
		for (Class<?> listener : listeners) {
			logger.info(lang.read("logger.listeners.register", listener.getSimpleName()));
			try {
				jda.addEventListener(listener.getConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		logger.info(lang.read("logger.listeners.finished", jda.getEventManager().getRegisteredListeners().size()));
	}
	*/

	/**
	 * Generates a seeded random with member ID
	 * @param member Member to generate seed for
	 * @param methods Calendar methods to use
	 * @return Seeded random generator
	 */
	public static Random getSeededRandom(Member member, int ...methods) {
		SeededRandom random = Util.getSeededRandom(methods);
		long seed = random.getSeed();

		// Make the random instance
		return new Random(member.getIdLong() + seed);
	}

	/**
	 * Check if a server member is an admin
	 * @param member Member to check
	 * @return Whether or not the member is an admin
	 */
	public static boolean isAdmin(Member member) {
		return member.isOwner() || member.hasPermission(Permission.ADMINISTRATOR) || member.hasPermission(Permission.MANAGE_SERVER);
	}
}
