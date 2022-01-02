package map.model;

import com.google.gson.annotations.SerializedName;


public class StepsItem {
	
	@SerializedName ("duration") private Duration duration;
	
	@SerializedName ("start_location") private StartLocation startLocation;
	
	@SerializedName ("distance") private Distance distance;
	
	@SerializedName ("travel_mode") private String travelMode;
	
	@SerializedName ("html_instructions") private String htmlInstructions;
	
	@SerializedName ("end_location") private EndLocation endLocation;
	
	@SerializedName ("maneuver") private String maneuver;
	
	@SerializedName ("polyline") private Polyline polyline;
	
	public Duration getDuration() {
		return duration;
	}
	
	public StartLocation getStartLocation() {
		return startLocation;
	}
	
	public Distance getDistance() {
		return distance;
	}
	
	public String getTravelMode() {
		return travelMode;
	}
	
	public String getHtmlInstructions() {
		return htmlInstructions;
	}
	
	public EndLocation getEndLocation() {
		return endLocation;
	}
	
	public String getManeuver() {
		return maneuver;
	}
	
	public Polyline getPolyline() {
		return polyline;
	}
	
}