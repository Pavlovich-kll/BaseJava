package com.basejava.webapp.model;

import java.util.Objects;

public class SelfInfoSection extends Section {
    private static final long serialVersionUID = 1L;

    private String selfInfo;

    public SelfInfoSection() {
    }

    public SelfInfoSection(String selfInfo) {
        Objects.requireNonNull(selfInfo, "selfInfo must not be null");
        this.selfInfo = selfInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelfInfoSection that = (SelfInfoSection) o;
        return selfInfo.equals(that.selfInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selfInfo);
    }

    @Override
    public String toString() {
        return "SelfInfoSection{" +
                "texInfo='" + selfInfo + '\'' +
                '}';
    }
}