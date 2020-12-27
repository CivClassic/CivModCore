package vg.civcraft.mc.civmodcore.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public final class Trie {

	private final List<String> completions;

	public static Trie getNewTrie() {
		return new Trie();
	}
	
	private Trie() {
		this.completions = new ArrayList<>();
	}


	public void insert(String completion) {
		this.completions.add(completion);
	}
	
	public List<String> match(String[] args) {
		if (args.length == 0) {
			return completions;
		}

		// number of words to drop from completions
		int words = args.length - 1;

		List<String> matches = new ArrayList<>(completions);
		ListIterator<String> it = matches.listIterator();
		OUTER:
		while (it.hasNext()) {
			String completion = it.next();
			String[] completionWords = completion.split(" ");
			for (int i = 0; i < Math.min(completionWords.length, args.length); i++) {
				String completionWord = completionWords[i];
				String word = args[i];

				if (!completionWord.regionMatches(true, 0, word, 0, word.length())) {
					it.remove();
					continue OUTER;
				}
			}
			String[] result = Arrays.copyOfRange(completionWords, words, completionWords.length);
			it.set(String.join(" ", result));
		}
		return matches;
	}
}
