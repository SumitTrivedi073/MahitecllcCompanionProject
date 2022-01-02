package map.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RoutesItem {
	
	@SerializedName ("summary") private String summary;
	
	@SerializedName ("copyrights") private String copyrights;
	
	@SerializedName ("legs") private List<LegsItem> legs;
	
	@SerializedName ("warnings") private List<Object> warnings;
	
	@SerializedName ("bounds") private Bounds bounds;
	
	@SerializedName ("overview_polyline") private OverviewPolyline overviewPolyline;
	
	@SerializedName ("waypoint_order") private List<Object> waypointOrder;
	
	public String getSummary() {
		return summary;
	}
	
	public String getCopyrights() {
		return copyrights;
	}
	
	public List<LegsItem> getLegs() {
		return legs;
	}
	
	public List<Object> getWarnings() {
		return warnings;
	}
	
	public Bounds getBounds() {
		return bounds;
	}
	
	public OverviewPolyline getOverviewPolyline() {
		return overviewPolyline;
	}
	
	public List<Object> getWaypointOrder() {
		return waypointOrder;
	}
	
}