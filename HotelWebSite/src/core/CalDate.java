package core;

import java.sql.Date;

public class CalDate {
	public String cal_date;
	public int sngl;
	public int dbl;
	public int twin;
	public int trpl;
	public int king;
	public int hmn;
	public String getCal_date() {
		return cal_date;
	}
	public void setCal_date(String cal_date) {
		this.cal_date = cal_date;
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
	public CalDate(String cal_date, int sngl, int dbl, int twin, int trpl,
			int king, int hmn) {
		super();
		this.cal_date = cal_date;
		this.sngl = sngl;
		this.dbl = dbl;
		this.twin = twin;
		this.trpl = trpl;
		this.king = king;
		this.hmn = hmn;
	}
	
	public CalDate(String cal_date, int sngl, int dbl, int trpl) {
		super();
		this.cal_date = cal_date;
		this.sngl = sngl;
		this.dbl = dbl;
		this.trpl = trpl;
	}
	public CalDate() {
		super();
	}
	@Override
	public String toString() {
		return "Calendar [cal_date=" + cal_date + ", sngl=" + sngl + ", dbl="
				+ dbl + ", twin=" + twin + ", trpl=" + trpl + ", king=" + king
				+ ", hmn=" + hmn + "]";
	}
}
