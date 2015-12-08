package net.myrts.georgy.google;

import net.myrts.georgy.api.GeoLocation;
import net.myrts.georgy.google.GoogleAddressProvider;
import net.myrts.georgy.google.stubs.GoogleResponse;
import net.myrts.georgy.google.stubs.Result;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Created by Oleksandr Pavlov avpavlov108@gmail.com on 15.11.15.
 */
public class GoogleGeoProviderTest implements net.myrts.georgy.assertLocation {

    @Override
    public void assertLocation(Double latitude, Double longitude, GeoLocation geoLocation) {
        assertEquals(latitude, geoLocation.getLatitude(), 0.00001);
        assertEquals(longitude, geoLocation.getLongitude(), 0.00001);
    }

    @Override
    public void assertLocation(Double latitude, Double longitude, AddressLocation addressLocation) {

    }

    @Test
    public void shouldConvertAddressToLatLong() throws IOException {
        final String address = "Apollo Bunder, Mumbai, Maharashtra, India";
        final double dLat =18.9203886d;
        final double dLon =72.8301305d;
        final GeoLocation geoLocation = new GoogleAddressProvider().convertToLatLong(address);
        assertLocation(dLat, dLon, geoLocation);
    }

    @Test
    public void shouldConvertLatLongToAddress() throws IOException {

        GoogleResponse res1 = new GoogleAddressProvider().convertFromLatLong("18.92038860,72.83013059999999");
        if (res1.getStatus().equals("OK")) {
                List<Result> result = res1.getResults();

                // address is
                assertEquals("1218, Shahid Bhagat Singh Marg, Apollo Bandar, Colaba, Mumbai, Maharashtra 400001, India", result.get(0).getFormattedAddress());

                // address is
                assertEquals("Colaba Depot, Apollo Bandar, Colaba, Mumbai, Maharashtra 400001", result.get(1).getFormattedAddress());

                // address is
                assertEquals("Cusrow Baug Colony, Apollo Bandar, Colaba, Mumbai, Maharashtra, India", result.get(2).getFormattedAddress());

                // address is
                assertEquals("Apollo Bandar, Colaba, Mumbai, Maharashtra, India", result.get(3).getFormattedAddress());

                // address is
                assertEquals("Colaba, Mumbai, Maharashtra, India", result.get(4).getFormattedAddress());

                // address is
                assertEquals("Mumbai, Maharashtra, India", result.get(5).getFormattedAddress());

                // address is
                assertEquals("Mumbai, Maharashtra 400001, India", result.get(6).getFormattedAddress());

                // address is
                assertEquals("Mumbai, Maharashtra, India", result.get(7).getFormattedAddress());

                // address is
                assertEquals("Maharashtra, India", result.get(8).getFormattedAddress());

                // address is
                assertEquals("India", result.get(9).getFormattedAddress());

        } else {
            System.out.println(res1.getStatus());
        }

    }

}
