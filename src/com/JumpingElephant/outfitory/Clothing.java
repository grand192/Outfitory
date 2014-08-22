package com.JumpingElephant.outfitory;
 
public class Clothing {
 
    private int id;
    private String name;
    private String type;
    private String color;
    private String season;
    private String tags;
    private byte[] picture;

	public Clothing(){}
 
    public Clothing(String name, String type, String color, String season, String tags, byte[] pic) {
        super();
        this.name = name;
        this.type = type;
        this.color = color;
        this.season = season;
        this.tags = tags;
        this.picture = pic;
        
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Clothing [id=" + id 
        		+ ", name=" + name 
        		+ ", type=" + type
        		+ ", color=" + color
        		+ ", season=" + season
        		+ ", tags=" + tags
        		+ ", picture=" + picture
                + "]";
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public void setName(String nameString) {
		this.name=nameString;
	}

	public void setColor(String colorString) {
		this.color=colorString;
	}
	public void setId(int idInt) {
		this.id = idInt;
	}
	
    public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
}
