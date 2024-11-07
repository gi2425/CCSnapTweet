public class AVL{
	public AVLNode root;
	public int size;
	public boolean alternator = true;
	public Post[] inOrdList = new Post[100];
	private int index = 0;

	public AVL(){}

	public AVLNode find(int k, AVLNode v){
		if(v == null){
			return null;
		}
		else if(k == v.time){
			return v;
		}
		else if(k < v.time){
			return find(k, v.left);
		}
		else{
			return find(k, v.right);
		}
	}

	private void recInsert(Post t, AVLNode v){
		Long k = t.time;
		if(k > v.time){
			if(v.right != null){
				recInsert(t,v.right);
			}
			else{
				v.right = new AVLNode(t,null,null,v);
			}
		}
		else if(k <= v.time){
			if(v.left != null){
				recInsert(t, v.left);
			}
			else{
				v.left = new AVLNode(t,null,null,v);
			}
		}
	}

	public void insert(Post k){
		if (root==null){
			root = new AVLNode(k, null,null,null);
		}
		else{
			recInsert(k,root);
		}
	}

	public void inOrdTrav(AVLNode v){
		if(v.left != null){
			inOrdTrav(v.left);
		}
		System.out.println(v.time + ": "+ v.key.text + ", Likes: " + v.key.likes);
		//System.out.print(" ");
		if(v.right != null){
			inOrdTrav(v.right);
		}
	}

	public void preOrdTrav(AVLNode v){
		System.out.print(v.time + ": " + v.key.text + ", ");
		System.out.print(" ");
		if(v.left != null){
			preOrdTrav(v.left);
		}
		if(v.right != null){
			preOrdTrav(v.right);
		}
	}

	public void MRP(AVLNode v){
		while(v.right!=null){
			v=v.right;
		}
		System.out.println(v.time + ": "+ v.key.text + ", Likes: " + v.key.likes);
	}

	public void inOrdWrite(AVLNode v){
		if(v.left != null){
			inOrdWrite(v.left);
		}
		inOrdList[index] = v.key;
		index++;
		if(v.right != null){
			inOrdWrite(v.right);
		}
	}

}


class AVLTest{
	public static void main(String[] args) {
		/*
		Post p1 = new Post(1, "Post 1");
		Post p2 = new Post(2, "Post 2");
		Post p3 = new Post(3, "Post 3");
		AVL test = new AVL();
		test.insert(p1);
		test.insert(p2);
		test.insert(p3);
		//AVLNode h = test.find(15, test.root);
		test.preOrdTrav(test.root);
		*/
	}
}











