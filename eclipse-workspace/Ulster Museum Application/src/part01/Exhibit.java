package part01;

import java.util.ArrayList;
import java.util.HashMap;

public class Exhibit {

	private String exhibitID;
	private String name;
	private String description;

	private HashMap<String, String> IDsAndSigns;
	private ArrayList<String> orderedArtifactIds = new ArrayList<>();

	public Exhibit(String exhibitID, String name, String description) {
		this.exhibitID = exhibitID;
		this.name = name;
		this.description = description;
		this.IDsAndSigns = new HashMap<>();
	}

	public void addArtifact(String artifactID, String signText) {
		IDsAndSigns.put(artifactID, signText);
		orderedArtifactIds.add(artifactID);
	}

	public ArrayList<String> getorderedArtifactIds(String exhibitId) {
		return orderedArtifactIds;
	}

	public HashMap<String, String> getIDsAndSigns(String exhibitId) {
		return IDsAndSigns;
	}

	public ArrayList<String> getArtifactIDs() {
		return new ArrayList<>(orderedArtifactIds);
	}

	public String getSignForArtifact(String artifactId) {
		return IDsAndSigns.get(artifactId);
	}

	public String getExhibitID() {
		return exhibitID;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return exhibitID;
	}

	public void replaceArtifactMap(HashMap<String, String> newMap) {
		this.IDsAndSigns = newMap;
	}

	public void setArtifactOrder(ArrayList<String> newOrder) {
		this.orderedArtifactIds = new ArrayList<>();
		for (int i = 0; i < newOrder.size(); i++) {
			this.orderedArtifactIds.add(newOrder.get(i));
		}
	}

	public void setExhibitID(String exhibitID) {
		this.exhibitID = exhibitID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void removeArtifact(String artifactID) {
		IDsAndSigns.remove(artifactID);
		orderedArtifactIds.remove(artifactID);
	}

	public boolean containsArtifact(String artifactID) {
		return IDsAndSigns.containsKey(artifactID);
	}
}
