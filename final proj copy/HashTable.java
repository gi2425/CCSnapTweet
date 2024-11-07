//Todd, Gi, and John

/**
 * HashTable designed to receive a User object, or string of characters, and use a horner's hash
 * function to convert the username into a series of numbers, that can be retrieved and used 
 * at will.
 */


public class HashTable{

	//instance variables

	public int size; //capacity of the hashtable
	public User[] table; //list of user objects called table

	//Constructor method

	public HashTable(){
		size = 10000;
		table = new User[size];

	}

	/**
	 * Returns the value outputted from the horner's hash function
	 * used within all other methods to determine the user being located.
	 * @param e
	 * 		User object
	 */

	public int index(User e){
		char[] username = e.username.toCharArray(); //converts user to character array
		int hashval;
		int a = 10000;
		int b = 26;
	    int x = 0;
	    int y = 1;
	    for (int i = 0; i < username.length; i++){ //horner hash function:
	        x = (int) ((x + (username[i] + username[username.length-1] - 1) * y) % a);
	      	y = (y * b) % a;
	    }
	    hashval = x;
	    //System.out.println(hashval);
	    return hashval;
	}

	/**
	 * inserts value into the hashtable given the assigned hashvalue
	 * @param e
	 * 		the user being inserted
	 */

	public void insert(User e){
		int hashval = index(e); //gets position based on hash function
		while(table[hashval] != null){
			hashval++; //moves the user to the next available position in the table if the spot is already taken
			if(hashval == 10001){
				hashval=0; //if, when searching, it reaches max capacity, it restarts it's search for an open spot at position 0
			}
		}
		table[hashval]=e;
	}

	/**
	 * returns the value received from the hash function
	 * AKA the position of the user within the hashtable
	 * @param e
	 * 		the user being searched for
	 */

	public void findpos(User e){
		int hashval = index(e);
		System.out.println(hashval);
	}

	/**
	 *finds the determined user given their username rather than the user object itself
	 * used to search for users to follow
	 * @param e
	 * 		the string (username) inputted by the user
	 */

	public User finduser(String e){
		char[] username = e.toCharArray(); //instead of changing a user object to a char array, change string to a char array
		int hashval;
		int a = 10000;
		int b = 26;
	    int x = 0;
	    int y = 1;
	    for (int i = 0; i < username.length; i++){
	        x = (int) ((x + (username[i] + username[username.length-1] - 1) * y) % a);
	      	y = (y * b) % a;
	    }
	    hashval = x;
	    return table[hashval]; //returns user info at position generated from hash function
	}

	/**
	 * method used to test functionality
	 * 
	 */

	public void display(){
		int i;
		for(i=0;i<size;i++){
			if(table[i] == null){
				//System.out.println(i + " null");
			}else{
				System.out.println(i + " " + table[i].toString());
			}
		}
	}

	public User[] toWrite(){
		User[] fin = new User[1000];
		int index = 0;
		int i;
		for(i=0;i<size;i++){
			if(table[i]!=null){
				fin[index] = table[i];
				index++;
			}
		}
		return fin;
	}

	/*public static void main(String[] args) {
		HashTable a = new HashTable();
		User g = new User("gcortgrwef","gcortgras","hi");
		User t = new User("tuiywruiy","a","f");
		a.insert(g);
		a.insert(t);
		a.display();
		a.findpos(g);
		System.out.println(a.finduser("gcortgras"));
	}*/
}