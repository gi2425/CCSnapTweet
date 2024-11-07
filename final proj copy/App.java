import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class App{
	Post[] c = new Post[500];
	public CUP cup = new CUP(c);
	public User user;
	public HashTable hash = new HashTable();

	public App(/*User u*/){
		/*user = u;*/
	}

	public Boolean login(String u, String p){
		//also call cup.purge
		User pp = hash.finduser(u);
		if(pp==null){
			System.out.println("user does not exist");
			return false;
		}
		else{
			if(pp.password.equals(p)){
				user=pp;
				System.out.println("Successful login!");
				return true;
			}
			else{
				System.out.println("wrong password");
				return false;
			}
		}

	}

	public void createUser(String dn, String un, String pw){
		User u = new User();
		u.displayName = dn;
		u.username = un;
		u.password = pw;
		u.posts=new AVL();
		String[] temp = new String[]{"tshriber"};
		u.following = temp;
		u.followers = temp;

		User us = hash.finduser("tshriber");
		//add user to us following
		String[] newfollow = new String[us.followers.length+1];
		int index = 0;
		for(String i: us.followers){
			newfollow[index] = i;
			index++;
		}
		newfollow[index]=un;
		//replace following list in user
		us.followers = newfollow;

		//add user to us followers
		String[] newfollower = new String[us.following.length+1];
		index = 0;
		for(String j: us.following){
			newfollower[index] = j;
			index++;
		}
		newfollower[index]=un;
		//replace follower list in us
		us.following = newfollower;

		long time = System.currentTimeMillis() / 1000;
		String s = "User " + dn + " created their account!";
		Post p = new Post(time, s, 0);
		//user avl
		u.posts.insert(p);
		//heap
		cup.insert(p);

		hash.insert(u);
		//login(un, pw);
	}

	public void loginDP(){
		System.out.println("");
		System.out.println("Your most recent post:");
		user.posts.MRP(user.posts.root);
		System.out.println("");
		Post p = cup.getMax();
		System.out.println("Post with most likes:");
		System.out.println(p.time + ": "+ p.text + ", Likes: " + p.likes);
		System.out.println("");
		User pp;
		System.out.println("Posts of people you follow:");
		for(String u: user.following){
			pp = hash.finduser(u);
			if(pp==null){break;}
			pp.posts.MRP(pp.posts.root);
		}
		System.out.println("");
	}

	public void read() {		//read file
		try {
			FileReader fileIn = new FileReader("userInfo");
			BufferedReader bufReader = new BufferedReader(fileIn);

			int pc = 0;
			
			String line = bufReader.readLine();
			int count = 1;
			int ucount = 0;
			User u = new User();

			while (line != null) {
				if (line.equals("")){			//create new user	
					//put old user into hashtable
					hash.insert(u);

					u = new User();
					ucount++;
					count=0;
				}

				if (count==1){					//displayName
					u.displayName = line;
				}

				else if(count==2){				//username
					u.username = line;
				}

				else if(count==3){				//password
					u.password = line;
				}

				else if(count==4){				//posts
					String[] p = line.split(";");
					for (String i:p){
						String temp = i.replace("(","").replace(")","").replace("\"","");
						String[] tlist = temp.split(",");
						
						//System.out.println(tlist[0]);
						//System.out.println(tlist[1]);
						//System.out.println(tlist[2]);
						//System.out.println("");
				
						Post po = new Post(Long.parseLong(tlist[0]), 
							tlist[1], Integer.parseInt(tlist[2]));
						//user avl
						u.posts.insert(po);
						//heap
						cup.insert(po);

					}
				}

				else if(count==5){				//followers
					String[] temp = line.split(",");
					/*
					System.out.println("start test");
					for(String i: temp){System.out.println(i);}
					System.out.println("end test");
					//gets rid of the one null at the end
					String[] fers = new String[temp.length-1];
					int index = 0;
					for(String s:temp){
						if(s==null){break;}
						else{
							fers[index]=s;
							index++;
						}
					}*/

					u.followers = temp;
				}

				else if(count==6){				//following
					String[] temp = line.split(",");
					u.following = temp;
				}

				line=bufReader.readLine();
				count++;
			}

			bufReader.close();
			fileIn.close();
			//System.out.println(cup.getMax().text);

		}  catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayPosts(String u){ //input the string of displayName
		//needs the displayName of the posts that you want to display
		User us = hash.finduser(u);
		if(us==null){
			System.out.println("user does not exist");
		}
		else{
			us.posts.inOrdTrav(us.posts.root);
		}
	}

	public void createPost(String s){
		long time = System.currentTimeMillis() / 1000;
		Post p = new Post(time, s, 0);
		//user avl
		user.posts.insert(p);
		//heap
		cup.insert(p);
	}

	public void viewFollowers(){
		int count = 1;
		for(String u: user.followers){
			System.out.println(count + ". " + u);
			//System.out.
			count++;
		}
	}		

	public void viewFollowing(){
		int count = 1;
		for(String u: user.following){
			System.out.println(count + ". " + u);
			//System.out.
			count++;
		}
	}

	public Boolean follow(String s){
		User us = hash.finduser(s);
		//check if it is already a follower
		boolean contains = Arrays.stream(user.following).anyMatch(s::equals);
		//if user is self
		if(user==us){
			System.out.println("you can not follow yourself");
			return false;
		}
		//if user exists
		else if(us==null){
			System.out.println("user does not exist");
			return false;
		}
		//if they already follow that user
		else if(contains==true){
			System.out.println("you already follow this person.");
			return false;
		}
		else{
			//add us to user following
			String[] newfollow = new String[user.following.length+1];
			int index = 0;
			for(String i: user.following){
				newfollow[index] = i;
				index++;
			}
			newfollow[index]=s;
			//replace following list in user
			user.following = newfollow;

			//add user to us followers
			String[] newfollower = new String[us.followers.length+1];
			index = 0;
			for(String j: us.followers){
				newfollower[index] = j;
				index++;
			}
			newfollower[index]=s;
			//replace follower list in us
			us.following = newfollower;
			System.out.println("Succesfully followed " + s);
			return true;
		}
	}

	public void like(int t){
		//call .heapifyUp from the CUP
		return;
	}

	public void logout(){
		//just clears the user in the system
		user = null;
	}

	public void write(){
		User[] users = hash.toWrite();
		
		try {
			FileWriter fileOut = new FileWriter("userinfo");
			BufferedWriter bufWriter = new BufferedWriter(fileOut);
			
			for(User u: users){
				if(u==null){break;}
				else{
					bufWriter.write(u.displayName);
					bufWriter.write("\n");
					bufWriter.write(u.username);
					bufWriter.write("\n");
					bufWriter.write(u.password);
					bufWriter.write("\n");
					u.posts.inOrdWrite(u.posts.root);
					for(Post p: u.posts.inOrdList){
						if(p==null){break;}
						else{
							bufWriter.write("("+p.time+",\""+p.text+"\","+p.likes+");");
						}
					}
					bufWriter.write("\n");
					for(String s: u.followers){
						bufWriter.write(s+",");
					}
					bufWriter.write("\n");
					for(String k: u.following){
						bufWriter.write(k+",");
					}
					bufWriter.write("\n");
					bufWriter.write("\n");
				}

			}
	

			//bufWriter.write("This is line " + i + "\n");
			
			bufWriter.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//##########################################################################################

class appTest{
	public static void main(String[] args) {
		App test = new App();
		test.read();
		
		test.login("esmith25", "Peepee23"); //not working without hashtables
		//test.viewFollowing();
		System.out.println("");
		//test.follow("kbergero");
		test.follow("kbergeron");
		test.viewFollowing();
		//test.follow("kbergeron");
		System.out.println("");
		System.out.println("");
		test.loginDP();
		System.out.println("");
		System.out.println("");
		
		//test.createUser("Deez Nuts", "dnuts", "donuts1234");
		test.write();
		System.out.println("");
		System.out.println("");

		//test.viewFollowing();
		//test.displayPosts("kbergeron");
		//long time = System.currentTimeMillis() / 1000;
		//System.out.println(time);

		//test.viewFollowers();
		//System.out.println(test.tempList[0].displayName);
		//System.out.println(test.tempList[1].displayName);
	}
}

//##########################################################################################

//Todd, Gi and John
/**
 * Main class where the program can be interfaced with
 */
class main
{
    /**
     * Method that is ran to interface with the program
     */
    
    //note: if you guys are having trouble acessing methods from other classes, remove the static tag from main and homelist, but I think it should work just fine with the static tag
    public static void main(String[] args) throws InterruptedException
    {
    	//creating the app object
    	App app = new App();
    	//reading in the files
    	app.read();

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("  ___                       _        _               ");
        System.out.println(" / (_)                     | |      | |             |");
        System.out.println("|      __,   _  _  _    _  | |  __  | |     __, _|_ |");
        System.out.println("|     /  |  / |/ |/ |  |/  |/  /    |/ |   /  |  |  |");
        System.out.println(" (___/[_/|_/  |  |  |_/|__/|__/[___/|   |_/[_/|_/|_/o");
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------");    
        
        System.out.println("");
        System.out.println("Welcome to Camelchat! One of the only social media programs built for members of conn like us!");
        System.out.println("");
        
        //instance variables 
        boolean mainloop = true;
        Scanner in = new Scanner(System.in); //scanner used for user response
        
        //main loop
        while (mainloop == true) //is going to loop until program is told to stop
        {
            System.out.println("--------------------------------------------------------------------------------------------"); 
            System.out.println("");            
            System.out.println("____ ___  ___ _ ____ _  _ ____");
            System.out.println("|  | |__]  |  | |  | |[ | [__ "); 
            System.out.println("|__| |     |  | |__| | [| ___]"); 
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------");   
            System.out.println("");
            System.out.println("Option A -> login (if you are an existing user)");
            System.out.println("Option B -> create a new account");
            System.out.println("Option C -> exit program"); 
            System.out.println("");            
            System.out.println("--------------------------------------------------------------------------------------------");   
            System.out.println("");            
            System.out.print("Please input the letter corresponding to your choice: ");
            String answer = in.nextLine();
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------------");  
            System.out.println("");         
            if (answer.equalsIgnoreCase("a"))
            {
            	System.out.print("Please enter your username: ");
            	String uname = in.nextLine();
            	System.out.print("Please enter your passowrd: ");
            	String pword = in.nextLine();
            	System.out.println("");

            	Boolean t = app.login(uname,pword);
            	TimeUnit.SECONDS.sleep(1);
            	if(t==true){
            		homescreen(app);
            	}


              /**
               * If they are an existing user (which is accessed through records), after logging in, they see their “home screen,” 
               * which displays the following info in a nice, clear, readable fashion:  
                
               * Their own most recent post
               * The current post with the most likes among all users of the app
               * The most recent post of each of the user’s they follow
               */
            }
            else if (answer.equalsIgnoreCase("b"))
            {
            	System.out.print("Please enter your display name: ");
            	String dn = in.nextLine();
            	System.out.print("Please enter your username: ");
            	String usern = in.nextLine();
            	System.out.print("Please enter your passowrd: ");
            	String passw = in.nextLine();
            	System.out.println("");

            	app.createUser(dn,usern,passw);
            	System.out.println("Succesfully created new user!");
            	TimeUnit.SECONDS.sleep(1);
            	System.out.println("Returning to login screen.");
            	TimeUnit.SECONDS.sleep(1);

                /**
                 * If the user chooses to create a new account, 
                 * they will be prompted for their info and a new user record will be created for them.
                 * 
                 * This means -> adding a new user to records class
                 */
              
            }
            else if (answer.equalsIgnoreCase("c"))
            {
            	app.write();
                System.out.println("Thank you for using our program! :)" );
                System.out.println("");            
                System.out.println("--------------------------------------------------------------------------------------------");  
                mainloop = false;
            }
            else
            {
                System.out.println("Input a, b, or c");
            }
        }

    }
    
    /**
     * Method that displays user information and allows users to interact with it
     */
    private static void homescreen (App app) throws InterruptedException
    {
        /**
         * Find user in records: Display ->
         * 
         * Their own most recent post -> access post from recent posts in records
         * 
         * The current post with the most likes among all users of the app -> access post from clp 
         * 
         * The most recent post of each of the user’s they follow
         */
        //basically just refrence the breakdown document I made to do this part
        //we can basically fit everything under "They are then prompted with the option of:" in this method
        
        //instance variables 
        boolean homeloop = true;
        Scanner in = new Scanner(System.in); //scanner used for user interaction
                System.out.println("--------------------------------------------------------------------------------------------");
                System.out.println(" ");            
                System.out.println("  _   _                                                                      "); 
                System.out.println(" | | | |   ___    _ __ ___     ___   ___    ___   _ __    ___    ___   _ __  ");                                                         
                System.out.println(" | |_| |  / _ ]  | '_ ` _ ]   / _ ] / __|  / __| | '__|  / _ ]  / _ ] | '_ ] ");  
                System.out.println(" |  _  | | (_) | | | | | | | |  __/ [__ / | (__  | |    |  __/ |  __/ | | | |");
                System.out.println(" |_| |_|  [___/  |_| |_| |_|  [___| |___/  [___| |_|     [___|  [___| |_| |_|");
                System.out.println(" ");                                                                                                                                    
                System.out.println("--------------------------------------------------------------------------------------------");

        while (homeloop == true) //is going to loop until user wishes to log out
            {
                System.out.println("");
                app.loginDP();
                TimeUnit.SECONDS.sleep(3);
                System.out.println("--------------------------------------------------------------------------------------------"); 
                System.out.println("");            
                System.out.println("____ ___  ___ _ ____ _  _ ____");
                System.out.println("|  | |__]  |  | |  | |[ | [__ "); 
                System.out.println("|__| |     |  | |__| | [| ___]"); 
                System.out.println("");
                System.out.println("--------------------------------------------------------------------------------------------");      
                System.out.println("Option A -> Give a post a 'like' ");
                System.out.println("Option B -> Make a new post");
                System.out.println("Option C -> View list of people who follow you"); 
                System.out.println("Option D -> View list of people you follow");
                System.out.println("Option E -> Follow someone new");
                System.out.println("Option F -> Log out");
                System.out.println("");            
                System.out.println("--------------------------------------------------------------------------------------------");   
                System.out.println("");            
                System.out.print("Please input the letter corresponding to your choice: ");
                String answer = in.nextLine();
                System.out.println("");
                System.out.println("--------------------------------------------------------------------------------------------");  
                System.out.println("");
            if (answer.equalsIgnoreCase("a")){
            									//like a post goes here
            	//give a post to like
            }

            else if (answer.equalsIgnoreCase("b")){
            	System.out.print("Enter your post here: ");
            	String p = in.nextLine();
            	app.createPost(p);
            	System.out.println("Succesfully created post!");
            }
            
            else if (answer.equalsIgnoreCase("c")){
            	//view list of all followers
            	app.viewFollowers();
            	TimeUnit.SECONDS.sleep(3);
            }
            
            else if (answer.equalsIgnoreCase("d")){
            	//view list of people you follow
            	app.viewFollowing();
            	TimeUnit.SECONDS.sleep(3);
            	System.out.println("Would you like to view all posts of a user?");
            	System.out.print("Select Y for yes: ");
            	String a = in.nextLine();
            	if(a.equalsIgnoreCase("y")){
            		System.out.println("");
            		System.out.print("Enter the name of the user: ");
            		String r = in.nextLine();
            		app.displayPosts(r);
            		TimeUnit.SECONDS.sleep(3);
            		System.out.println("");
            		System.out.println("Like to like or reply to a post?");
            		System.out.print("type \"reply\" or \"like\": ");
            		String reply = in.nextLine();
            		if(reply.equalsIgnoreCase("reply")){
            			System.out.println("");
            			System.out.print("Enter your post here: ");
            			String p1 = in.nextLine();
            			String p2 = "replying to @" + r + ": " + p1;
            			app.createPost(p2);
            			System.out.println("Succesfully created post!");
            			TimeUnit.SECONDS.sleep(1);
            		}
            		else if(reply.equalsIgnoreCase("like")){
            			//call like() here

            													//like a user post here

            			TimeUnit.SECONDS.sleep(1);
            		}
            	}
            }
             
            else if (answer.equalsIgnoreCase("e")){
            	//follow someone new
            	System.out.print("give the username of the person you want to follow: ");
            	String f1 = in.nextLine();
            	app.follow(f1);
            	TimeUnit.SECONDS.sleep(1);

            }
            
            else if (answer.equalsIgnoreCase("f")){
            	app.logout();
                System.out.println("Logging out...");
                System.out.println("");            
                System.out.println("--------------------------------------------------------------------------------------------");  
                homeloop = false;
            }
            else
            {
                System.out.println("Input a, b, c, d, e or f");
            }
            }
        }
}





