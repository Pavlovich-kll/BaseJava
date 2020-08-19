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

    public static Company empty = new Company("", "", Position.empty);

    private Link link;
    private List<Position> positions;

    public Company() {
    }

    public Company(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Company(Link link, List<Position> positions) {
        this.link = link;
        this.positions = positions;
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
        public String position;
        public String description;
        public static Position empty = new Position();

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        public YearMonth startDate;

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        public YearMonth endDate;

        public Position() {
        }

        public Position(String position, String description, YearMonth startDate, YearMonth endDate) {
            Objects.requireNonNull(position, "title shouldn't be null");
            Objects.requireNonNull(startDate, "startDate shouldn't be null");
            Objects.requireNonNull(endDate, "endDate shouldn't be null");
            this.position = position;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
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
            return this.position.equals(position.position) &&
                    description != null ? description.equals(position.description) : position.description == null &&
                    startDate.equals(position.startDate) &&
                    endDate.equals(position.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, description, startDate, endDate);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "position='" + position + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}