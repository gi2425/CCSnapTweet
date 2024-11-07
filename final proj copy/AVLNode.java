public class AVLNode{

	public Post key;
	public Long time;
	public AVLNode left;
	public AVLNode right;
	public AVLNode parent;

	public int height;

	public AVLNode(Post data, AVLNode left, AVLNode right, AVLNode parent){
		key = data;
		left = left;
		right = right;
		parent = parent;
		time = data.time;
	}
}