package dev.encode42.copper.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Random;

public class BotUtil extends Util {
	private static JDA jda;

	/**
	 * Generates a seeded random with member ID.
	 * @param member Member to generate seed for
	 * @param methods Calendar methods to use
	 * @return Seeded random generator
	 */
	public static Random getSeededRandom(Member member, int ...methods) {
		SeededRandom random = getDatedRandom(methods);
		long seed = random.getSeed();

		// Make the random instance
		return new Random(member.getIdLong() + seed);
	}

	/**
	 * Check if a server member is an admin.
	 * @param member Member to check
	 * @return Whether the member is an admin
	 */
	public static boolean isAdmin(Member member) {
		return member.isOwner() || member.hasPermission(Permission.ADMINISTRATOR) || member.hasPermission(Permission.MANAGE_SERVER);
	}

	/**
	 * Sets the utilized JDA instance.
	 * @param jda JDA instance
	 */
	public static void setJda(JDA jda) {
		BotUtil.jda = jda;
	}

	/**
	 * Gets the utilized JDA instance.
	 * @return JDA instance
	 */
	public static JDA getJda() {
		return jda;
	}
}
