package com.basejava.webapp.model;

import java.util.Objects;

public class SelfInfoSection extends AbstractSection {
    private final String selfInfo;

    public SelfInfoSection(String selfInfo) {
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