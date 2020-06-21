package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {
    private final String title;
    private final String filling;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Company(String title, String filling, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(title, "title shouldn't be null");
        Objects.requireNonNull(filling, "filling shouldn't be null");
        Objects.requireNonNull(startDate, "startDate shouldn't be null");
        Objects.requireNonNull(endDate, "endtDate shouldn't be null");
        this.title = title;
        this.filling = filling;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Company{" +
                "title='" + title + '\'' +
                ", filling='" + filling + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
