package net.myrts.georgy.maxmind;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;
import net.myrts.georgy.api.AddressLocation;
import net.myrts.georgy.api.GeoProvider;
import net.myrts.georgy.api.GeorgyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 10/19/15
 *         Time: 5:48 PM
 */
public class MaxMindGeoProvider implements GeoProvider {
    private static final Logger LOG = LoggerFactory.getLogger(MaxMindGeoProvider.class);
    
    private static final String IP_DB_PATH = "GeoLite2-City.mmdb";

    @Override
    public AddressLocation getAddressByIp(InetAddress inetAddress, Locale locale) throws GeorgyException {
        final InputStream inputStream = getClass().getResourceAsStream(IP_DB_PATH);
        final DatabaseReader reader;

        try {
            reader = new DatabaseReader.Builder(inputStream).build();

            final CityResponse response = reader.city(inetAddress);

            final Country country = response.getCountry();
            final Subdivision subdivision = response.getMostSpecificSubdivision();
            final City city = response.getCity();
            final Postal postal = response.getPostal();
            final Location location = response.getLocation();
            final AddressLocation addressLocation = new AddressLocation();
            if (locale != null) {
                final String localeName = getLocaleString(locale);
                addressLocation.setCountry(getByKeyOrDefault(country.getNames(), localeName, country.getName()));
                addressLocation.setSubdivision(getByKeyOrDefault(subdivision.getNames(), localeName, subdivision.getName()));
                addressLocation.setCity(getByKeyOrDefault(city.getNames(), localeName, city.getName()));
            } else {
                addressLocation.setCountry(country.getName());
                addressLocation.setSubdivision(subdivision.getName());
                addressLocation.setCity(city.getName());
            }
            addressLocation.setCountryIsoCode(country.getIsoCode());
            addressLocation.setSubdivisionIsoCode(subdivision.getIsoCode());
            addressLocation.setPostalCode(postal.getCode());
            addressLocation.setLatitude(location.getLatitude());
            addressLocation.setLongitude(location.getLongitude());
            return addressLocation;
        } catch (IOException e) {
            LOG.error("Failed to find ip database " + IP_DB_PATH, e);
            throw new GeorgyException(e.getMessage(), e);
        } catch (GeoIp2Exception e) {
            LOG.error("Failed to query location", e);
            throw new GeorgyException(e.getMessage(), e);
        }
    }

    @Override
    public AddressLocation getAddressByIp(InetAddress byName) throws GeorgyException {
        return getAddressByIp(byName, null);
    }

    private String getByKeyOrDefault(Map<String, String> map, String key, String defaultValue) {
        if(map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

    private String getLocaleString(Locale locale) {
        if(locale == null) {
            throw new IllegalArgumentException("Locale is null");
        }
        String localeStr = locale.getLanguage();
        if(locale.getCountry() != null 
                && !locale.getCountry().isEmpty()) {
            localeStr += '-' + locale.getCountry();
        }
        return  localeStr;
    }
}
