package net.myrts.georgy.api;

/**
 * Location coordinates container.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 11/22/15
 *         Time: 4:44 PM
 */
public class GeoLocation {
    private final Double latitude;
    private final Double longitude;

    public GeoLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Location{");
        sb.append("latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append('}');
        return sb.toString();
    }
}
