package map.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GeocodedWaypointsItem {
	
	@SerializedName ("types") private List<String> types;
	
	@SerializedName ("geocoder_status") private String geocoderStatus;
	
	@SerializedName ("place_id") private String placeId;
	
	public List<String> getTypes() {
		return types;
	}
	
	public String getGeocoderStatus() {
		return geocoderStatus;
	}
	
	public String getPlaceId() {
		return placeId;
	}
	
}