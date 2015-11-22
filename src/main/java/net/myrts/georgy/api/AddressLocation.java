package net.myrts.georgy.api;

/**
 * Container for address and location.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 10/19/15
 *         Time: 7:38 PM
 */
public class AddressLocation {
    private Address  address;
    private GeoLocation location;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AddressLocation{");
        sb.append("address=").append(address);
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}
