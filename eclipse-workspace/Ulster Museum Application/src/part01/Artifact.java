package part01;

public class Artifact {

	private String id;
	private String name;
	private String type;
	private int engagementMinutes;
	private String description;

	public Artifact(String id, String type, String name, String description, int engagementMinutes) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
		this.engagementMinutes = engagementMinutes;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getEngagementMinutes() {
		return engagementMinutes;
	}

	public String getDescription() {
		return description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEngagementMinutes(int engagementMinutes) {
		this.engagementMinutes = engagementMinutes;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
