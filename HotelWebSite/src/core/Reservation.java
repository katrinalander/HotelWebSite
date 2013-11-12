package core;

public class Reservation {
	public int reservId;
	public String email;
	public String checkIn;
	public String checkOut;
	public double price;
	public int sngl;
	public int dbl;
	public int twin;
	public int trpl;
	public int king;
	public int hmn;
	public String type;
	
	public Reservation(int reservId, String email, String checkIn,
			String checkOut) {
		super();
		this.reservId = reservId;
		this.email = email;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}
	public String getType() {
		return type;
	}
	public void setType(String string) {
		this.type = string;
	}
	public int getReservId() {
		return reservId;
	}
	public void setReservId(int reservId) {
		this.reservId = reservId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getSngl() {
		return sngl;
	}
	public void setSngl(int sngl) {
		this.sngl = sngl;
	}
	public int getDbl() {
		return dbl;
	}
	public void setDbl(int dbl) {
		this.dbl = dbl;
	}
	public int getTwin() {
		return twin;
	}
	public void setTwin(int twin) {
		this.twin = twin;
	}
	public int getTrpl() {
		return trpl;
	}
	public void setTrpl(int trpl) {
		this.trpl = trpl;
	}
	public int getKing() {
		return king;
	}
	public void setKing(int king) {
		this.king = king;
	}
	public int getHmn() {
		return hmn;
	}
	public void setHmn(int hmn) {
		this.hmn = hmn;
	}
	public Reservation(int reservId, String email, String checkIn,
			String checkOut, double price, int sngl, int dbl, int twin,
			int trpl, int king, int hmn) {
		super();
		this.reservId = reservId;
		this.email = email;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.price = price;
		this.sngl = sngl;
		this.dbl = dbl;
		this.twin = twin;
		this.trpl = trpl;
		this.king = king;
		this.hmn = hmn;
	}
	public Reservation() {
		super();
	}
	@Override
	public String toString() {
		return "Reservation [reservId=" + reservId + ", email=" + email
				+ ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", price=" + price + ", sngl=" + sngl + ", dbl=" + dbl
				+ ", twin=" + twin + ", trpl=" + trpl + ", king=" + king
				+ ", hmn=" + hmn + ", type=" + type + "]";
	}
}
