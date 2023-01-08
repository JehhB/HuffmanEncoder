package huffmantree;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;

public class HuffmanParentNode implements HuffmanTreeNode {
	private final int weight;
	private HuffmanParentNode parent; 
	private final HuffmanTreeNode right, left;

	public HuffmanParentNode(HuffmanTreeNode left, HuffmanTreeNode right) {
		this.weight = left.getWeight() + right.getWeight();
		this.left = left;
		this.right = right;
		this.parent = null;
	}

	@Override
	public String toString() {
		return weight + "";
	}

	@Override
	public List<String> getCharacters() {
		var left = this.left.getCharacters();
		var right = this.right.getCharacters();
		left.addAll(right);
		return left;
	}

	@Override
	public void setParent(HuffmanParentNode parent) {
		this.parent = parent;
	}


	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public TreeNode getChildAt(int i) {
		if (i == 0) return right;
		if (i == 1) return left;
		return null;
	}

	@Override
	public int getChildCount() {
		return 2;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode tn) {
		if (tn == right) return 0;
		if (tn == left) return 1;
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Enumeration<? extends TreeNode> children() {
		return new Enumeration<>() {
			private int i = 0;

			@Override
			public boolean hasMoreElements() {
				return i < 2;
			}

			@Override
			public TreeNode nextElement() {
				return getChildAt(i++);
			}
		};
	}

	@Override
	public void encodeTree (BitOutput out) throws IOException {
		out.writeBoolean(false);
		left.encodeTree(out);
		right.encodeTree(out);
	}

	@Override
	public char decode(BitInput in) throws IOException {
		if (!in.readBoolean()) return left.decode(in);
		else return right.decode(in);
	}
}
