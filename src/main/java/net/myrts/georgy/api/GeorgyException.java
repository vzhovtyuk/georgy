package net.myrts.georgy.api;

import com.maxmind.geoip2.exception.GeoIp2Exception;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 10/19/15
 *         Time: 8:19 PM
 */
public class GeorgyException extends Exception {
    public GeorgyException(String message, Exception e) {
        super(message, e);
    }
}
