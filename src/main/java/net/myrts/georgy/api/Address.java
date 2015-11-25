package net.myrts.georgy.api;

/**
 * Container for address.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 10/19/15
 *         Time: 7:38 PM
 */
public class Address {
    private String country;
    private String countryIsoCode;
    private String city;
    private String postalCode;
    private String subdivision;
    private String subdivisionIsoCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getSubdivisionIsoCode() {
        return subdivisionIsoCode;
    }

    public void setSubdivisionIsoCode(String subdivisionIsoCode) {
        this.subdivisionIsoCode = subdivisionIsoCode;
    }

    @Override
    public String toString() {
        return "AddressLocation{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", subdivision='" + subdivision + '\'' +
                ", subdivisionIsoCode='" + subdivisionIsoCode + '\'' +
                '}';
    }
}
