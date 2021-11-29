package dev.encode42.copper.util;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.EnumSet;

public class PermissionUtil {
    /**
     * Add a member to a channel's override.
     * @param channel Channel to add member to
     * @param member Member to add to channel
     */
    public static void addMember(TextChannel channel, Member member) {
        channel.createPermissionOverride(member)
                .setAllow(EnumSet.of(Permission.VIEW_CHANNEL))
                .queue();
    }

    // todo: currently not working
    /**
     * Remove a member from a channel's override.
     * @param channel Channel to remove member from
     * @param member Member to remove from channel
     */
    public static void removeMember(TextChannel channel, Member member) {
        for (PermissionOverride permission : channel.getPermissionOverrides()) {
            if (permission.getMember() == member) {
                permission.delete().queue();
            }
        }
    }

    /**
     * Check if a member of a server has a role ID.
     * @param member Member to check
     * @param role Role to check
     * @return Whether the member has the role
     */
    public static boolean hasRole(Member member, String role) {
        return member.getRoles().contains(BotUtil.getJda().getRoleById(role));
    }
}
