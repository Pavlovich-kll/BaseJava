package com.basejava.webapp.model;

import com.basejava.webapp.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link link;
    private List<Position> positions;

    public Company() {
    }

    public Company(String name, String url, Position... positions) {
        Objects.requireNonNull(name, "name shouldn't be null");
        Objects.requireNonNull(url, "url shouldn't be null");
        this.link = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return link.equals(company.link) &&
                positions.equals(company.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        return "Company{" +
                "link=" + link +
                ", positions=" + positions +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        public String filling;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        public YearMonth startDate;

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        public YearMonth endDate;

        public Position() {
        }

        public Position(String filling, YearMonth startDate, YearMonth endDate) {
            Objects.requireNonNull(filling, "filling shouldn't be null");
            Objects.requireNonNull(startDate, "startDate shouldn't be null");
            Objects.requireNonNull(endDate, "endtDate shouldn't be null");
            this.filling = filling;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getFilling() {
            return filling;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return filling.equals(position.filling) &&
                    startDate.equals(position.startDate) &&
                    endDate.equals(position.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(filling, startDate, endDate);
        }
        @Override
        public String toString() {
            return "Position{" +
                    "filling='" + filling + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}