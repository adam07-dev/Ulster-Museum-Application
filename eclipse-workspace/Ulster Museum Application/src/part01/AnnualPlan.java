package part01;

import java.util.HashMap;

public class AnnualPlan {

	private String planId;
	private int year;

	private HashMap<String, String[]> schedule;

	public AnnualPlan(String planId, int year) {
		this.planId = planId;
		this.year = year;

		this.schedule = new HashMap<>();
		schedule.put("HALL_A", new String[12]);
		schedule.put("HALL_B", new String[12]);
		schedule.put("HALL_C", new String[12]);
	}

	public String getPlanId() {
		return planId;
	}

	public int getYear() {
		return year;
	}

	public HashMap<String, String[]> getSchedule() {
		return schedule;
	}

	public String getExhibitFor(String hall, int month) {
		return schedule.get(hall)[month - 1];
	}

	public void setExhibitFor(String hall, int month, String exhibitId) {
		schedule.get(hall)[month - 1] = exhibitId;
	}

	public void setYear(int year) {
		this.year = year;
	}

}