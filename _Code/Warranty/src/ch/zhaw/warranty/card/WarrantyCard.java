package ch.zhaw.warranty.card;

public class WarrantyCard {
	private int _id;
	private String title;
	private String description;
	private String imagePath;
	private String createdAt;
	private String validUntil;
	private String price;
	private String reseller;

	public WarrantyCard(int id,String title, String description,String imagePath,
			String createdAt, String validUntil, String price, String reseller) {
		this._id = id;
		this.title = title;
		this.description = description;
		this.imagePath = imagePath;
		this.createdAt = createdAt;
		this.validUntil = validUntil;
		this.price = price;
		this.reseller = reseller;		
	}

	/**
	 * returns the ID
	 * 
	 * @return	ID of the current card
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * returns the title
	 * 
	 * @return	Title of the current card
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * returns the description
	 * 
	 * @return	Description of the current card
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * returns the path to the image file
	 * 
	 * @return	Path to the image of the current card
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * returns the creation date
	 * 
	 * @return	creation date of the current card
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * returns the expiration date
	 * 
	 * @return	expiration date of the current card
	 */
	public String getValidUntil() {
		return validUntil;
	}

	/**
	 * returns the price
	 * 
	 * @return	price of the current card
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * returns the reseller
	 * 
	 * @return	reseller  of the current card
	 */
	public String getReseller() {
		return reseller;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return title + " [" + description + "] - " + validUntil;
	}
}
