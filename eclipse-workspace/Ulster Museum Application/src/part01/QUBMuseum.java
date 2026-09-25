package part01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import api.QUBMuseumAPI;

public class QUBMuseum implements QUBMuseumAPI {

	public static void main(String[] args) {

		// THIS IS FOR RUNNING TESTS ONLY, NOT MY COMPLETE SYSTEM
		// my system should be run from the menu file (InteractiveQUBMuseum.java)
		// This is for running tests only

		// Create API which manages all business logic
		QUBMuseum api = new QUBMuseum();
		// Create UI instance
		Scanner input = new Scanner(System.in);
		// Main loop
		boolean running = true;
		while (running) {
			// Display current prompt
			System.out.println(api.getCurrentPrompt());
			// Get user input
			String userInput = input.nextLine().trim();
			// Process input through state machine
			String result = api.processInput(userInput);
			// Check for exit condition
			if ("EXIT".equals(result)) {
				running = false;
			} else if (!result.isEmpty()) {
				System.out.println(result);
			}
		}
	}

	private static final String[] VALID_TYPES = { "PAINTING", "SCULPTURE", "POTTERY", "JEWELRY", "TEXTILE", "WEAPON",
			"TOOL", "FOSSIL", "COIN", "PHOTOGRAPH", "DOCUMENT", "OTHER" };

	// hash maps to store artifacts, exhibits and annual plans
	private HashMap<String, Artifact> artifacts = new HashMap<>();
	private HashMap<String, Exhibit> exhibits = new HashMap<>();
	private HashMap<String, AnnualPlan> annualPlans = new HashMap<>();

	// used to create new ID's
	private int nextArtifactID = 1;
	private int nextExhibitID = 1;
	private int nextPlanID = 1;

	// console UI testing
	private String currentState = "MAIN_MENU";
	private String currentPrompt = "";

	// --- Artifact Management ---

