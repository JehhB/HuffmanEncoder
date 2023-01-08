package huffmantree;

import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.tree.DefaultTreeModel;
import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import java.io.IOException;

public class HuffmanTreeModel extends DefaultTreeModel {
	private HashMap<Character, HuffmanCharacterNode> map;

	public static HuffmanTreeNode readTree(BitInput in) throws IOException {
		if (in.readBoolean()) {
			return new HuffmanCharacterNode(in.readChar(Character.SIZE), 0);
		}

		var left = readTree(in);
		var right = readTree(in);

		var parent = new HuffmanParentNode(left, right);
		left.setParent(parent);
		right.setParent(parent);

		return parent;
	}

	public void encodeTree(BitOutput out) throws IOException {
		((HuffmanTreeNode) root).encodeTree(out);
	}

	public void encode(BitOutput out, String text) throws IOException {
		for (int i = 0; i < text.length(); ++i) {
			var node = map.get(text.charAt(i));
			node.encode(out);
		}
	}

	public HuffmanTreeModel() {
		super(null);
		map = null;
	}

	public void setText(String text) {
		var count = new HashMap<Character, Integer>();
		for (int i = 0; i < text.length(); ++i) {
			count.compute(text.charAt(i), (k, v) -> v == null ? 1 : v + 1);
		}

		var priorityQueue = new TreeSet<HuffmanTreeNode>();
		count.forEach((character, frequency) -> {
			var node = new HuffmanCharacterNode(character, frequency);
			priorityQueue.add(node);
		});

		map = new HashMap<>();
		priorityQueue.forEach((node) -> {
			map.put(((HuffmanCharacterNode) node).character, (HuffmanCharacterNode) node);
		});

		while (priorityQueue.size() > 1) {
			var first = priorityQueue.pollFirst();
			var second = priorityQueue.pollFirst();

			var parent = new HuffmanParentNode(first, second);
			first.setParent(parent);
			second.setParent(parent);

			priorityQueue.add(parent);
		}

		root = priorityQueue.size() == 1 ? priorityQueue.first() : null;
		setRoot(root);
	}

	public int getSize() {
		var res = new Object() {
			public int out = 0;

			public void add(int a) {
				out += a;
			}
		};

		map.forEach((a, node) -> {
		res.add(node.frequency * node.getLevel());
		});

		return res.out;
	}
}
