package net.myrts.georgy.maxmind;

import net.myrts.georgy.google.AddressConverter;
import net.myrts.georgy.google.stubs.GoogleResponse;
import net.myrts.georgy.google.stubs.Result;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by georgy on 01.12.15.
 */
public class GoogleGeoProviderTest {

    @Test
    public void convertAddressToLatLong() throws IOException {
        GoogleResponse res = new AddressConverter().convertToLatLong("Apollo Bunder, Mumbai, Maharashtra, India");
        if (res.getStatus().equals("OK")) {
            for (Result result : res.getResults()) {
                //Lattitude of address is
                String strLat ="18.9203886";
                assertTrue(result.getGeometry().getLocation().getLat() > 18);

                String strLon ="72.83013059999999";
                //Longitude of address is
                assertTrue(result.getGeometry().getLocation().getLng() >72);

                //Location is
                assertEquals("APPROXIMATE", result.getGeometry().getLocationType());
            }
        } else {
            System.out.println(res.getStatus());
        }

    }

    @Test
    public void convertLatLongToAddress() throws IOException {

        GoogleResponse res1 = new AddressConverter().convertFromLatLong("18.92038860,72.83013059999999");
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