	/**
	 * Checks if an artifact type is valid
	 * 
	 * @param type: the artifact category
	 * @return boolean: true if the type is valid, false if the type is invalid
	 */
	public boolean isValidType(String type) {
		// loop through valid types
		for (String validType : VALID_TYPES) {
			// check if the type is valid against every valid type
			if (validType.equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param text:    the text to search through
	 * @param pattern: the pattern to search for within the text
	 * @return true or false depending on if the text contained the specified
	 *         pattern
	 */
	private boolean linearContains(String text, String pattern) {
		// validation
		if (pattern.length() == 0) {
			return true;
		}
		if (pattern.length() > text.length()) {
			return false;
		}
		// looping through the text to find the start of the pattern
		for (int i = 0; i <= text.length() - pattern.length(); i++) {
			boolean match = true;

			// looping through the proceeding characters to check if they also match the
			// pattern
			for (int j = 0; j < pattern.length(); j++) {
				if (text.charAt(i + j) != pattern.charAt(j)) {
					match = false;
					break;
				}
			}

			if (match) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates and stores an artifact
	 * 
	 * @param type:              the artifact category
	 * @param name:              the artifact name
	 * @param description:       description of artifact
	 * @param engagementMinutes: engagement time of the artifact
	 * @return String: the id of the new artifact or an error message
	 */
	@Override
	public String createArtifact(String type, String name, String description, int engagementMinutes) {
		// validation
		if (type == null || type.isEmpty() || name == null || name.isEmpty() || description == null
				|| description.isEmpty() || engagementMinutes <= 0) {

			System.out.println("Error - Invalid artifact datas");
			return null;
		}

		// checks if the type is valid
		if (isValidType(type) == false) {
			System.out.println("Error - Invalid type");
			return null;
		}

		// setting the artifact id
		String id = "A" + nextArtifactID;
		nextArtifactID++;

		// creating the artifact
		Artifact artifact = new Artifact(id, type, name, description, engagementMinutes);

		// putting the artifact in the artifacts hash map
		artifacts.put(id, artifact);

		return id;
	}

	/**
	 * Returns the info about the requested artifact and info type
	 * 
	 * @param artifactId: the id associated with the artifact
	 * @param infoName:   the name of the type of info
	 * @return String: the requested info about the artifact or an error message
	 */
	@Override
	public String getArtifactInfo(String artifactId, String infoName) {
		// gets artifact from artifacts hash map
		Artifact artifact = artifacts.get(artifactId);

		// validation
		if (artifact == null) {
			System.out.println("Error - Artifact not found");
			return null;
		} else if (artifacts.isEmpty()) {
			System.out.println("Error - Artifacts HashMap is empty");
			return null;
		} else if (infoName == null) {
			System.out.println("Error - invalid infoName value");
			return null;
		}

		// returns info based on info name
		switch (infoName.toLowerCase()) {
		case "id":
			return artifact.getId();
		case "type":
			return artifact.getType();
		case "name":
			return artifact.getName();
		case "description":
			return artifact.getDescription();
		case "engagement_minutes":
		case "engagement":
			return Integer.toString(artifact.getEngagementMinutes());
		default:
			return "Error - Invalid infoName value";
		}
	}

	/**
	 * Returns the engagement time in minutes of the requested artifact
	 * 
	 * @param artifactId: the id associated with the artifact
	 * @return int: the engagement time in minutes for the artifact or an error
	 *         message
	 */
	@Override
	public int getArtifactEngagementTime(String artifactId) {
		// gets artifact from artifacts hash map
		Artifact artifact = artifacts.get(artifactId);

		// validation
		if (artifact == null) {
			System.out.println("Error - Artifact not found");
			return 0;
		} else if (artifacts.isEmpty()) {
			System.out.println("Error - Artifacts HashMap is empty");
			return 0;
		}

		return artifact.getEngagementMinutes();
	}

	/**
	 * Updates the artifact info about the requested info type
	 * 
	 * @param artifactId: the id associated with the artifact
	 * @param infoName:   the name of the type of info
	 * @param newValue:   the value to update the artifact info with
	 */
	@Override
	public void updateArtifactInfo(String artifactId, String infoName, String newValue) {
		// gets artifact from artifacts hash map
		Artifact artifact = artifacts.get(artifactId);

		// validation
		if (artifact == null) {
			System.out.println("Error - Artifact not found");
			return;
		} else if (infoName == null) {
			System.out.println("Error - invalid infoName value");
			return;
		} else if (newValue == null || newValue.isEmpty()) {
			System.out.println("Error - newValue cannot be empty");
			return;
		} else {
			// sets info based on info name
			switch (infoName.toLowerCase()) {
			case "type":
				artifact.setType(newValue);
				break;
			case "name":
				artifact.setName(newValue);
				break;
			case "description":
				artifact.setDescription(newValue);
				break;
			case "engagement_minutes":
			case "engagement":
				try {

					int intNewValue = Integer.parseInt(newValue);
					if (intNewValue > 0) {
						artifact.setEngagementMinutes(intNewValue);
					} else {
						System.out.println("Error - Engagement must be positive");
					}
				} catch (Exception e) {
					System.out.println("Error - " + e.getMessage());
					break;
				}
				break;
			default:
				System.out.println("Error - Invalid infoName value");
				break;
			}
		}
	}

	/**
	 * Deletes the artifact from the artifacts hash map with the associated id and
	 * all instances of it in other exhibits and annual plans
	 * 
	 * @param artifactId: the id associated with the artifact
	 */
	@Override
	public void deleteArtifact(String artifactId) {

		// validation
		if (artifacts.isEmpty()) {
			System.out.println("Error - Artifact HashMap is empty");
			return;
		}

		// validation that the artifact exists
		Artifact artifact = artifacts.get(artifactId);
		if (artifact == null) {
			System.out.println("Error - Artifact not found");
			return;
		}

		// removing the artifact from every exhibit
		for (String exId : exhibits.keySet()) {
			Exhibit ex = exhibits.get(exId);

			if (ex.containsArtifact(artifactId)) {
				ex.removeArtifact(artifactId);
			}
		}

		// removing the artifact from the artifacts hash map
		artifacts.remove(artifactId);
		System.out.println("Artifact deleted successfully");
	}

	/**
	 * @param searchCriterea: what type of search and what to search for
	 * @param sortBy:         specifies which method of sorting the user wants
	 * @return ArrayList of results or null if there was an error
	 */
	@Override
	public ArrayList<String> findArtifacts(String searchCriteria, String sortBy) {

		// validation
		if (artifacts.isEmpty()) {
			System.out.println("Error - There are no artifacts stored");
			return null;
		}
		// temp list of matches, stores all artifact data so we can sort later
		ArrayList<Artifact> matches = new ArrayList<>();

		// case 1 - no filter so include all artifacts
		if (searchCriteria == null || searchCriteria.trim().isEmpty()) {
			for (Artifact a : artifacts.values()) {
				matches.add(a);
			}
		}
		// other cases, where search criteriea is not null
		else {
			searchCriteria = searchCriteria.toLowerCase();
			// case 2 - includes artifacts of the specified type
			if (searchCriteria.startsWith("type=")) {
				String type = searchCriteria.substring(5).toLowerCase();
				for (Artifact a : artifacts.values()) {
					if (a.getType().toLowerCase().equals(type)) {
						matches.add(a);
					}
				}
			}
			// case 3 - includes artifacts with a specified name
			else if (searchCriteria.startsWith("name=")) {
				String name = searchCriteria.substring(5).toLowerCase();
				for (Artifact a : artifacts.values()) {
					if (a.getName().toLowerCase().equals(name)) {
						matches.add(a);
					}
				}
			}
			// case 4 - includes artifacts who's names contain the specified phrase
			else if (searchCriteria.startsWith("name_contains=")) {
				String contains = searchCriteria.substring(14).toLowerCase();
				for (Artifact a : artifacts.values()) {
					String name = a.getName().toLowerCase();

					if (linearContains(name, contains)) {
						matches.add(a);
					}
				}
			}
			// case 5 - includes the artifact with the specified id
			else if (searchCriteria.startsWith("id=")) {
				String id = searchCriteria.substring(3).toUpperCase();
				Artifact found = artifacts.get(id);
				if (found != null) {
					matches.add(found);
				}
			}
			// case 6 - The user enters an invalid search criterea
			else {
				System.out.println("Error - Invalid search criterea");
				return null;
			}
		}

		// sorting the list of matches
		if (sortBy != null) {
			sortBy = sortBy.toLowerCase();
			for (int i = 0; i < matches.size() - 1; i++) {
				for (int j = 0; j < matches.size() - i - 1; j++) {
					Artifact artifact1 = matches.get(j);
					Artifact artifact2 = matches.get(j + 1);
					boolean swap = false;

					switch (sortBy) {
					case "name":
						if (artifact1.getName().compareToIgnoreCase(artifact2.getName()) > 0) {
							swap = true;
						}
						break;
					case "type":
						if (artifact1.getType().compareToIgnoreCase(artifact2.getType()) > 0) {
							swap = true;
						}
						break;
					case "engagement":
						if (artifact1.getEngagementMinutes() > artifact2.getEngagementMinutes()) {
							swap = true;
						}
						break;
					case "id":
					default:
						int id1 = Integer.parseInt(artifact1.getId().substring(1));
						int id2 = Integer.parseInt(artifact2.getId().substring(1));
						if (id1 > id2) {
							swap = true;
						}
						break;
					}
					if (swap) {
						Artifact temp = matches.get(j);
						matches.set(j, matches.get(j + 1));
						matches.set(j + 1, temp);
					}
				}
			}
		}

		// convert the matches, to just a list of the matches ID's
		ArrayList<String> result = new ArrayList<>();
		for (int i = 0; i < matches.size(); i++) {
			result.add(matches.get(i).getId());
		}

		return result;
	}

	// --- Exhibit Management ---

	/**
	 * @param name:        the name of the exhibit
	 * @param description: a brief description of the exhibit
	 * @return The ID associated with the exhibit
	 */
	@Override
	public String createExhibit(String name, String description) {

		// validation
		if (name == null || name.isEmpty()) {
			System.out.println("Error - Exhibit name cannot be empty");
			return null;
		}
		if (description == null) {
			System.out.println("Error - Exhibit description cannot be null");
			return null;
		}

		// generate the exhibitId
		String id = "E" + nextExhibitID;
		nextExhibitID++;

		// create an instance of exhibit class
		Exhibit exhibit = new Exhibit(id, name, description);

		// store the new exhibit in the exhibits hash map
		exhibits.put(id, exhibit);

		return id;
	}

	/**
	 * @param exhibitId: the ID associated with the exhibit
	 * @param infoName:  the name of the type of info
	 * @return The requested information as a string, or null if exhibit not found
	 *         or invalid infoName
	 */
	@Override
	public String getExhibitInfo(String exhibitId, String infoName) {

		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return null;
		} else if (infoName == null) {
			System.out.println("Error - invalid infoName value");
			return null;
		}

		// gets exhibit from exhibits hash map
		Exhibit exhibit = exhibits.get(exhibitId);

		// validating that the exhibit exists
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return null;
		}

		// returns info based on info name
		switch (infoName.toLowerCase()) {
		case "name":
			return exhibit.getName();
		case "description":
			return exhibit.getDescription();
		default:
			System.out.println("Error - Invalid infoName value");
			return null;
		}
	}

	/**
	 * @param exhibitId:  the ID associated with the exhibit
	 * @param artifactId: the ID associated with the exhibit
	 * @return the sign text, or null if there is an error
	 */
	@Override
	public String getExhibitArtifactSign(String exhibitId, String artifactId) {

		// validation
		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return null;
		}

		// retrieve sign info from the exhibit
		String sign = exhibit.getSignForArtifact(artifactId);

		if (sign == null) {
			System.out.println("Error - Invalid Artifact");
			return null;
		} else {
			return sign;
		}
	}

	/**
	 * @param exhibitId: the exhibit ID the user wishes to get the total engagement
	 *                   time for
	 * @return the total engagement time for the specified exhibit in minutes
	 */
	@Override
	public int getExhibitEngagementTime(String exhibitId) {
		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return 0;
		}

		// retrieving exhibit
		Exhibit exhibit = exhibits.get(exhibitId);

		// more validation
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return 0;
		}

		// initialisation of the total engagement time variable
		int totalMinutes = 0;

		// looping through all artifactId's in the exhibit
		for (String artifactId : exhibit.getArtifactIDs()) {

			Artifact artifact = artifacts.get(artifactId);

			// validation for the artifact in the museum
			if (artifact != null) {
				totalMinutes += artifact.getEngagementMinutes();
			} else {
				System.out.println("Error - Artifact " + artifactId + " in exhibit does not exist");
				return 0;
			}
		}

		return totalMinutes;
	}

