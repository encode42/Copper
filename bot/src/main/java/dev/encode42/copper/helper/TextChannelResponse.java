package dev.encode42.copper.helper;

import net.dv8tion.jda.api.entities.TextChannel;

public class TextChannelResponse {
    private boolean completed;
    private TextChannel channel;

    /**
     * A TextChannel container with completed status.
     * @param channel TextChannel to store
     * @param completed Whether the channel is initialized
     */
    public TextChannelResponse(TextChannel channel, boolean completed) {
        this.channel = channel;
        this.completed = completed;
    }

    public TextChannelResponse(TextChannel channel) {
        this(channel, false);
    }

    public TextChannelResponse(boolean completed) {
        this(null, completed);
    }

    public TextChannelResponse() {
        this(null, false);
    }

    /**
     * Set the stored TextChannel.
     * @param channel TextChannel to store
     */
    public void setChannel(TextChannel channel) {
        this.channel = channel;
    }

    /**
     * Set the container's completion status.
     * @param completed Completion status
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Get the stored TextChannel.
     * @return Stored TextChannel
     */
    public TextChannel getChannel() {
        return channel;
    }

    /**
     * Whether the TextChannel has been initialized.
     * @return TextChannel completion status.
     */
    public boolean isCompleted() {
        return completed;
    }
}