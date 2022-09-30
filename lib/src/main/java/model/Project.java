package model;

import java.util.Date;

public class Project { //ESSA CLASSE REPRESENTA O PROJETO DO APP

	private int id;
	private String name;
	private String description;
	private Date createAt;
	private Date updateat;
	
	private Project(int id, String name, String description, Date createAt, Date updateat) { //PARAMETROS
		super();                            //ATRIBUTOS
		this.id = id;
		this.name = name;
		this.description = description;
		this.createAt = createAt;
		this.updateat = updateat;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateat() {
		return updateat;
	}

	public void setUpdateat(Date updateat) {
		this.updateat = updateat;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", createAt=" + createAt
				+ ", updateat=" + updateat + "]";
	}
	
	
}
