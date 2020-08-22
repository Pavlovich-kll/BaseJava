package com.basejava.webapp.util;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.SelfInfoSection;
import org.junit.Assert;
import org.junit.Test;

import static com.basejava.webapp.storage.AbstractStorageTest.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() throws Exception {
        Section section1 = new SelfInfoSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}