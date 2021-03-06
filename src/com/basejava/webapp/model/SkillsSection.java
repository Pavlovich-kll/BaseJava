package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SkillsSection extends Section {
    private static final long serialVersionUID = 1L;

    public  static SkillsSection empty = new SkillsSection("");

    private List<String> skills;

    public SkillsSection() {
    }

    public SkillsSection(String...skills) {
        this(Arrays.asList(skills));
    }

    public SkillsSection(List<String> skills) {
        Objects.requireNonNull(skills, "skills must not be null");
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillsSection that = (SkillsSection) o;
        return skills.equals(that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skills);
    }

    @Override
    public String toString() {
        return "SkillsSection{" +
                "skills=" + skills +
                '}';
    }
}