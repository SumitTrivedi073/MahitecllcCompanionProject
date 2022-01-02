package map.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DirectionResponseModel {
	
	@SerializedName ("routes") private List<RoutesItem> routes;
	
	@SerializedName ("geocoded_waypoints") private List<GeocodedWaypointsItem> geocodedWaypoints;
	
	@SerializedName ("status") private String status;
	
	public List<RoutesItem> getRoutes() {
		return routes;
	}
	
	public List<GeocodedWaypointsItem> getGeocodedWaypoints() {
		return geocodedWaypoints;
	}
	
	public String getStatus() {
		return status;
	}
	
}