package core;

public class Feedback {

	public int id;
	public String email;
	public String name;
	public int raiting;
	public String message;
	public String date;
	public boolean status;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRaiting() {
		return raiting;
	}
	public void setRaiting(int raiting) {
		this.raiting = raiting;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Feedback(int id, String email, String name, int raiting,
			String message, String date) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.raiting = raiting;
		this.message = message;
		this.date = date;
	}
	public Feedback() {
		super();
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", email=" + email + ", name=" + name
				+ ", raiting=" + raiting + ", message=" + message + ", date="
				+ date + "]";
	}
	
}
