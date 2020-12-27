package vg.civcraft.mc.civmodcore.commands;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import vg.civcraft.mc.civmodcore.command.Trie;

public class TrieTest {
	private final Trie trie = Trie.getNewTrie();

	{
		trie.insert("advanced cauldron");
		trie.insert("aesthetics factory");
		trie.insert("bakery");
		trie.insert("basic cauldron");
	}

	@Test
	public void testNoArgs() {
		List<String> match = trie.match(new String[0]);
		Assert.assertEquals(List.of("advanced cauldron", "aesthetics factory", "bakery", "basic cauldron"), match);
	}

	@Test
	public void testOneArg() {
		List<String> match = trie.match(new String[] {"a"});
		Assert.assertEquals(List.of("advanced cauldron", "aesthetics factory"), match);
	}

	@Test
	public void testOneAndEmptyArg() {
		List<String> match = trie.match(new String[] {"advanced", ""});
		Assert.assertEquals(List.of("cauldron"), match);
	}

	@Test
	public void testTwoArgs() {
		List<String> match = trie.match(new String[] {"advanced", "c"});
		Assert.assertEquals(List.of("cauldron"), match);
	}
}
