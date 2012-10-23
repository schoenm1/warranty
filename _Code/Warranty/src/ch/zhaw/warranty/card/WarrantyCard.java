package ch.zhaw.warranty.card;

public class WarrantyCard {
	private long _id;
	private String title;
	private String description;
	private String imagePath;
	private String createdAt;
	private String validUntil;
	private String price;
	private String reseller;

	public WarrantyCard(String title, String description,String imagePath,
			String createdAt, String validUntil, String price, String reseller) {
		this.title = title;
		this.description = description;
		this.imagePath = imagePath;
		this.createdAt = createdAt;
		this.validUntil = validUntil;
		this.price = price;
		this.reseller = reseller;		
	}

	public long get_id() {
		return _id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getValidUntil() {
		return validUntil;
	}

	public String getPrice() {
		return price;
	}

	public String getReseller() {
		return reseller;
	}
}
