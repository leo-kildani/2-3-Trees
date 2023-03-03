import java.awt.TexturePaint;
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
			
			distributeChildren(curr, left, right, p);
			
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
				distributeChildren(curr, left, right, newNode);
		}		
	}
	
	private void distributeChildren(TreeNode nodeToSplit, TreeNode left, TreeNode right, TreeNode p) {
		left.parent = p;
		right.parent = p;
		
		if (nodeToSplit.leftChild != null) {
			left.leftChild = nodeToSplit.leftChild;
			nodeToSplit.leftChild.parent = left;
		}
		if (nodeToSplit.leftMidChild != null) {
			left.rightChild = nodeToSplit.leftMidChild;
			nodeToSplit.leftMidChild.parent = left;
		}
		if (nodeToSplit.rightMidChild != null) {
			right.leftChild = nodeToSplit.rightMidChild;
			nodeToSplit.rightMidChild.parent = right;
		}
		if (nodeToSplit.rightChild != null) {
			right.rightChild = nodeToSplit.rightChild;
			nodeToSplit.rightChild.parent = right;
		}
	}
	
	public int size() {
		if (root == null) return 0;
		return size(root.minKey);
	}
	
	public int size(int key) {
		int size = 0;
		TreeNode subRoot = search(key);
		if (subRoot == null) return size;
		
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
					if (curr.rightChild != null) {
						stack.push(curr.rightChild);
					}
					curr = curr.midChild;
				}
			}
		}
		return size;
	}
	
	private TreeNode search(int key) {
		TreeNode curr = root;
		while (curr != null) {
			if (curr.contains(key)) {
				return curr;
			} else {
				if (key < curr.minKey) {
					curr = curr.leftChild;
				} else {
					if (curr.slots == 2 && key < curr.maxKey && curr.midChild != null) {
						curr = curr.midChild;
					} else {
						curr = curr.rightChild;
					}
				}
			}
		}
		return null;
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
					if (curr.rightChild != null) {
						stack.push(curr.rightChild);
					}
					
					stack.push(new TreeNode(curr.maxKey));
					
					curr = curr.midChild;
				}
			}
		}
		return currKey;
	}
}
