package huffmantree;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
//
class HuffmanCharacterNode implements HuffmanTreeNode {
	public final char character;
	public final int frequency;
	private HuffmanParentNode parent;

	public HuffmanCharacterNode(char character, int frequency) {
		this.character = character;
		this.frequency = frequency;
	}

	@Override
	public List<String> getCharacters() {
		var temp = new ArrayList<String>();
		temp.add(character + "");
		return temp;
	}

	@Override 
	public String toString() {
		return String.format("(%s) %d", character, frequency);
	}

	@Override
	public void setParent(HuffmanParentNode parent) {
		this.parent = parent;
	}

	@Override
	public int getWeight() {
		return frequency;
	}

	@Override
	public TreeNode getChildAt(int i) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode tn) {
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Enumeration<? extends TreeNode> children() {
		return new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				return false;
			}

			@Override
			public TreeNode nextElement() {
				return null;
			}

		};
	}

	@Override
	public void encodeTree(BitOutput out) throws IOException {
		out.writeBoolean(true);
		out.writeChar(Character.SIZE, character);
	}

	@Override
	public char decode(BitInput in) throws IOException {
		return character;
	}

}
