package com.basejava.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Company {
    private final Link link;
    private final String filling;
    private final YearMonth startDate;
    private final YearMonth endDate;

    public Company(String name, String url, String filling, YearMonth startDate, YearMonth endDate) {
        Objects.requireNonNull(name, "name shouldn't be null");
        Objects.requireNonNull(url, "url shouldn't be null");
        Objects.requireNonNull(filling, "filling shouldn't be null");
        Objects.requireNonNull(startDate, "startDate shouldn't be null");
        Objects.requireNonNull(endDate, "endtDate shouldn't be null");
        this.link = new Link(name, url);
        this.filling = filling;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Company{" +
                "link=" + link +
                ", filling='" + filling + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
