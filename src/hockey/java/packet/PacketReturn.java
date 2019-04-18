package hockey.java.packet;

public class PacketReturn {
	public int status;
	
	/*
	  1 = sign up success
	  2 = sign up failure
	  3 = login success
	  4 = login failure
	  5 = signout
	  7 = play (logged or guest)
	  8 = stats
	  
	  */
	
	public int id;
	public String username;
	public String message;
	public PacketReturn() {} // for KryoNet; otherwise cannot be deserialized
	public PacketReturn(int status) {
		this.status = status;
	}
	public PacketReturn(int status, int id, String username) {
		this.status = status;
		this.id = id;
		this.username = username;
	}
	public PacketReturn(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	
}
