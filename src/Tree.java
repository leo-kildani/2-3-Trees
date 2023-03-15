public class Tree {
	
	TreeNode root;
	
	public class TreeNode{
		public int keys[] = new int[3];
		public TreeNode[] children = new TreeNode[4];
		public boolean isLeaf = true;
		public int keySlots = 0;
		public TreeNode parent;
		
		public TreeNode() {
			// Default constructor
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
		
		// Return the middle key of a node
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
		
		// Search for key, if node contains key, return; recurse until leaf is hit
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
			
			// redistribute children/parents
			left.isLeaf = isLeaf;
			right.isLeaf = isLeaf;
			for (int i = 0; i < children.length/2; i++) {
				left.children[i] = children[i];
				if (left.children[i] != null) {
					left.children[i].parent = left;
				}
				right.children[i] = children[children.length/2 + i];
				if (right.children[i] != null) {
					right.children[i].parent = right;
				}
			}
			
			if (parent == null) {
				parent = new TreeNode();
				returnNode = parent;
				parent.isLeaf = false;
			}
			left.parent = parent;
			right.parent = parent;
			parent.addKey(getMedian(), left, right);

			if (parent.keySlots == parent.keys.length) {
				returnNode = parent.split();
			}
			
			return returnNode;
		}
		
		public int size() {
			int size = keySlots;
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null) {
					size += children[i].size();
				}
				
			}	
			return size;
		}
		
		private int get(int idx) {
			for (int i = 0; i <= keySlots; i++) {
				if (children[i] != null) {
					int size = children[i].size();
					if (children[i].size() > idx - cnt) {
						return children[i].get(idx);
					} else {
						cnt += size;
					}
				}
				
				if (i < keySlots && cnt++ == idx) {
					return keys[i];
				}
			}
			return 0;
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
		if (root == null) {
			return 0;
		}
		return root.size();
	}
	
	public int size(int key) {
		TreeNode subRoot = root.search(key);
		if (subRoot.contains(key)) {
			return subRoot.size();
		}
		return 0;
	}
	
	private int cnt = 0;
	public int get(int idx) {
		if (cnt != 0) cnt = 0;
		return root.get(idx);
	}
}
