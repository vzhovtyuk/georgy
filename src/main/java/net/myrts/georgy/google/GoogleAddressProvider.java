package net.myrts.georgy.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import net.myrts.georgy.api.GeoLocation;
import net.myrts.georgy.google.stubs.GoogleResponse;
import net.myrts.georgy.google.stubs.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Oleksandr Pavlov avpavlov108@gmail.com on 15.11.15.
 */


/**
 * Geocode request URL. Here see we are passing "json" it means we will get
 * the output in JSON format. You can also pass "xml" instead of "json" for
 * XML output. For XML output URL will be
 * "http://maps.googleapis.com/maps/api/geocode/xml";
 *
 */
public class GoogleAddressProvider {

/**
 * Here the fullAddress String is in format like
 * "address,city,state,zipcode". Here address means "street number + route"
 *
 */
    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    private static final Logger LOG = LoggerFactory.getLogger(GoogleAddressProvider.class);

    /**
     * Create an java.net.URL object by passing the request URL in
     * constructor. Here you can see I am converting the fullAddress String
     * in UTF-8 format. You will get Exception if you don't convert your
     * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
     * parameter we also need to pass "sensor" parameter. sensor (required
     * parameter) — Indicates whether or not the geocoding request comes
     * from a device with a location sensor. This value must be either true
     * or false.
     *
     * @param fullAddress String
     */
    public GeoLocation convertToLatLong(String fullAddress)  {

        URL url = null;
        GeoLocation geoLocation = new GeoLocation(0.0,0.0);

        try {
            url = new URL(URL + "?address="
                    + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");

            // Open the Connection
            URLConnection conn = url.openConnection();

            GoogleResponse response;

        try(InputStream in = conn.getInputStream()) {

            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

            // IMPORTANT
            // without this option set adding new fields breaks old code
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            response = mapper.readValue(in,GoogleResponse.class);


          //  GoogleResponse res = new GoogleAddressProvider().convertToLatLong("Apollo Bunder, Mumbai, Maharashtra, India");

            if (response.getStatus().equals("OK")) {
                for (Result result : response.getResults()) {
                    geoLocation = new GeoLocation(
                            result.getGeometry().getLocation().getLat(),
                            result.getGeometry().getLocation().getLng()
                    );
                    LOG.info("Latitude ", result.getGeometry().getLocation().getLat());
                    LOG.info("Longitude ", result.getGeometry().getLocation().getLng());
                    LOG.info("Location type ", result.getGeometry().getLocationType());

                }
            } else {
                LOG.info(response.getStatus());
            }
        }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            LOG.error("MalformedURLException ", e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException ", e);
        } catch (JsonMappingException e) {
            LOG.error("JsonMappingException ", e);
        } catch (JsonParseException e) {
            LOG.error("JsonParseException ", e);
        } catch (IOException e) {
            LOG.error("IOException ", e);
        }

        return geoLocation;
    }

    /**
     * Create an java.net.URL object by passing the request URL in
     * constructor. Here you can see I am converting the fullAddress String
     * in UTF-8 format. You will get Exception if you don't convert your
     * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
     * parameter we also need to pass "sensor" parameter. sensor (required
     * parameter) — Indicates whether or not the geocoding request comes
     * from a device with a location sensor. This value must be either true
     * or false.
     *
     * @param latlongString String
     */
    public GoogleResponse convertFromLatLong(String latLongString) throws IOException {

        URL url = new URL(URL + "?latlng="
                + URLEncoder.encode(latLongString, "UTF-8") + "&sensor=false");

        // Open the Connection
        URLConnection conn = url.openConnection();

        GoogleResponse response;

        try(InputStream in = conn.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

            // IMPORTANT
            // without this option set adding new fields breaks old code
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
            in.close();
        }

        return response;

    }

}