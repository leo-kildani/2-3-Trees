public class Tree {
	
	TreeNode root;
	
	public class TreeNode{
		public int keys[] = new int[3];
		public TreeNode[] children = new TreeNode[4];
		public boolean isLeaf = true;
		public int keySlots = 0;
		public TreeNode parent;
		
		public TreeNode() {
		
		}
		
		public TreeNode(int key) {
			keys[0] = key;
			keySlots++;
		}
		
		// add key to node (only called when there is space)
		public void addKey(int newKey, TreeNode left, TreeNode right) {
			int i = keySlots;
			TreeNode temp = children[keySlots];
			
			// insertion sort on keys; if keys shift, children shift
			while (i > 0 && keys[i - 1] > newKey) {
				keys[i] = keys[i - 1];
				children[i] = children[i - 1];
				i--;
			}
			
			// fill in children/keys slots that are empty after shift
			keys[i] = newKey;
			children[i] = left;
			
			if (i == keySlots) {
				children[++keySlots] = right;
			} else {
				children[++i] = right;
				children[++keySlots] = temp;
			}
		}
		
		public int getMedian() {
			return (keys[keys.length / 2]);
		}
		
		public boolean contains(int key) {
			for (int i = 0; i < keySlots; i++) {
				if (keys[i] == key)
					return true;
			}
			return false;
		}
		
		public TreeNode search(int target) {
			if (this.isLeaf) return this;
			
			int i = 0;
			// determine what key/child to check
			while (i < keySlots && target > keys[i]) {
				i++;
			}
			if (i < keySlots && target == keys[i]) {
				return this;
			} else {
				return children[i].search(target);	
			}
		}
		
		public TreeNode split() {
			TreeNode returnNode = null;
			TreeNode left = new TreeNode(keys[0]);
			TreeNode right = new TreeNode(keys[2]);
			
			left.children[0] = children[0];
			left.children[1] = children[1];
			right.children[0] = children[2];
			right.children[1] = children[3];
			if (parent == null) {
				parent = new TreeNode();
				returnNode = parent;
			}
			
			left.parent = parent;
			right.parent = parent;
			parent.addKey(getMedian(), left, right);
			parent.isLeaf = false;
			if (parent.keySlots == parent.keys.length) {
				returnNode = parent.split();
			}
			
			return returnNode;
			
		}
	}
	
	public Tree() {
		root = null;
	}
	
	public boolean insert(int key) {
		if (root == null) {
			TreeNode newRoot = new TreeNode(key);
			root = newRoot;
			return true;
		} 
		
		TreeNode nodeToInsert = root.search(key);
		if (nodeToInsert.contains(key)) {
			return false;
		}
		
		nodeToInsert.addKey(key, null, null);
		if (nodeToInsert.keySlots == nodeToInsert.keys.length) {
			split(nodeToInsert);
		}
		return true;
	}
	
	public void split(TreeNode node) {
		TreeNode changedRoot = node.split();
		if (changedRoot != null) {
			root = changedRoot;
		}
		
	}
	
	
	public int size() {
		return 0;
	}
	
	public int size(int key) {
		return 0;
	}
	
	public int get(int idx) {
		return 0;
	}
}
