package core;

public class User {
	public String user_name;
	public String email;
	public String address;
	public String phone;
	public String password;

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String user_name, String email, String address,
			String phone, String password) {
		super();
		this.user_name = user_name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.password = password;
	}
	public User() {
		super();
	}
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [user_name=" + user_name
				+ ", email=" + email + ", address=" + address + ", phone="
				+ phone + ", password=" + password + "]";
	}
}
