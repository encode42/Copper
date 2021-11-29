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

	/**
	 * Get a Discord-formatted date that embeds in a message.
	 * @param time Time to create the embed
	 * @param flag Flags to create the embed with
	 * @return Formatted date embed
	 */
	public static String formatTimestamp(long time, TimestampFlags flag) {
		return "<t:" + time + ":" + flag.getFormat() + ">";
	}

	/**
	 * Get a Discord-formatted date that embeds in a message.
	 * @param time Time to create the embed
	 * @return Formatted date embed
	 */
	public static String formatTimestamp(long time) {
		return formatTimestamp(time, TimestampFlags.LONG_DATE_TIME);
	}

	/**
	 * Get a Discord-formatted date for the current time that embeds in a message.
	 * @param flags Flags to create the embed with
	 * @return Formatted date embed
	 */
	public static String formatTimestamp(TimestampFlags flags) {
		return formatTimestamp(Util.getEpoch(), flags);
	}

	/**
	 * Get a Discord-formatted date for the current time that embeds in a message.
	 * @return Formatted date embed
	 */
	public static String formatTimestamp() {
		return formatTimestamp(Util.getEpoch(), TimestampFlags.LONG_DATE_TIME);
	}
}
