public class HashNode{
	public User person;
	public HashNode next;

	public HashNode(User e, HashNode n){
		person = e;
		next = n;
	}
}