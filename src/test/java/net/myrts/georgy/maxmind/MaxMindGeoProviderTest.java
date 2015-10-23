package net.myrts.georgy.maxmind;

import net.myrts.georgy.api.AddressLocation;
import net.myrts.georgy.api.GeorgyException;
import org.junit.Test;

import java.net.InetAddress;
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
public class MaxMindGeoProviderTest {
    @Test
    public void shouldParseLocationByIPFromLocalDB() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        final AddressLocation addressLocation = maxMindGeoProvider.getAddressByIp(InetAddress.getByName("128.101.101.101"));
        assertEquals("Country iso code does not match " + addressLocation, "US", addressLocation.getCountryIsoCode());
        assertEquals("Country does not match " + addressLocation, "United States", addressLocation.getCountry());

        assertEquals("State name does not match " + addressLocation, "Minnesota", addressLocation.getSubdivision());
        assertEquals("State iso code does not match " + addressLocation, "MN", addressLocation.getSubdivisionIsoCode());

        assertEquals("City does not match " + addressLocation, "Minneapolis", addressLocation.getCity());

        assertEquals("Postal code does not match " + addressLocation, "55414", addressLocation.getPostalCode());

        assertEquals("Latitude does not match " + addressLocation, 44.9759d, addressLocation.getLatitude(), 0.00001);
        assertEquals("Longitude does not match for " + addressLocation, -93.2166d, addressLocation.getLongitude(), 0.00001);
    }

    @Test(expected = GeorgyException.class)
    public void shouldParseLocationByIPNotFound() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        maxMindGeoProvider.getAddressByIp(InetAddress.getByName("127.0.0.1"));
        fail("Should fail with GeorgyException: The address 127.0.0.1 is not in the database");
    }

    @Test
    public void shouldParseLocationByIPFromLocalDBLocalized() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        final AddressLocation addressLocation = maxMindGeoProvider.getAddressByIp(InetAddress.getByName("128.101.101.101"), Locale.JAPANESE);
        assertEquals("Country iso code does not match " + addressLocation, "US", addressLocation.getCountryIsoCode());
        assertEquals("Country does not match " + addressLocation, "アメリカ合衆国", addressLocation.getCountry());

        assertEquals("State name does not match " + addressLocation, "ミネソタ州", addressLocation.getSubdivision());
        assertEquals("State iso code does not match " + addressLocation, "MN", addressLocation.getSubdivisionIsoCode());

        assertEquals("City does not match " + addressLocation, "ミネアポリス", addressLocation.getCity());

        assertEquals("Postal code does not match " + addressLocation, "55414", addressLocation.getPostalCode());

        assertEquals("Latitude does not match " + addressLocation, 44.9759d, addressLocation.getLatitude(), 0.00001);
        assertEquals("Longitude does not match for " + addressLocation, -93.2166d, addressLocation.getLongitude(), 0.00001);
    }
    
    @Test
    public void shouldParseLocationByIPFromLocalDBLocalizedNotExists() throws UnknownHostException, GeorgyException {
        final MaxMindGeoProvider maxMindGeoProvider = new MaxMindGeoProvider();
        final AddressLocation addressLocation = maxMindGeoProvider.getAddressByIp(InetAddress.getByName("128.101.101.101"), SIMPLIFIED_CHINESE);
        assertEquals("Country iso code does not match " + addressLocation, "US", addressLocation.getCountryIsoCode());
        assertEquals("Country does not match " + addressLocation, "美国", addressLocation.getCountry());

        assertEquals("State name does not match " + addressLocation, "Minnesota", addressLocation.getSubdivision());
        assertEquals("State iso code does not match " + addressLocation, "MN", addressLocation.getSubdivisionIsoCode());

        assertEquals("City does not match " + addressLocation, "明尼阿波利斯", addressLocation.getCity());

        assertEquals("Postal code does not match " + addressLocation, "55414", addressLocation.getPostalCode());

        assertEquals("Latitude does not match " + addressLocation, 44.9759d, addressLocation.getLatitude(), 0.00001);
        assertEquals("Longitude does not match for " + addressLocation, -93.2166d, addressLocation.getLongitude(), 0.00001);
    }

}
