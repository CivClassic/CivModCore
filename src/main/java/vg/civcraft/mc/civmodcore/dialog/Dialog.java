package vg.civcraft.mc.civmodcore.dialog;

import java.util.List;

import io.protonull.utilities.Exists;
import lombok.Getter;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Dialog {

	@Getter
	private final Player player;
	private final Conversation conversation;

	public Dialog(Player player, JavaPlugin plugin) {
		this(player, plugin, null);
	}

	public Dialog(Player player, JavaPlugin plugin, String prompt) {
		this.player = player;
		DialogManager.register(player, this);
		this.conversation = new ConversationFactory(plugin)
				.withModality(false)
				.withLocalEcho(false)
				.withFirstPrompt(new StringPrompt() {
					@Override
					public String getPromptText(ConversationContext context) {
						if (Exists.string(prompt)) {
							return prompt;
						}
						return "";
					}
					@Override
					public Prompt acceptInput(ConversationContext context, String message) {
						onResponse(message);
						return Prompt.END_OF_CONVERSATION;
					}
				}).buildConversation(player);
		this.conversation.begin();
	}

	/**
	 * @deprecated Kept for backwards compatibility, please use .onResponse(String) instead.
	 * @see Dialog .onResponse(String)
	 * */
	@Deprecated
	public void onReply(String[] message) {}

	/**
	 * @deprecated Kept for backwards compatibility, please use .onTabComplete(String, String) instead.
	 * @see Dialog .onTabComplete(String, String)
	 * */
	@Deprecated
	public List<String> onTabComplete(String wordCompleted, String[] fullMessage) {
		return null;
	}

	/**
	 * Overwrite this method!
	 * */
	public void onResponse(String message) {
		if (Exists.string(message)) {
			onReply(message.split(" "));
		}
		else {
			onReply(new String[0]);
		}
	}

	/**
	 * Overwrite this method!
	 * */
	public List<String> onTabComplete(String word, String message) {
		if (Exists.string(message)) {
			return onTabComplete(word, message.split(" "));
		}
		else {
			return onTabComplete(word, new String[0]);
		}
	}

	/**
	 * Overwrite this method!
	 * */
	public void onCancelled() {

	}

	public void end() {
		this.conversation.abandon();
		onCancelled();
	}

}
