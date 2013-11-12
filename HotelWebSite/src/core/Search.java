package core;

public class Search {
	public String checkIn;
	public String checkOut;
	public int sngl;
	public int dbl;
	public int trpl;
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
	public int getTrpl() {
		return trpl;
	}
	public void setTrpl(int trpl) {
		this.trpl = trpl;
	}
	public Search(String checkIn, String checkOut, int sngl, int dbl, int trpl) {
		super();
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.sngl = sngl;
		this.dbl = dbl;
		this.trpl = trpl;
	}
	public Search() {
		super();
	}
	@Override
	public String toString() {
		return "Search [checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", sngl=" + sngl + ", dbl=" + dbl + ", trpl=" + trpl + "]";
	}
}
