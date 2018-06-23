package ua.khpi.krasov.db.entity;

public class Tariff extends Entity{

	private static final long serialVersionUID = 4623489646797735011L;
	
	private String name;
	
	private int price;
	
	private String description;
	
	private String nameRu;
	
	private String descriptionRu;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getDescriptionRu() {
		return descriptionRu;
	}

	public void setDescriptionRu(String descriptionRu) {
		this.descriptionRu = descriptionRu;
	}

	@Override
	public String toString() {
		return "Tariff [name=" + name + ", price=" + price + ", description=" + description + "]";
	}
}
