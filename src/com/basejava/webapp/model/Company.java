package com.basejava.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link link;
    private List<Position> positions = new ArrayList<>();

    public Company(String name, String url, Position... positions) {
        Objects.requireNonNull(name, "name shouldn't be null");
        Objects.requireNonNull(url, "url shouldn't be null");
        this.link = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public static class Position implements Serializable {
        public final String filling;
        public final YearMonth startDate;
        public final YearMonth endDate;

        public Position(String filling, YearMonth startDate, YearMonth endDate) {
            Objects.requireNonNull(filling, "filling shouldn't be null");
            Objects.requireNonNull(startDate, "startDate shouldn't be null");
            Objects.requireNonNull(endDate, "endtDate shouldn't be null");
            this.filling = filling;
            this.startDate = startDate;
            this.endDate = endDate;
        }

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
}
