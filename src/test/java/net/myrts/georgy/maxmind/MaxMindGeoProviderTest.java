package net.myrts.georgy.maxmind;

import net.myrts.georgy.api.Address;
import net.myrts.georgy.api.AddressLocation;
import net.myrts.georgy.api.GeoLocation;
import net.myrts.georgy.api.GeorgyException;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Locale;

import static java.util.Locale.SIMPLIFIED_CHINESE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 10/19/15
 *         Time: 5:48 PM
 */
public class MaxMindGeoProviderTest implements net.myrts.georgy.assertLocation {
    @Test
    public void shouldParseLocationByIPFromLocalDB() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        final AddressLocation addressLocation = maxMindGeoProvider.getAddressByIp("128.101.101.101");
        final Address address = addressLocation.getAddress();
        assertEquals("Country iso code does not match " + addressLocation, "US", address.getCountryIsoCode());
        assertEquals("Country does not match " + addressLocation, "United States", address.getCountry());

        assertEquals("State name does not match " + addressLocation, "Minnesota", address.getSubdivision());
        assertEquals("State iso code does not match " + addressLocation, "MN", address.getSubdivisionIsoCode());

        assertEquals("City does not match " + addressLocation, "Minneapolis", address.getCity());

        assertEquals("Postal code does not match " + addressLocation, "55414", address.getPostalCode());
        assertLocation(44.9759d, -93.2166d, addressLocation);
    }

    @Test(expected = GeorgyException.class)
    public void shouldParseLocationByIPNotFound() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        maxMindGeoProvider.getAddressByIp("127.0.0.1");
        fail("Should fail with GeorgyException: The address 127.0.0.1 is not in the database");
    }

    @Test
    public void shouldParseLocationByIPFromLocalDBLocalized() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        final AddressLocation addressLocation = maxMindGeoProvider.getAddressByIp("128.101.101.101", Locale.JAPANESE);
        final Address address = addressLocation.getAddress();
        assertEquals("Country iso code does not match " + addressLocation, "US", address.getCountryIsoCode());
        assertEquals("Country does not match " + addressLocation, "アメリカ合衆国", address.getCountry());

        assertEquals("State name does not match " + addressLocation, "ミネソタ州", address.getSubdivision());
        assertEquals("State iso code does not match " + addressLocation, "MN", address.getSubdivisionIsoCode());

        assertEquals("City does not match " + addressLocation, "ミネアポリス", address.getCity());

        assertEquals("Postal code does not match " + addressLocation, "55414", address.getPostalCode());
        assertLocation(44.9759d, -93.2166d, addressLocation);
    }
    
    @Test
    public void shouldParseLocationByIPFromLocalDBLocalizedNotExists() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        final AddressLocation addressLocation = maxMindGeoProvider.getAddressByIp("128.101.101.101", SIMPLIFIED_CHINESE);
        final Address address = addressLocation.getAddress();
        assertEquals("Country iso code does not match " + addressLocation, "US", address.getCountryIsoCode());
        assertEquals("Country does not match " + addressLocation, "美国", address.getCountry());

        assertEquals("State name does not match " + addressLocation, "Minnesota", address.getSubdivision());
        assertEquals("State iso code does not match " + addressLocation, "MN", address.getSubdivisionIsoCode());

        assertEquals("City does not match " + addressLocation, "明尼阿波利斯", address.getCity());

        assertEquals("Postal code does not match " + addressLocation, "55414", address.getPostalCode());

        assertLocation(44.9759d, -93.2166d, addressLocation);
    }

    @Override
    public void assertLocation(Double latitude, Double longitude, GeoLocation geoLocation) {

    }

    @Override
    public void assertLocation(Double latitude, Double longitude, AddressLocation addressLocation) {
        final GeoLocation geoLocation = addressLocation.getLocation();
        assertEquals("Latitude does not match " + addressLocation, latitude, geoLocation.getLatitude(), 0.00001);
        assertEquals("Longitude does not match for " + addressLocation, longitude, geoLocation.getLongitude(), 0.00001);
    }
}
