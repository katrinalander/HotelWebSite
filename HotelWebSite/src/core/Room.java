package core;

public class Room {
	public int Id;
	public String type;
	public String type2;
	public String description;
	public double area;
	public double price_regular;
	public double price_deal;
	public String imageUrl;
	public int amount;
	public int num;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getPrice_regular() {
		return price_regular;
	}
	public void setPrice_regular(double price_regular) {
		this.price_regular = price_regular;
	}
	public double getPrice_deal() {
		return price_deal;
	}
	public void setPrice_deal(double price_deal) {
		this.price_deal = price_deal;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Room(int id, String type, String description, double area,
			double price_regular, double price_deal, String imageUrl, int amount) {
//		super();
		Id = id;
		this.type = type;
		this.description = description;
		this.area = area;
		this.price_regular = price_regular;
		this.price_deal = price_deal;
		this.imageUrl = imageUrl;
		this.amount = amount;
	}
	public Room() {
//		super();
	}
	@Override
	public String toString() {
		return "Room [Id=" + Id + ", type=" + type +", type2=" + type2 + ", description="
				+ description + ", area=" + area + ", price_regular="
				+ price_regular + ", price_deal=" + price_deal + ", imageUrl="
				+ imageUrl + ", amount=" + amount + "]";
	}
	
	
}
