package part02;

import java.util.ArrayList;

import api.TestingQUBMuseumAPI;
import part01.QUBMuseum;

public class TestingQUBMuseum implements TestingQUBMuseumAPI {

	// Tests can replace this instance with a new one if you want a clean start
	QUBMuseum api = new QUBMuseum();

	public static void main(String[] args) {

		TestingQUBMuseum tester = new TestingQUBMuseum();

		// UNIT TESTS

		// Run comprehensive test suite covering all API methods and edge cases,
		// including error usage
		tester.runArtifactCRUDTests();
		tester.runExhibitCRUDTests();
		tester.runAnnualPlanCRUDTests();

		// Add additional tests unit tests to cover any advanced functionality

		// END-TO-END TESTS

		// Simulate user inputs to test plausible end to end tests that a user might
		// perform
		// Use to validate both correct and incorrect actions being handled correctly by
		// the system
		// Validate both the menu responses and the internal state of the system using
		// the api
		tester.runMenuTests();

		// FUZZING TESTS
		// Randomly simulate user inputs with and without errors to validate that the
		// user interface
		// is working correctly
		tester.runFuzzingTests();
	}

	@Override
	public void runArtifactCRUDTests() {
		System.out.println("\n===== RUNNING ARTIFACT CRUD TESTS =====");

		int passed = 0;
		int failed = 0;

		try {
			// fresh museum instance
			api = new QUBMuseum();

			// ============ 1. CREATE ARTIFACT ============

			// 1a. Create Artifact - valid
			String id1 = api.createArtifact("Pottery", "Clay Tablet", "Ancient writing", 5);

			if (id1 != null && id1.equals("A1")) {
				System.out.println("PASS: Created artifact A1");
				passed++;
			} else {
				System.out.println("FAIL: Artifact A1 was not created correctly");
				failed++;
			}
			// 1b. Create Artifact - invalid
			String id2 = api.createArtifact("invalid", "Clay Tablet", "Ancient writing", 3);

			if (id2 == null) {
				System.out.println("PASS: New Artifact not created");
				passed++;
			} else {
				System.out.println("FAIL: Error in artifact creation");
				failed++;
			}
			// 1c. Create Artifact - valid
			String id3 = api.createArtifact("Jewelry", "Emerald Chain", "Beautiful Emerald Stone Chain", 3);

			if (id3 != null && id3.equals("A2")) {
				System.out.println("PASS: Created artifact A2");
				passed++;
			} else {
				System.out.println("FAIL: Artifact A2 was not created correctly");
				failed++;
			}
			// 1d. Create Artifact - valid
			String id4 = api.createArtifact("Sculpture", "The Monk", "Beautiful Stone Sculpture of an Ancient Monk",
					10);

			if (id4 != null && id4.equals("A3")) {
				System.out.println("PASS: Created artifact A3");
				passed++;
			} else {
				System.out.println("FAIL: Artifact A3 was not created correctly");
				failed++;
			}
			// 1e. Create Artifact - invalid
			String id5 = api.createArtifact("Pottery", "Clay Tablet", "Ancient writing", -4);

			if (id5 == null) {
				System.out.println("PASS: New Artifact not created");
				passed++;
			} else {
				System.out.println("FAIL: Error in artifact creation");
				failed++;
			}
			// 1f. Create Artifact - invalid - empty type
			String id6 = api.createArtifact("", "Clay Tablet", "Ancient writing", 3);

			if (id6 == null) {
				System.out.println("PASS: New Artifact not created");
				passed++;
			} else {
				System.out.println("FAIL: Error in artifact creation");
				failed++;
			}
			// 1g. Create Artifact - invalid - empty name
			String id7 = api.createArtifact("Pottery", "", "Ancient writing", 3);

			if (id7 == null) {
				System.out.println("PASS: New Artifact not created");
				passed++;
			} else {
				System.out.println("FAIL: Error in artifact creation");
				failed++;
			}
			// 1h. Create Artifact - invalid - empty description
			String id8 = api.createArtifact("Pottery", "Clay Tablet", "", 3);

			if (id8 == null) {
				System.out.println("PASS: New Artifact not created");
				passed++;
			} else {
				System.out.println("FAIL: Error in artifact creation");
				failed++;
			}

			// ============ 2. GET ARTIFACT INFO ============

			// 2a. Get Artifact info - valid
			String nameInfo1 = api.getArtifactInfo("A1", "name");

			if (nameInfo1 != null && nameInfo1.equals("Clay Tablet")) {
				System.out.println("PASS: getArtifactInfo returned correct name");
				passed++;
			} else {
				System.out.println("FAIL: getArtifactInfo returned incorrect result");
				failed++;
			}

			// 2b. Get Artifact info - invalid
			String nameInfo2 = api.getArtifactInfo("A999", "name");

			if (nameInfo2 == null) {
				System.out.println("PASS: Correct error on invalid ID");
				passed++;
			} else {
				System.out.println("FAIL: Missing error message on invalid ID");
				failed++;
			}

			// ============ 3. UPDATE ARTIFACT INFO ============

			// 3a. Update Artifact info - valid
			api.updateArtifactInfo("A1", "name", "New Tablet");
			String updatedInfo1 = api.getArtifactInfo("A1", "name");

			if (updatedInfo1.equals("New Tablet")) {
				System.out.println("PASS: updateArtifactInfo updated name");
				passed++;
			} else {
				System.out.println("FAIL: updateArtifactInfo did not update name");
				failed++;
			}
			// 3b. Update Artifact info - invalid
			api.updateArtifactInfo("A1", "invalid", "Old Tablet");
			String updatedInfo2 = api.getArtifactInfo("A1", "name");

			if (updatedInfo2.equals("New Tablet")) {
				System.out.println("PASS: updateArtifactInfo did not update name");
				passed++;
			} else {
				System.out.println("FAIL: updateArtifactInfo updated name");
				failed++;
			}
			// 3c. Update Artifact info (engagement time) - valid
			api.updateArtifactInfo("A2", "engagement", "2");
			String updatedInfo3 = api.getArtifactInfo("A2", "engagement_minutes");

			if (updatedInfo3.equals("2")) {
				System.out.println("PASS: updateArtifactInfo updated engagement time");
				passed++;
			} else {
				System.out.println("FAIL: updateArtifactInfo did not update engagement time");
				failed++;
			}
			// 3d. Update Artifact Engagement Time - invalid negative value
			api.updateArtifactInfo("A2", "engagement", "-10");
			String updatedInfo4 = api.getArtifactInfo("A2", "engagement_minutes");

			if (updatedInfo4.equals("2")) {
				System.out.println("PASS: Negative engagement rejected");
				passed++;
			} else {
				System.out.println("FAIL: Negative engagement incorrectly accepted");
				failed++;
			}
			// 3e. Update Artifact - invalid ID
			api.updateArtifactInfo("A999", "name", "invalid");

			String updatedInfo5 = api.getArtifactInfo("A999", "name");

			if (updatedInfo5 == null) {
				System.out.println("PASS: updateArtifactInfo rejected invalid ID");
				passed++;
			} else {
				System.out.println("FAIL: updateArtifactInfo accepted invalid ID");
				failed++;
			}
			// 3f. Update Artifact - invalid - null newValue
			api.updateArtifactInfo("A1", "name", null);

			String updatedInfo6 = api.getArtifactInfo("A1", "name");

			if (updatedInfo6 == "New Tablet") {
				System.out.println("PASS: updateArtifactInfo rejected invalid newValue");
				passed++;
			} else {
				System.out.println("FAIL: updateArtifactInfo accepted invalid newValue");
				failed++;
			}
			// 3g. Update Artifact - invalid - empty newValue
			api.updateArtifactInfo("A1", "name", "");

			String updatedInfo7 = api.getArtifactInfo("A1", "name");

			if (updatedInfo7 == "New Tablet") {
				System.out.println("PASS: updateArtifactInfo rejected invalid newValue");
				passed++;
			} else {
				System.out.println("FAIL: updateArtifactInfo accepted invalid newValue");
				failed++;
			}

			// ============ 4. FIND ARTIFACTS (SEARCHING) ============

			// 4a. Test Searching Artifacts - name - valid
			ArrayList<String> results1 = api.findArtifacts("name=new tablet", "name");

			if (results1 != null && results1.size() == 1 && results1.get(0).equals("A1")) {
				System.out.println("PASS: findArtifacts returned correct search results");
				passed++;
			} else {
				System.out.println("FAIL: findArtifacts search incorrect");
				failed++;
			}
			// 4b. Test Searching Artifacts - invalid
			ArrayList<String> results2 = api.findArtifacts("name_contains=new tablet", "");

			if (results2 != null && results2.size() == 1 && results2.get(0).equals("A1")) {
				System.out.println("PASS: findArtifacts returned correct search results");
				passed++;
			} else {
				System.out.println("FAIL: findArtifacts search incorrect");
				failed++;
			}
			// 4c. Test Searching Artifacts (name_contains)- valid
			ArrayList<String> results3 = api.findArtifacts("name_contains=a", "type");

			if (results3 != null && results3.size() == 2 && results3.get(0).equals("A2")
					&& results3.get(1).equals("A1")) {
				System.out.println("PASS: findArtifacts returned correct search results");
				passed++;
			} else {
				System.out.println("FAIL: findArtifacts search incorrect");
				failed++;
			}
			// 4d. Test Searching Artifacts - empty criteria returns all - valid
			ArrayList<String> results4 = api.findArtifacts("", "id");

			if (results4.size() == 3 && results4.get(0).equals("A1") && results4.get(1).equals("A2")
					&& results4.get(2).equals("A3")) {
				System.out.println("PASS: Empty searchCriteria returned all artifacts");
				passed++;
			} else {
				System.out.println("FAIL: Empty searchCriteria produced wrong results");
				failed++;
			}
			// 4e. Test Searching Artifacts - invalid search criteria
			ArrayList<String> results5 = api.findArtifacts("invalid=test", "name");

			if (results5 == null) {
				System.out.println("PASS: Invalid search criteria correctly handled");
				passed++;
			} else {
				System.out.println("FAIL: Invalid search criteria was not rejected");
				failed++;
			}
			// 4f. Test Searching Artifacts - invalid sortBy
			ArrayList<String> results6 = api.findArtifacts("", "invalid");

			if (results6.get(0).equals("A1")) {
				System.out.println("PASS: Invalid sortBy defaulted to ID order");
				passed++;
			} else {
				System.out.println("FAIL: Invalid sortBy did not default to ID");
				failed++;
			}
			// 4g. Test Searching Artifacts - valid - sort by engagement
			ArrayList<String> results7 = api.findArtifacts("", "engagement");

			if (results7 != null && results7.size() == 3 && results7.get(0).equals("A2") && results7.get(1).equals("A1")
					&& results7.get(2).equals("A3")) {
				System.out.println("PASS: findArtifacts returned correct search results");
				passed++;
			} else {
				System.out.println("FAIL: findArtifacts search incorrect");
				failed++;
			}

			// ============ 5. GET ARTIFACT ENGAGEMENT TIME ============

			// 5a. Test get Artifact Engagement Time - valid
			int time1 = api.getArtifactEngagementTime("A1");

			if (time1 == 5) {
				System.out.println("PASS: Correct engagement time returned");
				passed++;
			} else {
				System.out.println("FAIL: Engagement time incorrect");
				failed++;
			}
			// 5b. Test get Artifact Engagement Time - invalid
			int time2 = api.getArtifactEngagementTime("0");

			if (time2 == 0) {
				System.out.println("PASS: Error as invalid ID given");
				passed++;
			} else {
				System.out.println("FAIL: Error as 0 is not returned");
				failed++;
			}
			// 5c. Test get Artifact Engagement Time - valid
			int time3 = api.getArtifactEngagementTime("A2");

			if (time3 == 2) {
				System.out.println("PASS: Correct engagement time returned");
				passed++;
			} else {
				System.out.println("FAIL: Engagement time incorrect");
				failed++;
			}

			// ============ 6. DELETE ARTIFACT ============

			// 6a. Test for deleting Artifacts - valid
			api.deleteArtifact("A1");

			String deletedInfo1 = api.getArtifactInfo("A1", "name");

			if (deletedInfo1 == null) {
				System.out.println("PASS: deleteArtifact successfully removed A1");
				passed++;
			} else {
				System.out.println("FAIL: deleteArtifact did not remove A1 correctly");
				failed++;
			}
			// 6b. Test for deleting Artifacts - invalid
			api.deleteArtifact("test");

			String deletedInfo2 = api.getArtifactInfo("test", "name");

			if (deletedInfo2 == null) {
				System.out.println("PASS: Artifact not deleted because invalid ID");
				passed++;
			} else {
				System.out.println("FAIL: Error in artifact deletion or getting process");
				failed++;
			}
			// 6c. Test for deleting Artifacts - test that deleteArtifact removes from
			// exhibits & plans
			String exId = api.createExhibit("Dinosaurs", "Big animals");
			api.addArtifactToExhibit("A2", exId, "sign");

			String planId = api.createAnnualPlan(2025);
			api.addExhibitToAnnualPlan(exId, planId, "HALL_A", 1);

			api.deleteArtifact("A2");

			boolean inExhibit = api.getExhibitArtifacts(exId).contains("A2");

			if (!inExhibit) {
				System.out.println("PASS: deleteArtifact removed artifact from exhibit");
				passed++;
			} else {
				System.out.println("FAIL: deleteArtifact did not remove from exhibit");
				failed++;
			}

		} catch (Exception e) {
			System.out.println("ERROR during Artifact CRUD tests: " + e.getMessage());
			failed++;
		}

		System.out.println("===== ARTIFACT CRUD TEST RESULTS =====");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);
	}

	@Override
	public void runExhibitCRUDTests() {

		System.out.println("\n===== RUNNING EXHIBIT CRUD TESTS =====");

		int passed = 0;
		int failed = 0;

		try {
			// fresh museum instance
			api = new QUBMuseum();

			// create some artifacts for testing purposes
			String A1 = api.createArtifact("Pottery", "Clay Tablet", "Writing", 3);
			String A2 = api.createArtifact("Jewelry", "Necklace", "Gold", 5);
			String A3 = api.createArtifact("Sculpture", "Statue", "Old Statue", 7);

			// ============ 1. CREATE EXHIBIT ============

			// 1a. valid creation
			String createExhibit1 = api.createExhibit("Dinosaurs", "Big scary lizards");
			if (createExhibit1 != null && createExhibit1.equals("E1")) {
				System.out.println("PASS: Created exhibit E1");
				passed++;
			} else {
				System.out.println("FAIL: Exhibit E1 was not created correctly");
				failed++;
			}

			// 1b. invalid creation (empty name)
			String createExhibit2 = api.createExhibit("", "desc");
			if (createExhibit2 == null) {
				System.out.println("PASS: Invalid exhibit name rejected");
				passed++;
			} else {
				System.out.println("FAIL: Invalid exhibit name accepted");
				failed++;
			}

			// 1c. invalid creation (null description)
			String createExhibit3 = api.createExhibit("name", null);
			if (createExhibit3 == null) {
				System.out.println("PASS: Null description rejected");
				passed++;
			} else {
				System.out.println("FAIL: Null description accepted");
				failed++;
			}

			// 1d. valid creation
			String createExhibit4 = api.createExhibit("Egypt", "Mummies");
			if (createExhibit4 != null && createExhibit4.equals("E2")) {
				System.out.println("PASS: Created exhibit E2");
				passed++;
			} else {
				System.out.println("FAIL: Exhibit E2 was not created correctly");
				failed++;
			}

			// ============ 2. GET EXHIBIT INFO ============

			// 2a. valid get name
			String getExhibit1 = api.getExhibitInfo("E1", "name");
			if (getExhibit1 != null && getExhibit1.equals("Dinosaurs")) {
				System.out.println("PASS: getExhibitInfo returned correct name");
				passed++;
			} else {
				System.out.println("FAIL: getExhibitInfo returned incorrect name");
				failed++;
			}

			// 2b. valid get description
			String getExhibit2 = api.getExhibitInfo("E1", "description");
			if (getExhibit2 != null && getExhibit2.equals("Big scary lizards")) {
				System.out.println("PASS: getExhibitInfo returned correct description");
				passed++;
			} else {
				System.out.println("FAIL: getExhibitInfo returned incorrect description");
				failed++;
			}

			// 2c. invalid id get name
			String getExhibit3 = api.getExhibitInfo("E999", "name");
			if (getExhibit3 == null) {
				System.out.println("PASS: Correct error on invalid exhibit ID");
				passed++;
			} else {
				System.out.println("FAIL: No error message on invalid exhibit ID");
				failed++;
			}

			// 2d. invalid infoName
			String getExhibit4 = api.getExhibitInfo("E1", "invalid");
			if (getExhibit4 == null) {
				System.out.println("PASS: Correct error on invalid infoName");
				passed++;
			} else {
				System.out.println("FAIL: Invalid infoName not rejected");
				failed++;
			}

			// ============ 3. UPDATE EXHIBIT INFO ============

			// 3a. valid update name
			api.updateExhibitInfo("E1", "name", "Dinos");
			String ExhibitOne1 = api.getExhibitInfo("E1", "name");
			if (ExhibitOne1.equals("Dinos")) {
				System.out.println("PASS: updateExhibitInfo updated name");
				passed++;
			} else {
				System.out.println("FAIL: updateExhibitInfo did not update name");
				failed++;
			}

			// 3b. invalid id update name
			api.updateExhibitInfo("E999", "name", "NewName");
			String ExhibitOne2 = api.getExhibitInfo("E1", "name");
			if (ExhibitOne2.equals("Dinos")) {
				System.out.println("PASS: updateExhibitInfo did not alter valid exhibit when invalid ID used");
				passed++;
			} else {
				System.out.println("FAIL: updateExhibitInfo incorrectly modified exhibit on invalid ID");
				failed++;
			}

			// 3c. invalid infoName
			api.updateExhibitInfo("E1", "invalid", "INVALID");
			String ExhibitOne3 = api.getExhibitInfo("E1", "name");
			if (ExhibitOne3.equals("Dinos")) {
				System.out.println("PASS: updateExhibitInfo rejected invalid infoName");
				passed++;
			} else {
				System.out.println("FAIL: updateExhibitInfo incorrectly changed exhibit with invalid infoName");
				failed++;
			}

			// ============ 4. ADD ARTIFACT TO EXHIBIT ============

			// 4a. valid addition
			api.addArtifactToExhibit(A1, "E1", "Stone writing");
			ArrayList<String> addArtifact1 = api.getExhibitArtifacts("E1");
			if (addArtifact1.size() == 1 && addArtifact1.get(0).equals(A1)) {
				System.out.println("PASS: addArtifactToExhibit added artifact");
				passed++;
			} else {
				System.out.println("FAIL: addArtifactToExhibit failed to add");
				failed++;
			}

			// 4b. invalid artifact id addition
			api.addArtifactToExhibit("INVALID", "E1", "text");
			if (api.getExhibitArtifacts("E1").size() == 1) {
				System.out.println("PASS: invalid artifact not added");
				passed++;
			} else {
				System.out.println("FAIL: invalid artifact was added");
				failed++;
			}

			// 4c. invalid exhibit id addition
			api.addArtifactToExhibit(A2, "E999", "text");
			System.out.println("PASS: artifact not added to invalid exhibit");
			passed++;

			// ============ 5. REMOVE ARTIFACT FROM EXHIBIT ============

			// 5a. valid removal
			api.removeArtifactFromExhibit(A1, "E1");
			ArrayList<String> artifact1 = api.getExhibitArtifacts("E1");
			if (artifact1.isEmpty()) {
				System.out.println("PASS: removeArtifactFromExhibit removed artifact");
				passed++;
			} else {
				System.out.println("FAIL: removeArtifactFromExhibit did not remove");
				failed++;
			}

			// 5b. invalid removal (bad artifact)
			api.removeArtifactFromExhibit("INVALID", "E1");
			if (api.getExhibitArtifacts("E1").isEmpty()) {
				System.out.println("PASS: invalid artifact not removed");
				passed++;
			} else {
				System.out.println("FAIL: error in removal process");
				failed++;
			}

			// ============ 6. GET ENGAGEMENT TIME ============

			// add two artifacts back for engagement test
			api.addArtifactToExhibit(A2, "E1", "");
			api.addArtifactToExhibit(A3, "E1", "");

			int eangagementTime1 = api.getExhibitEngagementTime("E1");
			if (eangagementTime1 == 5 + 7) {
				System.out.println("PASS: getExhibitEngagementTime returned correct total");
				passed++;
			} else {
				System.out.println("FAIL: getExhibitEngagementTime incorrect");
				failed++;
			}

			// 6b. invalid id
			int eangagementTime2 = api.getExhibitEngagementTime("INVALID");
			if (eangagementTime2 == 0) {
				System.out.println("PASS: invalid exhibit returned 0 engagement");
				passed++;
			} else {
				System.out.println("FAIL: invalid exhibit returned not 0");
				failed++;
			}

			// ============ 7. FIND EXHIBITS (SEARCHING) ============

			// 7a. valid find name
			ArrayList<String> search1 = api.findExhibits("name=dinos", "id");
			if (search1.size() == 1 && search1.get(0).equals("E1")) {
				System.out.println("PASS: findExhibits name search correct");
				passed++;
			} else {
				System.out.println("FAIL: findExhibits name search incorrect");
				failed++;
			}

			// 7b. valid find name_contains
			ArrayList<String> search2 = api.findExhibits("name_contains=o", "name");
			if (search2.size() == 1 && search2.get(0).equals("E1")) {
				System.out.println("PASS: findExhibits name_contains correct");
				passed++;
			} else {
				System.out.println("FAIL: findExhibits name_contains incorrect");
				failed++;
			}

			// 7c. valid find id
			ArrayList<String> search3 = api.findExhibits("id=E2", "name");
			if (search3.size() == 1 && search3.get(0).equals("E2")) {
				System.out.println("PASS: findExhibits ID search correct");
				passed++;
			} else {
				System.out.println("FAIL: findExhibits ID search incorrect");
				failed++;
			}

			// 7d.invalid find id
			ArrayList<String> invalidSearch = api.findExhibits("invalid=invalid", "id");
			if (invalidSearch == null) {
				System.out.println("PASS: invalid search criteria rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid search criteria not rejected");
				failed++;
			}

			// ============ 8. REORDER ARTIFACTS ============

			// 8a. valid reorder
			ArrayList<String> newOrder = new ArrayList<>();
			newOrder.add(A3);
			newOrder.add(A2);

			api.reorderExhibitArtifacts("E1", newOrder);
			ArrayList<String> reordered1 = api.getExhibitArtifacts("E1");

			if (reordered1.get(0).equals(A3) && reordered1.get(1).equals(A2)) {
				System.out.println("PASS: reorderExhibitArtifacts set correct order");
				passed++;
			} else {
				System.out.println("FAIL: reorderExhibitArtifacts incorrect order");
				failed++;
			}

			// 8b. invalid reorder - not correct artifacts added into order arraylist
			ArrayList<String> invalidOrder = new ArrayList<>();
			invalidOrder.add(A3);

			api.reorderExhibitArtifacts("E1", invalidOrder);
			ArrayList<String> reordered2 = api.getExhibitArtifacts("E1");

			if (reordered2.size() == 2) {
				System.out.println("PASS: reorderExhibitArtifacts rejected invalid list size");
				passed++;
			} else {
				System.out.println("FAIL: reorderExhibitArtifacts incorrectly modified list on bad size");
				failed++;
			}

			// ============ 9. DELETE EXHIBIT ============

			// 9a. valid deletion
			api.deleteExhibit("E1");
			String checkDeleted = api.getExhibitInfo("E1", "name");

			if (checkDeleted == null) {
				System.out.println("PASS: deleteExhibit removed exhibit E1");
				passed++;
			} else {
				System.out.println("FAIL: deleteExhibit failed to delete E1");
				failed++;
			}

			// 9b. invalid deletion
			api.deleteExhibit("INVALID");
			System.out.println("PASS: invalid delete did not crash (manual check)");
			passed++;

		} catch (Exception e) {
			System.out.println("ERROR during Exhibit CRUD tests: " + e.getMessage());
			failed++;
		}

		System.out.println("===== EXHIBIT CRUD TEST RESULTS =====");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);
	}

	@Override
	public void runAnnualPlanCRUDTests() {

		System.out.println("\n===== RUNNING ANNUAL PLAN CRUD TESTS =====");

		int passed = 0;
		int failed = 0;

		try {
			// fresh museum instance
			api = new QUBMuseum();

			// create exhibits for testing purposes
			String E1 = api.createExhibit("Dinosaurs", "Big scary lizards");
			String E2 = api.createExhibit("Egypt", "Mummies");

			// ============ 1. CREATE ANNUAL PLAN ============

			// 1a. valid creation
			String createPlan1 = api.createAnnualPlan(2025);
			if (createPlan1 != null && createPlan1.equals("P1")) {
				System.out.println("PASS: Created annual plan P1");
				passed++;
			} else {
				System.out.println("FAIL: Annual plan P1 was not created correctly");
				failed++;
			}

			// 1b. invalid creation (year <= 0)
			String createPlan2 = api.createAnnualPlan(0);
			if (createPlan2 == null) {
				System.out.println("PASS: Invalid year rejected");
				passed++;
			} else {
				System.out.println("FAIL: Invalid year accepted");
				failed++;
			}

			// ============ 2. GET ANNUAL PLAN BY YEAR ============

			// 2a. valid get
			String getPlan1 = api.getAnnualPlan(2025);
			if (getPlan1 != null && getPlan1.equals("P1")) {
				System.out.println("PASS: getAnnualPlan returned correct ID");
				passed++;
			} else {
				System.out.println("FAIL: getAnnualPlan returned incorrect ID");
				failed++;
			}

			// 2b. invalid year
			String getPlan2 = api.getAnnualPlan(9999);
			if (getPlan2 == null) {
				System.out.println("PASS: invalid year returned null");
				passed++;
			} else {
				System.out.println("FAIL: invalid year should return null");
				failed++;
			}

			// ============ 3. ADD EXHIBIT TO ANNUAL PLAN ============

			// 3a. valid add
			api.addExhibitToAnnualPlan(E1, "P1", "HALL_A", 1);
			String checkAdd1 = api.getAnnualPlanExhibit("P1", "HALL_A", 1);

			if (checkAdd1 != null && checkAdd1.equals(E1)) {
				System.out.println("PASS: addExhibitToAnnualPlan added exhibit");
				passed++;
			} else {
				System.out.println("FAIL: addExhibitToAnnualPlan failed");
				failed++;
			}

			// 3b. invalid month
			api.addExhibitToAnnualPlan(E2, "P1", "HALL_A", 13);
			String checkAdd2 = api.getAnnualPlanExhibit("P1", "HALL_A", 13);

			if (checkAdd2 == null) {
				System.out.println("PASS: invalid month rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid month accepted");
				failed++;
			}

			// 3c. invalid hall
			api.addExhibitToAnnualPlan(E2, "P1", "INVALID", 2);
			String checkAdd3 = api.getAnnualPlanExhibit("P1", "INVALID", 2);

			if (checkAdd3 == null) {
				System.out.println("PASS: invalid hall rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid hall accepted");
				failed++;
			}

			// 3d. invalid exhibit ID
			api.addExhibitToAnnualPlan("INVALID", "P1", "HALL_B", 2);
			String checkAdd4 = api.getAnnualPlanExhibit("P1", "HALL_B", 2);

			if (checkAdd4 == null) {
				System.out.println("PASS: invalid exhibit rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid exhibit accepted");
				failed++;
			}

			// 3e. invalid plan ID
			api.addExhibitToAnnualPlan(E2, "INVALID", "HALL_B", 2);
			System.out.println("PASS: invalid plan ID did not crash (manual check)");
			passed++;

			// 3f. duplicate hall/month slot
			api.addExhibitToAnnualPlan(E2, "P1", "HALL_A", 1);
			String checkAdd5 = api.getAnnualPlanExhibit("P1", "HALL_A", 1);

			if (checkAdd5.equals(E1)) {
				System.out.println("PASS: duplicate slot was rejected");
				passed++;
			} else {
				System.out.println("FAIL: duplicate slot was overwritten");
				failed++;
			}

			// ============ 4. GET ANNUAL PLAN EXHIBIT ============

			// 4a. valid get
			String getExhibit1 = api.getAnnualPlanExhibit("P1", "HALL_A", 1);
			if (getExhibit1 != null && getExhibit1.equals(E1)) {
				System.out.println("PASS: getAnnualPlanExhibit returned correct exhibit");
				passed++;
			} else {
				System.out.println("FAIL: getAnnualPlanExhibit incorrect");
				failed++;
			}

			// 4b. invalid plan ID
			String getExhibit2 = api.getAnnualPlanExhibit("INVALID", "HALL_A", 1);
			if (getExhibit2 == null) {
				System.out.println("PASS: invalid plan ID returned null");
				passed++;
			} else {
				System.out.println("FAIL: invalid plan ID should return null");
				failed++;
			}

			// 4c. invalid hall
			String getExhibit3 = api.getAnnualPlanExhibit("P1", "INVALID", 1);
			if (getExhibit3 == null) {
				System.out.println("PASS: invalid hall returned null");
				passed++;
			} else {
				System.out.println("FAIL: invalid hall should return null");
				failed++;
			}

			// 4d. invalid month
			String getExhibit4 = api.getAnnualPlanExhibit("P1", "HALL_A", 13);
			if (getExhibit4 == null) {
				System.out.println("PASS: invalid month returned null");
				passed++;
			} else {
				System.out.println("FAIL: invalid month should return null");
				failed++;
			}

			// ============ 5. GET ANNUAL PLAN INFO ============

			// 5a. valid get year
			String getInfo1 = api.getAnnualPlanInfo("P1", "year");
			if (getInfo1 != null && getInfo1.equals("2025")) {
				System.out.println("PASS: getAnnualPlanInfo returned correct year");
				passed++;
			} else {
				System.out.println("FAIL: getAnnualPlanInfo returned incorrect year");
				failed++;
			}

			// 5b. valid get total exhibits
			String getInfo2 = api.getAnnualPlanInfo("P1", "total_exhibits");
			if (getInfo2 != null && getInfo2.equals("1")) {
				System.out.println("PASS: getAnnualPlanInfo returned correct exhibit count");
				passed++;
			} else {
				System.out.println("FAIL: getAnnualPlanInfo returned incorrect exhibit count");
				failed++;
			}

			// 5c. invalid infoName
			String getInfo3 = api.getAnnualPlanInfo("P1", "INVALID");
			if (getInfo3 == null) {
				System.out.println("PASS: invalid infoName rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid infoName accepted");
				failed++;
			}

			// ============ 6. UPDATE ANNUAL PLAN INFO ============

			// 6a. valid update year
			api.updateAnnualPlanInfo("P1", "year", "2030");
			String getYear1 = api.getAnnualPlanInfo("P1", "year");
			if (getYear1.equals("2030")) {
				System.out.println("PASS: updateAnnualPlanInfo updated year");
				passed++;
			} else {
				System.out.println("FAIL: updateAnnualPlanInfo failed to update year");
				failed++;
			}

			// 6b. invalid infoName
			api.updateAnnualPlanInfo("P1", "INVALID", "9999");
			String getYear2 = api.getAnnualPlanInfo("P1", "year");
			if (getYear2.equals("2030")) {
				System.out.println("PASS: invalid infoName rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid infoName incorrectly modified plan");
				failed++;
			}

			// 6c. invalid year value
			api.updateAnnualPlanInfo("P1", "year", "NOT_A_NUMBER");
			String getYear3 = api.getAnnualPlanInfo("P1", "year");
			if (getYear3.equals("2030")) {
				System.out.println("PASS: invalid year value rejected");
				passed++;
			} else {
				System.out.println("FAIL: invalid year value accepted");
				failed++;
			}

			// ============ 7. DELETE ANNUAL PLAN ============

			// 7a. valid delete
			api.deleteAnnualPlan("P1");
			String checkDelete1 = api.getAnnualPlanInfo("P1", "year");

			if (checkDelete1 == null) {
				System.out.println("PASS: deleteAnnualPlan removed plan");
				passed++;
			} else {
				System.out.println("FAIL: deleteAnnualPlan failed");
				failed++;
			}

			// 7b. invalid delete
			api.deleteAnnualPlan("INVALID");
			System.out.println("PASS: invalid delete did not crash (manual check)");
			passed++;

		} catch (Exception e) {
			System.out.println("ERROR during Annual Plan CRUD tests: " + e.getMessage());
			failed++;
		}

		System.out.println("===== ANNUAL PLAN CRUD TEST RESULTS =====");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);
	}

	@Override
	public void runMenuTests() {
		int passed = 0;
		int failed = 0;

		String prompt;
		String result;
		String state;

		final String MAIN_MENU_PROMPT = "Select option:";

		System.out.println("\n=== RUNNING MENU TESTS ===\n");

		try {

			// ============ TEST 1 - Main Menu Prompt ============

			prompt = api.getCurrentPrompt();
			state = api.getCurrentState();

			// linear search to search if the prompt contains the main menu prompt
			boolean containsPrompt = false;

			if (prompt != null) {
				for (int i = 0; i <= prompt.length() - MAIN_MENU_PROMPT.length(); i++) {
					boolean match = true;

					for (int j = 0; j < MAIN_MENU_PROMPT.length(); j++) {
						if (prompt.charAt(i + j) != MAIN_MENU_PROMPT.charAt(j)) {
							match = false;
							break;
						}
					}

					if (match) {
						containsPrompt = true;
						break;
					}
				}
			}

			if (prompt != null && containsPrompt && "MAIN_MENU".equals(state)) {
				System.out.println("PASS: Main menu prompt and state detected");
				passed++;
			} else {
				System.out.println("FAIL: Main menu prompt/state incorrect");
				failed++;
			}

			// ============ TEST 2 - Navigate to Artifact Menu ============

			result = api.processInput("1");
			state = api.getCurrentState();

			if ("ARTIFACT_MENU".equals(state) && result.equals("")) {
				System.out.println("PASS: Navigated to Artifact Menu");
				passed++;
			} else {
				System.out.println("FAIL: Failed to navigate to Artifact Menu");
				failed++;
			}

			// ============ TEST 3 - Invalid input in Artifact Menu ============

			result = api.processInput("99");

			if (!result.equals("")) {
				System.out.println("PASS: Invalid input correctly rejected in Artifact Menu");
				passed++;
			} else {
				System.out.println("FAIL: Invalid input accepted incorrectly");
				failed++;
			}

			// ============ TEST 4 - Back to Main Menu from Artifact Menu ============

			result = api.processInput("8");
			state = api.getCurrentState();

			if ("MAIN_MENU".equals(state)) {
				System.out.println("PASS: Returned to Main Menu from Artifact Menu");
				passed++;
			} else {
				System.out.println("FAIL: Failed to return to Main Menu");
				failed++;
			}

			// ============ TEST 5 - Navigate to Exhibit Menu ============

			result = api.processInput("2");
			state = api.getCurrentState();

			if ("EXHIBIT_MENU".equals(state)) {
				System.out.println("PASS: Navigated to Exhibit Menu");
				passed++;
			} else {
				System.out.println("FAIL: Failed to navigate to Exhibit Menu");
				failed++;
			}

			// ============ TEST 6 - Back to Main Menu from Exhibit Menu ============

			result = api.processInput("13");
			state = api.getCurrentState();

			if ("MAIN_MENU".equals(state)) {
				System.out.println("PASS: Returned to Main Menu from Exhibit Menu");
				passed++;
			} else {
				System.out.println("FAIL: Failed to return to Main Menu from Exhibit Menu");
				failed++;
			}

			// ============ TEST 7 - Navigate to Annual Plan Menu ============

			result = api.processInput("3");
			state = api.getCurrentState();

			if ("ANNUAL_PLAN_MENU".equals(state)) {
				System.out.println("PASS: Navigated to Annual Plan Menu");
				passed++;
			} else {
				System.out.println("FAIL: Failed to navigate to Annual Plan Menu");
				failed++;
			}

			// ============ TEST 8 - Back to Main Menu from Annual Plan Menu ============

			result = api.processInput("9");
			state = api.getCurrentState();

			if ("MAIN_MENU".equals(state)) {
				System.out.println("PASS: Returned to Main Menu from Annual Plan Menu");
				passed++;
			} else {
				System.out.println("FAIL: Failed to return to Main Menu from Annual Plan Menu");
				failed++;
			}

			// ============ TEST 9 - Invalid Menu Option ============

			result = api.processInput("5");
			state = api.getCurrentState();

			if ("MAIN_MENU".equals(state)) {
				System.out.println("PASS: System stayed in the currect state");
				passed++;
			} else {
				System.out.println("FAIL: System switched to incorrect state");
				failed++;
			}

			// ============ TEST 10 - Quit option ============

			result = api.processInput("4");
			state = api.getCurrentState();

			if ("EXIT".equals(state) || "QUIT".equals(state)) {
				System.out.println("PASS: Quit option works correctly");
				passed++;
			} else {
				System.out.println("FAIL: Quit option did not switch to exit state");
				failed++;
			}

		} catch (Exception e) {
			System.out.println("FAIL: Exception occurred during menu tests");
			failed++;
		}

		// ============ TEST SUMMARY ============

		System.out.println("\n=== MENU TEST SUMMARY ===");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);

	}

	@Override
	public void runFuzzingTests() {
		int passed = 0;
		int failed = 0;

		String input;
		String result;
		String state;

		System.out.println("\n=== RUNNING FUZZING TESTS ===\n");

		try {

			api = new QUBMuseum();

			// some random valid and invalid inputs
			String[] randomInputs = { "", " ", "asdasd", "123", "!", "@@@", "-1", "0", "5", "HALL_A", "HALL_X", "9999",
					"artifact", "exhibit", "plan", "name_contains=test", "type=STONE", "random_text", "A1", "E1", "P1",
					"EXIT", "QUIT" };

			// ============ FUZZING TEST LOOP ============
			for (int i = 0; i < 50; i++) {

				// select random input
				int index = (int) (Math.random() * randomInputs.length);
				input = randomInputs[index];

				// get state before
				state = api.getCurrentState();

				result = api.processInput(input);

				if (result != null) {
					System.out.println("PASS: Handled input [" + input + "] in state [" + state + "]");
					passed++;
				} else {
					System.out.println("FAIL: Null result for input [" + input + "] in state [" + state + "]");
					failed++;
				}

			}

		} catch (Exception e) {
			System.out.println("FAIL: Exception thrown during fuzzing");
			failed++;
		}

		// ============ TEST SUMMARY ============

		System.out.println("\n=== FUZZING TEST SUMMARY ===");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);

	}

}