public class Post{
	
	public long time;
	public String text;
	public int likes;

	public Post(long t, String p, int l){
		time = t;
		text = p;
		likes = l;
	}

	public int likes()
	{
	    return likes;
	}

	public String toString(){
		return time + ": " + text;
	}
}