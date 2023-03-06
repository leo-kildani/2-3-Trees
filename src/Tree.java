import java.util.Stack;

public class Tree {
	
	TreeNode root;
	
	public class TreeNode{
		public Integer minKey, maxKey;
		public TreeNode leftChild, midChild, rightChild, parent;
		public TreeNode leftMidChild, rightMidChild;
		public int slots;
		
		
		public TreeNode(int val) {
			minKey = val;
			slots = 1;
		}
		
		// add key to node, return middle value if slots are full
		public void addKey(int newKey) {
			if (newKey > minKey) {
				maxKey = newKey;
			} else {
				maxKey = minKey;
				minKey = newKey;
			}
			slots = 2;
		}
		
		public boolean contains(int key) {
			boolean contains = false;
			if(minKey == key || (maxKey != null && maxKey == key)) {
				contains = true;
			}
			return contains;
		}
		
		public int setMidKey(int key) {
			int midKey;
			if (key < minKey) {
				midKey = minKey;
				minKey = key;
			} else if (key > maxKey) {
				midKey = maxKey;
				maxKey = key;
			} else {
				midKey = key;
			}
			return midKey;
		}
		
		public void distributeChildren(TreeNode left, TreeNode right, TreeNode p) {
			left.parent = p;
			right.parent = p;
			
			if (this.leftChild != null) {
				left.leftChild = this.leftChild;
				this.leftChild.parent = left;
			}
			if (this.leftMidChild != null) {
				left.rightChild = this.leftMidChild;
				this.leftMidChild.parent = left;
			}
			if (this.rightMidChild != null) {
				right.leftChild = this.rightMidChild;
				this.rightMidChild.parent = right;
			}
			if (this.rightChild != null) {
				right.rightChild = this.rightChild;
				this.rightChild.parent = right;
			}
		}
		
		public TreeNode search(int target) {
			if (this.contains(target)) return this;
			
			if (target < minKey) {
				leftChild.search(target);
			} else {
				if (slots == 1) {
					// LEFT OFF HERE
				}
			}
			
	}
	
	public Tree() {
		root = null;
	}
	
	public boolean insert(int key) {
		if (root == null) {
			TreeNode newNode = new TreeNode(key);
			root = newNode;
			return true;
		}
		
		// find leaf to insert key
		TreeNode curr = root;
		TreeNode prev = null;
		
		while (curr != null) {
			prev = curr;
			if (curr.contains(key)) {
				return false;
			}
			if (key < curr.minKey) {
				curr = curr.leftChild;
			} else {
				if (curr.slots == 2 && key < curr.maxKey) {
					curr = curr.midChild;
				} else {
					curr = curr.rightChild;
				}
			}
		}
		
		if (prev.slots == 1) {
			prev.addKey(key);
		} else {
			split(prev, key);
		}
		return true;
		
	}
	
	private void split(TreeNode curr, Integer key) {
		// split TreeNode 
		int midKey = curr.setMidKey(key);  
		
		// make new nodes from minKey, maxKey
		TreeNode left = new TreeNode(curr.minKey);
		TreeNode right= new TreeNode(curr.maxKey);
		
		if (curr != root) {
			TreeNode p = curr.parent;
			
			curr.distributeChildren(left, right, p);
			
			// determine where split is coming from and what type node is being split
			if (p.slots == 1) {
				p.addKey(midKey);
				if (p.leftChild == curr){
					p.leftChild = left;
					p.midChild = right;
				} else {
					p.midChild = left;
					p.rightChild = right;
				}
			} else {
				if (p.leftChild == curr) {
					p.leftChild = left;
					p.leftMidChild = right;
					p.rightMidChild = p.midChild;
					p.midChild = null;
				} else if (p.midChild == curr) {
					p.leftMidChild = left;
					p.rightMidChild = right;
					p.midChild = null;
				} else {
					p.leftMidChild = p.midChild;
					p.midChild = null;
					p.rightMidChild = left;
					p.rightChild = right;
				}
				split(p, midKey);
			} 
		} else {
				TreeNode newNode = new TreeNode(midKey);
				root = newNode;
				newNode.leftChild = left;
				newNode.rightChild = right;
				curr.distributeChildren(left, right, newNode);
		}		
	}
	
	
	
	public int size() {
		if (root == null) return 0;
		return size(root.minKey);
	}
	
	public int size(int key) {
		TreeNode subRoot = search(key);
		if (subRoot == null) return 0;
		
		int size = 0;
		Stack<TreeNode> stack = new Stack<>();
		TreeNode curr = subRoot;
		
		while (curr != null || !stack.isEmpty()) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.leftChild;
			} else {
				curr = stack.pop();
				size++;
				if (curr.slots == 1) {
					curr = curr.rightChild;
				} else {
					size += 1;
					
					if (curr.rightChild != null)
						stack.push(curr.rightChild);
					
					curr = curr.midChild;
				}
			}
		}
		return size;
	}
	
	private TreeNode search(int key) {
		if (root == null) return null;
		
		return root.search(key);
//		while (curr != null) {
//			if (curr.contains(key)) {
//				return curr;
//			} else {
//				if (key < curr.minKey) {
//					curr = curr.leftChild;
//				} else {
//					if (curr.slots == 2 && key < curr.maxKey && curr.midChild != null) {
//						curr = curr.midChild;
//					} else {
//						curr = curr.rightChild;
//					}
//				}
//			}
	}
	
	public int get(int idx) {
		Stack<TreeNode> stack = new Stack<>();
		TreeNode curr = root;
		int currKey = 0;
		
		while (curr != null || !stack.isEmpty()) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.leftChild;
			} else {
				curr = stack.pop();
				currKey = curr.minKey;
				
				if (idx-- == 0) {
					break;
				}
				
				if (curr.slots == 1) {
					curr = curr.rightChild;
				} else {
					
					if (curr.rightChild != null)
						stack.push(curr.rightChild);
					
					stack.push(new TreeNode(curr.maxKey));
					
					curr = curr.midChild;
				}
			}
		}
		return currKey;
	}
}
