package map.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LegsItem {
	
	@SerializedName ("duration") private Duration duration;
	
	@SerializedName ("start_location") private StartLocation startLocation;
	
	@SerializedName ("distance") private Distance distance;
	
	@SerializedName ("start_address") private String startAddress;
	
	@SerializedName ("end_location") private EndLocation endLocation;
	
	@SerializedName ("end_address") private String endAddress;
	
	@SerializedName ("via_waypoint") private List<Object> viaWaypoint;
	
	@SerializedName ("steps") private List<StepsItem> steps;
	
	@SerializedName ("traffic_speed_entry") private List<Object> trafficSpeedEntry;
	
	public Duration getDuration() {
		return duration;
	}
	
	public StartLocation getStartLocation() {
		return startLocation;
	}
	
	public Distance getDistance() {
		return distance;
	}
	
	public String getStartAddress() {
		return startAddress;
	}
	
	public EndLocation getEndLocation() {
		return endLocation;
	}
	
	public String getEndAddress() {
		return endAddress;
	}
	
	public List<Object> getViaWaypoint() {
		return viaWaypoint;
	}
	
	public List<StepsItem> getSteps() {
		return steps;
	}
	
	public List<Object> getTrafficSpeedEntry() {
		return trafficSpeedEntry;
	}
	
}