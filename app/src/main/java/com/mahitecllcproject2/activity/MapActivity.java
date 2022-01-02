package com.mahitecllcproject2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mahitecllcproject2.R;
import com.mahitecllcproject2.Utility.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                              GoogleApiClient.ConnectionCallbacks
		, GoogleApiClient.OnConnectionFailedListener {
	
	private static final int REQUEST_CODE_PERMISSION = 2;
	
	private final float cameraZoom = 12f;
	
	GoogleApiClient googleApiClient;
	
	double lat, lon;
	
	LatLng loc;
	
	private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mapFragment =
				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		findViewById(R.id.otherUserBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MapActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		final LocationManager manager =
				(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean gps_enabled = false;
		boolean network_enabled = false;
		try {
			gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}
		if (!gps_enabled && !network_enabled) {
			buildAlertMessageNoGps(this);
		} else {
			checkPermissions();
		}
	}
	
	public static void buildAlertMessageNoGps(Context context) {
		new AlertDialog.Builder(context).setTitle(R.string.Location_permission)  // GPS not found
				.setMessage(R.string.Location_permission_txt) // Want to enable?
				.setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				}).setNegativeButton(R.string.cancel_text, null).show();
	}
	
	private void checkPermissions() {
		if (!(ActivityCompat.checkSelfPermission(this,
				Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) || !(ActivityCompat.checkSelfPermission(
				this,
				Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) || !(ActivityCompat.checkSelfPermission(
				this,
				Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.ACCESS_FINE_LOCATION,
							Manifest.permission.ACCESS_COARSE_LOCATION},
					REQUEST_CODE_PERMISSION);
		} else {
			buildGoogleApiClient();
			Constants.SerialID = getDeviceIMEI();
		}
	}
	
	private void buildGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(
				this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
		googleApiClient.connect();
	}
	
	@SuppressLint ("HardwareIds")
	public String getDeviceIMEI() {
		String deviceUniqueIdentifier = null;
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			deviceUniqueIdentifier =
					Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
		} else {
			final TelephonyManager mTelephony =
					(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			if (mTelephony.getDeviceId() != null) {
				deviceUniqueIdentifier = mTelephony.getDeviceId();
			} else {
				deviceUniqueIdentifier =
						Settings.Secure.getString(getContentResolver(),
								Settings.Secure.ANDROID_ID);
			}
		}
		return deviceUniqueIdentifier;
	}
	
	@Override
	public void onMapReady(@NonNull GoogleMap googleMap) {
		mMap = googleMap;
		if (ActivityCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				this,
				Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		mMap.setMyLocationEnabled(true);
	}
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {
	}
	
	@Override
	public void onConnected(@Nullable Bundle bundle) {
		if (ActivityCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			if (ActivityCompat.checkSelfPermission(this,
					Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
		Location mLastLocation =
				LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
		if (mLastLocation != null) {
			Constants.homeLatitude = mLastLocation.getLatitude();
			Constants.homeLongitude = mLastLocation.getLongitude();
			mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
			// Add a marker in Sydney and move the camera
			LatLng currentLocation = new LatLng(Constants.homeLatitude, Constants.homeLongitude);
			mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, cameraZoom));
		}
	}
	
	@Override
	public void onConnectionSuspended(int i) {
	}
	
	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
	}
	
}