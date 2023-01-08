package huffmantree;

import com.github.jinahya.bit.io.BitInput;
import java.util.ArrayList;
import javax.swing.tree.TreeNode;
import com.github.jinahya.bit.io.BitOutput;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface HuffmanTreeNode extends TreeNode, Comparable<HuffmanTreeNode> {
	public int getWeight();

	public void setParent(HuffmanParentNode parent);

	public List<String> getCharacters();

	public void encodeTree(BitOutput out) throws IOException;

	public char decode(BitInput in) throws IOException;

	public default int getLevel() {
		var parent = (HuffmanTreeNode) getParent();
		if (parent == null) { 
			return 1;
		}

		return 1 + parent.getLevel();
	}

	public default void encode(BitOutput out) throws IOException {
		var repr = getRepresentation();
		repr.forEach(value -> {
			try {
				out.writeBoolean(value);
			} catch (IOException ex) {
				Logger.getLogger(HuffmanTreeNode.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	public default List<Boolean> getRepresentation() {
		var parent = (HuffmanParentNode) getParent();
		if (parent == null) return new ArrayList<>();
		var prefix = parent.getRepresentation();
		prefix.add(parent.getIndex(this) == 0);
		return prefix;
	}


	public default String getRepresentationString() {
		var encoding = getRepresentation();
		var res = new Object(){ 
			String out = "";

			void add(Boolean b) {
				out += b ? "1" : "0";
			}
		};
		encoding.forEach((b) -> {
			res.add(b);
		});

		return res.out;
	}

	@Override
	public default int compareTo(HuffmanTreeNode o) {
		int res = getWeight() - o.getWeight();
		return res == 0 ? -1 : res;
	}
}
