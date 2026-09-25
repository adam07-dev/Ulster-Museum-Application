package part01;

import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveQUBMuseum {

	private static Scanner scanner = new Scanner(System.in);
	private static QUBMuseum museum = new QUBMuseum();
	private static MenuState state = MenuState.MAIN_MENU;

	private static void displayMainMenu() {
		System.out.println("\n==== MUSEUM MAIN MENU ====");
		System.out.println("1. Artifact Menu");
		System.out.println("2. Exhibit Menu");
		System.out.println("3. Annual Plan Menu");
		System.out.println("4. Quit");
		System.out.print("Select option: ");
	}

	private static void displayArtifactMenu() {
		System.out.println("\n==== ARTIFACT MENU ====");
		System.out.println("1. Add Artifact");
		System.out.println("2. List Artifacts");
		System.out.println("3. Delete Artifact");
		System.out.println("4. Update Artifact");
		System.out.println("5. Search Artifacts");
		System.out.println("6. View Artifact Info");
		System.out.println("7. View Artifact Engagement Time");
		System.out.println("8. Back");
		System.out.print("Select option: ");
	}

	public static void addArtifactUI() {
		try {
			System.out.print("Enter type: ");
			String type = scanner.nextLine();

			System.out.print("Enter name: ");
			String name = scanner.nextLine();

			System.out.print("Enter description: ");
			String desc = scanner.nextLine();

			System.out.print("Engagement minutes: ");
			int mins = Integer.parseInt(scanner.nextLine());

			String id = museum.createArtifact(type, name, desc, mins);
			if (id != null) {
				System.out.println("Artifact created with ID: " + id);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void listArtifactsUI() {
		try {
			ArrayList<String> all = museum.findArtifacts("", "id");

			if (all == null) {
				System.out.println("There are no artifacts to list");
			} else {
				System.out.println("\nArtifacts:");
				for (String id : all) {
					System.out.println(" - " + id);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteArtifactUI() {
		try {
			System.out.print("Enter artifact ID to delete: ");
			String id = scanner.nextLine();
			museum.deleteArtifact(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateArtifactUI() {
		try {
			System.out.print("Enter artifact ID to update: ");
			String id = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"name"
					"type"
					"engagement_minutes" """);
			System.out.print("Enter the type of info you would like to update: ");
			String infoName = scanner.nextLine();

			System.out.print("\nEnter the new value: ");
			String newValue = scanner.nextLine();

			museum.updateArtifactInfo(id, infoName, newValue);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void searchArtifactsUI() {
		try {
			System.out.println("""
					Valid Inputs:
					"type=TYPE_NAME"
					"name=TEXT"
					"name_contains=TEXT"
					"id=ARTIFACT_ID"
					"" """);
			System.out.print("Enter search criteria: ");
			String searchCriteria = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"name"
					"type"
					"id"
					"engagement"
					"" """);
			System.out.print("Enter sort: ");
			String sortBy = scanner.nextLine();

			ArrayList<String> results = museum.findArtifacts(searchCriteria, sortBy);
			if (results != null) {
				System.out.println("\nResults:");
				System.out.println(results);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void viewArtifactInfoUI() {
		try {
			System.out.print("Enter artifact ID to view: ");
			String artifactId = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"name"
					"type"
					"engagement_minutes" """);
			System.out.print("Enter the type of info you would like to view: ");
			String infoName = scanner.nextLine();

			String info = museum.getArtifactInfo(artifactId, infoName);
			if (info != null) {
				System.out.println("\n" + infoName + ": " + info);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void viewArtifactEngagementTimeUI() {
		try {
			System.out.print("Enter artifact ID to view: ");
			String artifactId = scanner.nextLine();

			int info = museum.getArtifactEngagementTime(artifactId);
			if (info != 0) {
				System.out.println("\nEngagmenet Time: " + info + " minutes");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void displayExhibitMenu() {
		System.out.println("\n==== EXHIBIT MENU ====");
		System.out.println("1. Add Exhibit");
		System.out.println("2. List Exhibits");
		System.out.println("3. Delete Exhibit");
		System.out.println("4. Update Exhibit");
		System.out.println("5. Search Exhibits");
		System.out.println("6. Get Exhibit Info");
		System.out.println("7. Get Exhibit Engagement Time");
		System.out.println("8. Add Artifact to Exhibit");
		System.out.println("9. Remove Artifact from Exhibit");
		System.out.println("10. Get Artifacts in Exhibit");
		System.out.println("11. Get Artifact Sign Info");
		System.out.println("12. Reorder Artifacts");
		System.out.println("13. Back");
		System.out.print("Select option: ");
	}

	public static void addExhibitUI() {
		try {
			System.out.print("Enter exhibit name: ");
			String name = scanner.nextLine();

			System.out.print("Enter description: ");
			String desc = scanner.nextLine();

			String id = museum.createExhibit(name, desc);
			if (id != null) {
				System.out.println("Exhibit created with ID: " + id);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void listExhibitsUI() {
		try {
			ArrayList<String> list = museum.findExhibits("", "id");

			if (list == null) {
				System.out.println("There are no exhibits to list");
			} else {
				System.out.println("\nExhibits:");
				for (String id : list) {
					System.out.println(" - " + id);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteExhibitUI() {
		try {
			System.out.print("Enter exhibit ID to delete: ");
			String id = scanner.nextLine();

			museum.deleteExhibit(id);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateExhibitUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"name"
					"description" """);
			System.out.print("Enter info type to update: ");
			String info = scanner.nextLine();

			System.out.print("Enter new value: ");
			String value = scanner.nextLine();

			museum.updateExhibitInfo(exhibitId, info, value);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void searchExhibitsUI() {
		try {
			System.out.println("""
					Valid Search Inputs:
					"name=TEXT"
					"name_contains=TEXT"
					"id=EXHIBIT_ID" """);
			System.out.print("Enter search criteria: ");
			String searchCriteria = scanner.nextLine();

			System.out.println("""
					Valid Sort Options:
					"name"
					"id" """);
			System.out.print("Enter sort option: ");
			String sortBy = scanner.nextLine();

			ArrayList<String> results = museum.findExhibits(searchCriteria, sortBy);

			if (results != null) {
				System.out.println("\nResults:");
				System.out.println(results);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getExhibitInfoUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"name"
					"description"
					""");
			System.out.print("Enter info to view: ");
			String infoName = scanner.nextLine();

			String info = museum.getExhibitInfo(exhibitId, infoName);
			if (info != null) {
				System.out.println("\n" + infoName + ": " + info);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getExhibitEngagementTimeUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			int info = museum.getExhibitEngagementTime(exhibitId);
			if (info != 0) {
				System.out.println("\nTotal Exhibit Engagement Time: " + info + " minutes");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addArtifactToExhibitUI() {
		try {
			System.out.print("Enter artifact ID: ");
			String artifactId = scanner.nextLine();

			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			System.out.print("Enter sign text (optional): ");
			String sign = scanner.nextLine();

			museum.addArtifactToExhibit(artifactId, exhibitId, sign);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void removeArtifactFromExhibitUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			System.out.print("Enter artifact ID: ");
			String artifactId = scanner.nextLine();

			museum.removeArtifactFromExhibit(artifactId, exhibitId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getArtifactsInExhibitUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			ArrayList<String> list = museum.getExhibitArtifacts(exhibitId);

			if (list == null) {
				System.out.println("Exhibit contains no artifacts");
			} else {
				System.out.println("\nArtifacts in exhibit:");
				for (String id : list) {
					System.out.println(" - " + id);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getArtifactSignInfoUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			System.out.print("Enter artifact ID: ");
			String artifactId = scanner.nextLine();

			String sign = museum.getExhibitArtifactSign(exhibitId, artifactId);

			if (sign == null) {
				System.out.println("No sign text found.");
			} else {
				System.out.println("\nSign Text:\n" + sign);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void reorderArtifactsUI() {
		try {
			System.out.print("Enter exhibit ID: ");
			String exhibitId = scanner.nextLine();

			ArrayList<String> current = museum.getExhibitArtifacts(exhibitId);

			if (current == null) {
				System.out.println("No artifacts to reorder.");
				return;
			} else {
				System.out.println("Current order: " + current);

				ArrayList<String> newOrder = new ArrayList<>();

				System.out.println("Enter artifact IDs in the new order:");
				for (int i = 0; i < current.size(); i++) {
					System.out.print("Position " + (i + 1) + ": ");
					newOrder.add(scanner.nextLine());
				}

				museum.reorderExhibitArtifacts(exhibitId, newOrder);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void displayAnnualPlanMenu() {
		System.out.println("\n==== ANNUAL PLAN MENU ====");
		System.out.println("1. Create Annual Plan");
		System.out.println("2. Add Exhibit To Plan");
		System.out.println("3. View Monthly Exhibit");
		System.out.println("4. Delete Annual Plan");
		System.out.println("5. Update Annual Plan Info");
		System.out.println("6. Get the Annual Plan for the Year");
		System.out.println("7. Get Exhibit ID for a specific year, month, and hall");
		System.out.println("8. Get Annual Plan Info");
		System.out.println("9. Back");
		System.out.print("Select option: ");
	}

	public static void createAnnualPlanUI() {
		try {
			System.out.print("Enter year (e.g., 2025): ");
			int year = Integer.parseInt(scanner.nextLine());

			String planId = museum.createAnnualPlan(year);
			if (planId != null) {
				System.out.println("Annual Plan created with ID: " + planId);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void addExhibitToPlanUI() {
		try {
			System.out.print("Enter Exhibit ID: ");
			String exhibitId = scanner.nextLine();

			System.out.print("Enter Plan ID: ");
			String planId = scanner.nextLine();

			System.out.println("""
					Valid Hall Names:
					"HALL_A"
					"HALL_B"
					"HALL_C" """);
			System.out.print("Enter hall: ");
			String hall = scanner.nextLine();

			System.out.print("Enter month (1–12): ");
			int month = Integer.parseInt(scanner.nextLine());

			museum.addExhibitToAnnualPlan(exhibitId, planId, hall, month);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void viewMonthlyExhibitUI() {
		try {
			System.out.print("Enter Plan ID: ");
			String planId = scanner.nextLine();

			System.out.print("Enter Hall: ");
			String hall = scanner.nextLine();

			System.out.print("Enter month (1–12): ");
			int month = Integer.parseInt(scanner.nextLine());

			String exhibit = museum.getAnnualPlanExhibit(planId, hall, month);

			if (exhibit != null) {
				System.out.println("Scheduled Exhibit: " + exhibit);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void deleteAnnualPlanUI() {
		try {
			System.out.print("Enter Plan ID to delete: ");
			String planId = scanner.nextLine();

			museum.deleteAnnualPlan(planId);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void updateAnnualPlanInfoUI() {
		try {
			System.out.print("Enter Plan ID: ");
			String planId = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"year" """);
			System.out.print("Enter info field to update: ");
			String infoName = scanner.nextLine();

			System.out.print("Enter new value: ");
			String newValue = scanner.nextLine();

			museum.updateAnnualPlanInfo(planId, infoName, newValue);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void getAnnualPlanForYearUI() {
		try {
			System.out.print("Enter year: ");
			int year = Integer.parseInt(scanner.nextLine());

			String planId = museum.getAnnualPlan(year);
			if (planId != null) {
				System.out.println("Annual Plan ID for year " + year + ": " + planId);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void getExhibitIDForYearMonthHallUI() {
		try {
			System.out.print("Enter Plan ID: ");
			String planId = scanner.nextLine();

			System.out.print("Enter Hall: ");
			String hall = scanner.nextLine();

			System.out.print("Enter month (1–12): ");
			int month = Integer.parseInt(scanner.nextLine());

			String exhibitId = museum.getAnnualPlanExhibit(planId, hall, month);
			if (exhibitId != null) {
				System.out.println("Exhibit scheduled: " + exhibitId);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void getAnnualPlanInfoUI() {
		try {
			System.out.print("Enter Plan ID: ");
			String planId = scanner.nextLine();

			System.out.println("""
					Valid Inputs:
					"year"
					"total_exhibits" """);
			System.out.print("Enter info field: ");
			String infoName = scanner.nextLine();

			String value = museum.getAnnualPlanInfo(planId, infoName);
			if (value != null) {
				System.out.println(infoName + ": " + value);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		// if user chooses to quit this condition can end the while loop easily
		boolean running = true;
		while (running) {

			switch (state) {

			// MAIN MENU -
			case MAIN_MENU:
				displayMainMenu();
				String input = scanner.nextLine();

				switch (input) {
				case "1":
					state = MenuState.ARTIFACT_MENU;
					break;
				case "2":
					state = MenuState.EXHIBIT_MENU;
					break;
				case "3":
					state = MenuState.ANNUAL_PLAN_MENU;
					break;
				case "4":
					state = MenuState.QUIT;
					break;
				default:
					System.out.println("Invalid option, try again.");
				}
				break;

			// ARTIFACT MENU
			case ARTIFACT_MENU:
				displayArtifactMenu();
				String a = scanner.nextLine();

				switch (a) {
				case "1":
					addArtifactUI();
					break;
				case "2":
					listArtifactsUI();
					break;
				case "3":
					deleteArtifactUI();
					break;
				case "4":
					updateArtifactUI();
					break;
				case "5":
					searchArtifactsUI();
					break;
				case "6":
					viewArtifactInfoUI();
					break;
				case "7":
					viewArtifactEngagementTimeUI();
					break;
				case "8":
					state = MenuState.MAIN_MENU;
					break;
				default:
					System.out.println("Invalid choice.");
				}
				break;

			// EXHIBIT MENU
			case EXHIBIT_MENU:
				displayExhibitMenu();
				String e = scanner.nextLine();

				switch (e) {
				case "1":
					addExhibitUI();
					break;
				case "2":
					listExhibitsUI();
					break;
				case "3":
					deleteExhibitUI();
					break;
				case "4":
					updateExhibitUI();
					break;
				case "5":
					searchExhibitsUI();
					break;
				case "6":
					getExhibitInfoUI();
					break;
				case "7":
					getExhibitEngagementTimeUI();
					break;
				case "8":
					addArtifactToExhibitUI();
					break;
				case "9":
					removeArtifactFromExhibitUI();
					break;
				case "10":
					getArtifactsInExhibitUI();
					break;
				case "11":
					getArtifactSignInfoUI();
					break;
				case "12":
					reorderArtifactsUI();
					break;
				case "13":
					state = MenuState.MAIN_MENU;
					break;
				default:
					System.out.println("Invalid option.");
				}
				break;

			// ---------------- ANNUAL PLAN MENU ----------------
			case ANNUAL_PLAN_MENU:
				displayAnnualPlanMenu();
				String p = scanner.nextLine();

				switch (p) {
				case "1":
					createAnnualPlanUI();
					break;
				case "2":
					addExhibitToPlanUI();
					break;
				case "3":
					viewMonthlyExhibitUI();
					break;
				case "4":
					deleteAnnualPlanUI();
					break;
				case "5":
					updateAnnualPlanInfoUI();
					break;
				case "6":
					getAnnualPlanForYearUI();
					break;
				case "7":
					getExhibitIDForYearMonthHallUI();
					break;
				case "8":
					getAnnualPlanInfoUI();
					break;
				case "9":
					state = MenuState.MAIN_MENU;
					break;
				default:
					System.out.println("Invalid option.");
				}
				break;

			case QUIT:
				System.out.println("Exiting");
				running = false;
				break;
			}
		}

		scanner.close();
	}
}
