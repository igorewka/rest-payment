package eu.isakels.rest.reqresp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationResp {
    private final String country;
    private final String status;

//    @JsonCreator
    public GeoLocationResp(final String country, final String status) {
        this.country = country;
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GeoLocationResp that = (GeoLocationResp) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, status);
    }
}
