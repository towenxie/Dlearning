package org.towen.deeplearning.data.geo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Location")
public class LocationModel {

    private List<CountryRegion> countryRegions;

    @XmlElement(name = "CountryRegion")
    public List<CountryRegion> getCountryRegions() {
        return countryRegions;
    }

    public void setCountryRegions(List<CountryRegion> countryRegions) {
        this.countryRegions = countryRegions;
    }


    public static class CountryRegion {
        private String name;

        private String code;

        private List<State> states;

        @XmlAttribute(name = "Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlAttribute(name = "Code")
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @XmlElement(name = "State")
        public List<State> getStates() {
            return states;
        }

        public void setStates(List<State> states) {
            this.states = states;
        }
    }

    public static class State {
        private String name;

        private String code;

        private List<City> cities;

        @XmlAttribute(name = "Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlAttribute(name = "Code")
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @XmlElement(name = "City")
        public List<City> getCities() {
            return cities;
        }

        public void setCities(List<City> cities) {
            this.cities = cities;
        }
    }

    public static class City {
        private String name;

        private String code;

        private List<Region> regions;

        @XmlAttribute(name = "Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlAttribute(name = "Code")
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @XmlElement(name = "Region")
        public List<Region> getRegions() {
            return regions;
        }

        public void setRegions(List<Region> regions) {
            this.regions = regions;
        }
    }
    public static class Region {
        private String name;

        private String code;

        @XmlAttribute(name = "Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlAttribute(name = "Code")
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
