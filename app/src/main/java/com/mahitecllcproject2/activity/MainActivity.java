package com.mahitecllcproject2.activity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mahitecllcproject2.FirebaseLocationModel;
import com.mahitecllcproject2.R;
import com.mahitecllcproject2.Utility.AppController;
import com.mahitecllcproject2.Utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import map.DoubleArrayEvaluator;
import map.model.DirectionResponseModel;
import map.model.LegsItem;
import map.model.RoutesItem;
import map.model.StepsItem;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
	
	private static final String currentLocation = "current_locations";
	
	private final float cameraZoom = 12f;
	
	private GoogleMap mMap;
	
	private LatLng originLocation;
	
	private Marker companionMarker;
	
	private PolylineOptions lineOptions;
	
	private TextView estimateTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mapFragment =
				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		listenForLocationChange();
	}
	
	public void listenForLocationChange() {
		DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
		DatabaseReference reference =
				firebaseDatabase.child(currentLocation).child(Constants.SerialID);
		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				FirebaseLocationModel locationModel =
						snapshot.getValue(FirebaseLocationModel.class);
				Log.e("onDataChange", locationModel.toString());
				LatLng dest = new LatLng(locationModel.latitude, locationModel.longitude);
				Location sourceLocation = new Location("source");
				sourceLocation.setLatitude(originLocation.latitude);
				sourceLocation.setLongitude(originLocation.longitude);
				Location endPoint = new Location("destination");
				endPoint.setLatitude(dest.latitude);
				endPoint.setLongitude(dest.longitude);
					companionMarker = mMap.addMarker(
							new MarkerOptions().position(dest).title("Other User Location"));
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			}
		});
	}
	
	private void animateMarker(Marker marker, LatLng destLatLng) {
		double[] startValues =
				new double[]{marker.getPosition().latitude, marker.getPosition().longitude};
		double[] endValues = new double[]{destLatLng.latitude, destLatLng.longitude};
		ValueAnimator latLngAnimator =
				ValueAnimator.ofObject(new DoubleArrayEvaluator(), startValues, endValues);
		latLngAnimator.setDuration(600);
		latLngAnimator.setInterpolator(new DecelerateInterpolator());
		latLngAnimator.addUpdateListener(animation -> {
			double[] animatedValue = (double[]) animation.getAnimatedValue();
			marker.setPosition(new LatLng(animatedValue[0], animatedValue[1]));
		});
		latLngAnimator.start();
	}
	
	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
	
	@Override
	public void onMapReady(@NonNull GoogleMap googleMap) {
		mMap = googleMap;
		mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
		if (Constants.homeLatitude != 0.0 && Constants.homeLongitude != 0.0) {
			originLocation = new LatLng(Constants.homeLatitude, Constants.homeLongitude);
			mMap.addMarker(new MarkerOptions().position(originLocation).title("Your Location"));
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, cameraZoom));
		}
	}
	
	
}