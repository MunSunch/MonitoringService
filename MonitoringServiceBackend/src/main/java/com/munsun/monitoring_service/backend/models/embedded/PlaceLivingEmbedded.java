package com.munsun.monitoring_service.backend.models.embedded;

/**
 * An embedded object that contains information about the user's place of residence
 */
public class PlaceLivingEmbedded {
    private String country;
    private String city;
    private String street;
    private String house;
    private String level;
    private String apartmentNumber;

    public PlaceLivingEmbedded() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceLivingEmbedded that = (PlaceLivingEmbedded) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (house != null ? !house.equals(that.house) : that.house != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        return apartmentNumber != null ? apartmentNumber.equals(that.apartmentNumber) : that.apartmentNumber == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (apartmentNumber != null ? apartmentNumber.hashCode() : 0);
        return result;
    }
}
