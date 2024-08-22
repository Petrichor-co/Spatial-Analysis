package lineBuffer;

public class LatLng {
	public double latitude = 0.0;
	public double Longitude = 0.0;

	public LatLng(double latitude, double longitude) {
		this.latitude = latitude;
		Longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

}