	/**
	 * @param exhibitId: the exhibitId corresponding with the exhibit the user
	 *                   wishes to update
	 * @param infoName:  the type of info the user wishes to update
	 * @param newValue:  the new value the user wishes to update the exhibit with
	 */
	@Override
	public void updateExhibitInfo(String exhibitId, String infoName, String newValue) {

		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return;
		}

		// validation that exhibit exists
		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return;
		}

		// validation of more parameters
		if (infoName == null) {
			System.out.println("Error - invalid infoName value");
			return;
		}
		if (newValue == null || newValue.isEmpty()) {
			System.out.println("Error - newValue cannot be empty");
			return;
		}

		// switch case to update the info the user requested
		switch (infoName.toLowerCase()) {
		case "name":
			exhibit.setName(newValue);
			break;
		case "description":
			exhibit.setDescription(newValue);
			break;
		default:
			System.out.println("Error - Invalid infoName value");
			break;
		}
	}

	/**
	 * @param exhibitId: the corresponding exhibitId the user wishes to delete
	 */
	@Override
	public void deleteExhibit(String exhibitId) {

		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return;
		}
		// validation that exhibit exists
		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return;
		}

		// removing the exhibit from all annual plans using a linear search
		String[] halls = { "HALL_A", "HALL_B", "HALL_C" };
		for (String planId : annualPlans.keySet()) {
			AnnualPlan plan = annualPlans.get(planId);

			for (int h = 0; h < halls.length; h++) {
				String hall = halls[h];
				String[] months = plan.getSchedule().get(hall);

				for (int m = 0; m < months.length; m++) {
					if (months[m] != null && months[m].equals(exhibitId)) {
						months[m] = null;
					}
				}
			}
		}

		// removing the exhibit from the exhibits hash map
		exhibits.remove(exhibitId);
	}

	/**
	 * @param searchCriterea: what type of search and what to search for
	 * @param sortBy:         specifies which method of sorting the user wants
	 * @return ArrayList of results or null if there was an error
	 */
	@Override
	public ArrayList<String> findExhibits(String searchCriteria, String sortBy) {

		// array list to store matches from the search criterea
		ArrayList<Exhibit> matches = new ArrayList<>();

		// validation
		if (exhibits == null) {
			System.out.println("Error - The exhibits hash map is empty");
			return null;
		}

		// case 1 - no filter so include all artifacts
		if (searchCriteria == null || searchCriteria.isEmpty()) {
			for (Exhibit e : exhibits.values()) {
				matches.add(e);
			}
		}
		// other cases, where search criteria is not null
		else {
			searchCriteria = searchCriteria.toLowerCase();
			// case 2 - includes exhibits with the specified name
			if (searchCriteria.startsWith("name=")) {
				String name = searchCriteria.substring(5).toLowerCase();
				for (Exhibit e : exhibits.values()) {
					if (e.getName().toLowerCase().equals(name)) {
						matches.add(e);
					}
				}
			}
			// case 3 - includes exhibits who's names contain the specified phrase
			else if (searchCriteria.startsWith("name_contains=")) {
				String contains = searchCriteria.substring(14).toLowerCase();
				for (Exhibit e : exhibits.values()) {
					String name = e.getName().toLowerCase();

					if (linearContains(name, contains)) {
						matches.add(e);
					}
				}
			}
			// case 4 - includes the artifact with the specified id
			else if (searchCriteria.startsWith("id=")) {
				String id = searchCriteria.substring(3).toUpperCase();
				Exhibit found = exhibits.get(id);
				if (found != null) {
					matches.add(found);
				}
			}
			// case 5 - The user enters an invalid search criterea
			else {
				System.out.println("Error - Invalid search criterea");
				return null;
			}
		}

		// sorting the list of matches
		if (sortBy != null) {
			sortBy = sortBy.toLowerCase();
			for (int i = 0; i < matches.size() - 1; i++) {
				for (int j = 0; j < matches.size() - i - 1; j++) {
					Exhibit exhibit1 = matches.get(j);
					Exhibit exhibit2 = matches.get(j + 1);
					boolean swap = false;

					switch (sortBy) {
					case "name":
						if (exhibit1.getName().compareToIgnoreCase(exhibit2.getName()) > 0) {
							swap = true;
						}
						break;
					case "id":
					default:
						int id1 = Integer.parseInt(exhibit1.getId().substring(1));
						int id2 = Integer.parseInt(exhibit2.getId().substring(1));
						if (id1 > id2) {
							swap = true;
						}
						break;
					}
					if (swap) {
						Exhibit temp = matches.get(j);
						matches.set(j, matches.get(j + 1));
						matches.set(j + 1, temp);
					}
				}
			}
		}

		// convert the matches, to just a list of the matches ID's
		ArrayList<String> result = new ArrayList<>();
		for (int i = 0; i < matches.size(); i++) {
			result.add(matches.get(i).getId());
		}

		return result;
	}

	/**
	 * @param artifactId: the corresponding artifactId of the artifact the user
	 *                    wishes to add to the exhibit
	 * @param exhibitId:  the exhibitId of the exhibit the user wishes to add the
	 *                    specified artifact to
	 * @param sign:       the sign text corresponding with the artifact
	 */
	@Override
	public void addArtifactToExhibit(String artifactId, String exhibitId, String sign) {
		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return;
		}
		if (artifacts.isEmpty()) {
			System.out.println("Error - Artifact HashMap is empty");
			return;
		}
		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return;
		}
		Artifact artifact = artifacts.get(artifactId);
		if (artifact == null) {
			System.out.println("Error - Artifact not found");
			return;
		}

		// optional sign variable
		if (sign == null) {
			sign = "";
		}

		// add the artifactId to the exhibit
		exhibit.addArtifact(artifactId, sign);
	}

	/**
	 * @param artifactId: the artifactId of the artifact the user wishes to remove
	 * @param exhibitId:  the exhibitId of the exhibit the artifact exists within
	 */
	@Override
	public void removeArtifactFromExhibit(String artifactId, String exhibitId) {
		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return;
		}
		if (artifacts.isEmpty()) {
			System.out.println("Error - Artifact HashMap is empty");
			return;
		}
		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return;
		}
		Artifact artifact = artifacts.get(artifactId);
		if (artifact == null) {
			System.out.println("Error - Artifact not found");
			return;
		}

		// remove the artifact from the exhibit
		exhibit.removeArtifact(artifactId);
	}

	/**
	 * @param exhibitId: the exhibit to get the artifacts for
	 * @return a list of all the artifactId's from the chosen exhibit
	 */
	@Override
	public ArrayList<String> getExhibitArtifacts(String exhibitId) {

		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return null;
		}

		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return null;
		}

		// convert Set<String> to ArrayList<String>
		ArrayList<String> list = new ArrayList<>();

		for (String id : exhibit.getArtifactIDs()) {
			list.add(id);
		}

		return list;
	}

	/**
	 * @param exhibitId:   the unique ID of the exhibit
	 * @param artifactIds: a list of artifact IDs in the desired display order
	 */
	@Override
	public void reorderExhibitArtifacts(String exhibitId, ArrayList<String> artifactIds) {

		// validation
		if (exhibits.isEmpty()) {
			System.out.println("Error - Exhibit HashMap is empty");
			return;
		}

		// gets exhibit from exhibits hash map
		Exhibit exhibit = exhibits.get(exhibitId);

		// validating that the exhibit exists
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return;
		}

		// validation that the list of artifacts is the length as the list of artifacts
		// in the exhibit
		if (exhibit.getArtifactIDs().size() != artifactIds.size()) {
			System.out.println("Error - List of Artifact ID's is the not the same length as in the exhibit");
			return;
		}

		// validation that every id in artifactIds is in the exhibit
		for (int i = 0; i < artifactIds.size(); i++) {
			String id = artifactIds.get(i);
			if (!exhibit.containsArtifact(id)) {
				System.out.println("Error - Artifact " + id + " is not part of this exhibit");
				return;
			}
		}

		// set the artifact order to the order of 'artifactIds'
		exhibit.setArtifactOrder(artifactIds);
	}

	// --- Annual Plan Management ---

	/**
	 * @param year: the year for the annual plan
	 * @return a unique id for the annual plan
	 */
	@Override
	public String createAnnualPlan(int year) {

		// validation
		if (year <= 0) {
			System.out.println("Error - Invalid year value");
			return null;
		}

		// generate plan ID
		String planId = "P" + nextPlanID;
		nextPlanID++;

		// create the AnnualPlan object
		AnnualPlan plan = new AnnualPlan(planId, year);

		// store it in museum’s annualPlans HashMap
		annualPlans.put(planId, plan);

		return planId;
	}

	/**
	 * @param year: the year for the annual plan
	 * @return the ID of the annual plan for the year the user requested
	 */
	@Override
	public String getAnnualPlan(int year) {

		// validation
		if (annualPlans.isEmpty()) {
			System.out.println("Error - No annual plans exist");
			return null;
		}

		// search through all plans for the matching year
		for (String id : annualPlans.keySet()) {
			AnnualPlan plan = annualPlans.get(id);
			if (plan.getYear() == year) {
				return id;
			}
		}

		System.out.println("Error - Annual plan for year " + year + " not found");
		return null;
	}

	/**
	 * @param exhibitId: the id for the exhibit the user wishes to add to the annual
	 *                   plan
	 * @param planId:    the id for the annual plan the user wants to add the
	 *                   exhibit to
	 * @param hall:      the specific hall the exhibit should be added to
	 * @param month:     the specific month the exhibit should be added to
	 */
	@Override
	public void addExhibitToAnnualPlan(String exhibitId, String planId, String hall, int month) {

		// validation
		if (annualPlans.isEmpty()) {
			System.out.println("Error - No annual plans exist");
			return;
		}
		if (exhibits.isEmpty()) {
			System.out.println("Error - No exhibits exist");
			return;
		}
		if (month < 1 || month > 12) {
			System.out.println("Error - Month must be between 1 and 12");
			return;
		}
		if (hall == null || !(hall.equals("HALL_A") || hall.equals("HALL_B") || hall.equals("HALL_C"))) {
			System.out.println("Error - Invalid hall name");
			return;
		}

		// validation that the annual plan and exhibit exists
		AnnualPlan plan = annualPlans.get(planId);
		if (plan == null) {
			System.out.println("Error - Annual plan not found");
			return;
		}
		Exhibit exhibit = exhibits.get(exhibitId);
		if (exhibit == null) {
			System.out.println("Error - Exhibit not found");
			return;
		}

		// validation that another exhibit doesn't already exist in the same place
		String existing = plan.getExhibitFor(hall, month);
		if (existing != null) {
			System.out.println("Error - A scheduled exhibit already exists for this hall/month");
			return;
		}

		// store the exhibit in the annual plan
		plan.setExhibitFor(hall, month, exhibitId);
	}

	/**
	 * @param planId: the id for the plan that the exhibit is located in
	 * @param hall:   the hall in which the exhibit is located in
	 * @param month:  the month in which the exhibit is located in
	 * @return the exhibitId from the specified annualPlan
	 */
	@Override
	public String getAnnualPlanExhibit(String planId, String hall, int month) {
		// validation
		if (annualPlans.isEmpty()) {
			System.out.println("Error - No annual plans exist");
			return null;
		}
		if (month < 1 || month > 12) {
			System.out.println("Error - Month must be between 1 and 12");
			return null;
		}
		if (hall == null || !(hall.equals("HALL_A") || hall.equals("HALL_B") || hall.equals("HALL_C"))) {
			System.out.println("Error - Invalid hall name");
			return null;
		}

		// validation that the annual plan exists
		AnnualPlan plan = annualPlans.get(planId);
		if (plan == null) {
			System.out.println("Error - Annual plan not found");
			return null;
		}

		// get the exhibit for the specified annualPlan
		String exhibitId = plan.getExhibitFor(hall, month);

		// if there is no exhibit in the specified annual plan
		if (exhibitId == null) {
			System.out.println("Error - No exhibit added to the specified annual plan");
			return null;
		}

		return exhibitId;
	}

	/**
	 * @param planId: the id associated with the plan the user wishes to delete
	 */
	@Override
	public void deleteAnnualPlan(String planId) {

		// validation
		if (annualPlans.isEmpty()) {
			System.out.println("Error - AnnualPlan HashMap is empty");
			return;
		}
		if (planId == null || planId.isEmpty()) {
			System.out.println("Error - Invalid annual plan ID");
			return;
		}

		// store the plan
		AnnualPlan plan = annualPlans.get(planId);

		// validation that the plan exists
		if (plan == null) {
			System.out.println("Error - Annual plan not found");
			return;
		}

		// delete the plan
		annualPlans.remove(planId);
	}

	/**
	 * @param planId:   the id associated with the plan the user wishes to retrieve
	 *                  info from
	 * @param infoName: the type of info the user wishes to update
	 * @return the info the user requested
	 */
	@Override
	public String getAnnualPlanInfo(String planId, String infoName) {

		// validation
		if (annualPlans.isEmpty()) {
			System.out.println("Error - No annual plans exist");
			return null;
		}
		if (infoName == null) {
			System.out.println("Error - Invalid infoName");
			return null;
		}

		// validation the annual plan exists
		AnnualPlan plan = annualPlans.get(planId);
		if (plan == null) {
			System.out.println("Error - Annual plan not found");
			return null;
		}

		// determine which field to return
		switch (infoName.toLowerCase()) {
		case "year":
			return Integer.toString(plan.getYear());
		case "total_exhibits":
			// count all exhibits in the plans schedule
			int count = 0;
			HashMap<String, String[]> schedule = plan.getSchedule();

			for (String hall : schedule.keySet()) {
				String[] months = schedule.get(hall);
				for (int i = 0; i < months.length; i++) {
					if (months[i] != null) {
						count++;
					}
				}
			}
			return Integer.toString(count);
		default:
			System.out.println("Error - Invalid infoName");
			return null;
		}
	}

	/**
	 * @param planId:   the id associated with the plan the user wishes to update
	 * @param infoName: the type of info the user wishes to update
	 * @param newValue: the new value the user wishes to update the annual plan with
	 */
	@Override
	public void updateAnnualPlanInfo(String planId, String infoName, String newValue) {

		// validation
		if (annualPlans.isEmpty()) {
			System.out.println("Error - No annual plans exist");
			return;
		}
		if (infoName == null) {
			System.out.println("Error - Invalid infoName");
			return;
		}

		// validation that annual plan exists
		AnnualPlan plan = annualPlans.get(planId);
		if (plan == null) {
			System.out.println("Error - Annual plan not found");
			return;
		}

		// validation that year is a number
		switch (infoName.toLowerCase()) {
		case "year":
			try {
				int newYear = Integer.parseInt(newValue);
				plan.setYear(newYear);
			} catch (Exception e) {
				System.out.println("Error - year must be a number");
			}
			break;
		default:
			System.out.println("Error - Invalid infoName for AnnualPlan");
			break;
		}
	}

	// --- Console UI Testing Support ---

	/**
	 * gets the current state of the user interface for testing purposes
	 * 
	 * @return the current state
	 */
	@Override
	public String getCurrentState() {
		return currentState;
	}

	/**
	 * gets the current prompt text being displayed to the user
	 * 
	 * @return the current prompt
	 */
	@Override
	public String getCurrentPrompt() {

		// main Menu prompt
		if ("MAIN_MENU".equals(currentState)) {
			currentPrompt = "==== MUSEUM MAIN MENU ====\n" + "1. Artifact Menu\n" + "2. Exhibit Menu\n"
					+ "3. Annual Plan Menu\n" + "4. Quit\n" + "Select option: ";
		}

		// artifact menu prompt
		else if ("ARTIFACT_MENU".equals(currentState)) {
			currentPrompt = "==== ARTIFACT MENU ====\n" + "1. Add Artifact\n" + "2. List Artifacts\n"
					+ "3. Delete Artifact\n" + "4. Update Artifact\n" + "5. Search Artifacts\n"
					+ "6. View Artifact Info\n" + "7. View Artifact Engagement Time\n" + "8. Back\n"
					+ "Select option: ";
		}

		// exhibit menu prompt
		else if ("EXHIBIT_MENU".equals(currentState)) {
			currentPrompt = "==== EXHIBIT MENU ====\n" + "1. Add Exhibit\n" + "2. List Exhibits\n"
					+ "3. Delete Exhibit\n" + "4. Update Exhibit\n" + "5. Search Exhibits\n" + "6. Get Exhibit Info\n"
					+ "7. Get Exhibit Engagement Time\n" + "8. Add Artifact to Exhibit\n"
					+ "9. Remove Artifact from Exhibit\n" + "10. Get Artifacts in Exhibit\n"
					+ "11. Get Artifact Sign Info\n" + "12. Reorder Artifacts\n" + "13. Back\n" + "Select option: ";
		}

		// annual plan menu prompt
		else if ("ANNUAL_PLAN_MENU".equals(currentState)) {
			currentPrompt = "==== ANNUAL PLAN MENU ====\n" + "1. Create Annual Plan\n" + "2. Add Exhibit To Plan\n"
					+ "3. View Monthly Exhibit\n" + "4. Delete Annual Plan\n" + "5. Update Annual Plan Info\n"
					+ "6. Get the Annual Plan for the Year\n"
					+ "7. Get Exhibit ID for a specific year, month, and hall\n" + "8. Get Annual Plan Info\n"
					+ "9. Back\n" + "Select option: ";
		}

		// exit prompt
		else if ("QUIT".equals(currentState)) {
			currentPrompt = "EXIT";
		}

		return currentPrompt;
	}

	/**
	 * validates if the current input is valid for the current menu state
	 * 
	 * @param input: the input that is going to be tested if it is valid or not
	 * @return true or false depending on if the input is valid or invalid
	 */
	@Override
	public boolean isValidInput(String input) {
		ArrayList<String> valid = getValidInputs();

		if (valid == null)
			return false;

		for (int i = 0; i < valid.size(); i++) {
			if (valid.get(i).equals(input)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * gets list of valid inputs for current state
	 * 
	 * @return an arraylist of valid inputs for the current state
	 */
	@Override
	public ArrayList<String> getValidInputs() {
		ArrayList<String> list = new ArrayList<>();

		if ("MAIN_MENU".equals(currentState)) {
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("4");
		}

		else if ("ARTIFACT_MENU".equals(currentState)) {
			for (int i = 1; i <= 8; i++) {
				list.add(Integer.toString(i));
			}
		}

		else if ("EXHIBIT_MENU".equals(currentState)) {
			for (int i = 1; i <= 13; i++) {
				list.add(Integer.toString(i));
			}
		}

		else if ("ANNUAL_PLAN_MENU".equals(currentState)) {
			for (int i = 1; i <= 9; i++) {
				list.add(Integer.toString(i));
			}
		}

		return list;
	}

	/**
	 * gets list of invalid inputs for current state
	 * 
	 * @return an arraylist of invalid inputs for the current state
	 */
	@Override
	public ArrayList<String> getInvalidInputs() {
		ArrayList<String> invalid = new ArrayList<>();

		// generic invalid inputs
		invalid.add("");
		invalid.add("0");
		invalid.add("-1");
		invalid.add("abc");
		invalid.add("999");

		return invalid;
	}

	/**
	 * processes user input and updates the UI state accordingly
	 * 
	 * @param input: the input to be processed
	 * @return Empty string if input was processed successfully, or error message if
	 *         input was invalid
	 */
	@Override
	public String processInput(String input) {

		// Validate first
		if (!isValidInput(input)) {
			return "Error - Invalid input";
		}

		// MAIN MENU
		if ("MAIN_MENU".equals(currentState)) {
			if ("1".equals(input))
				currentState = "ARTIFACT_MENU";
			else if ("2".equals(input))
				currentState = "EXHIBIT_MENU";
			else if ("3".equals(input))
				currentState = "ANNUAL_PLAN_MENU";
			else if ("4".equals(input)) {
				currentState = "QUIT";
				return "EXIT";
			}
			return "";
		}

		// ARTIFACT MENU
		if ("ARTIFACT_MENU".equals(currentState)) {
			if ("8".equals(input)) {
				currentState = "MAIN_MENU";
			}
			return "";
		}

		// EXHIBIT MENU
		if ("EXHIBIT_MENU".equals(currentState)) {
			if ("13".equals(input)) {
				currentState = "MAIN_MENU";
			}
			return "";
		}

		// ANNUAL PLAN MENU
		if ("ANNUAL_PLAN_MENU".equals(currentState)) {
			if ("9".equals(input)) {
				currentState = "MAIN_MENU";
			}
			return "";
		}

		return "";
	}

}
