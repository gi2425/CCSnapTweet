public class User{

	public String displayName;
	public String username;
	public String password;
	public AVL posts;
	public String[] followers;
	public String[] following;

	public User(/*String u, String i, String p*/){
		posts = new AVL();
		/*username = u;
		id = i;
		password = p;*/
	}

	public String toString(){
		return displayName + ", " + username + ", " + password;
	}

}