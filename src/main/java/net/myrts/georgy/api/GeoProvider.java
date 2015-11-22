package net.myrts.georgy.api;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;

/**
 * Geo provider base interface.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 10/19/15
 *         Time: 7:56 PM
 */
public interface GeoProvider {
    AddressLocation getAddressByIp(String inetAddress, Locale locale) throws IOException, GeoIp2Exception, GeorgyException;

    AddressLocation getAddressByIp(String byName) throws GeorgyException;
}
