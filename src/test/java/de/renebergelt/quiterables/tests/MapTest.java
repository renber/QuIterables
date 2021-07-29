package de.renebergelt.quiterables.tests;

import de.renebergelt.quiterables.QuIterables;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MapTest {

    @Test
    public void test_toMapToSelf() {
        // generate test data for each test case
        // will be generated anew for each test
        List<TestPojo> sampleData = new ArrayList<>();

        sampleData.add(new TestPojo("Item One", 1, "Child 1", "Child 2"));
        sampleData.add(new TestPojo("Item Two", 2, "Child 1"));
        sampleData.add(new TestPojo("Item Three", 3));
        sampleData.add(new TestPojo("Item Three b", 4, "Child 1"));
        sampleData.add(new TestPojo("Item Four", 5));
        sampleData.add(new TestPojo("Item Four b", 6, "Child 1", "Child 2", "Child 3"));

        Map<Integer, TestPojo> map = QuIterables.query(sampleData).toMap(x -> x.numberItem);
        assertEquals(6, map.size());

        for(TestPojo tp: sampleData) {
            assertEquals(map.get(tp.numberItem), tp);
        }
    }

    @Test
    public void test_toMapToValue() {
        // generate test data for each test case
        // will be generated anew for each test
        List<TestPojo> sampleData = new ArrayList<>();

        sampleData.add(new TestPojo("Item One", 1, "Child 1", "Child 2"));
        sampleData.add(new TestPojo("Item Two", 2, "Child 1"));
        sampleData.add(new TestPojo("Item Three", 3));
        sampleData.add(new TestPojo("Item Three b", 4, "Child 1"));
        sampleData.add(new TestPojo("Item Four", 5));
        sampleData.add(new TestPojo("Item Four b", 6, "Child 1", "Child 2", "Child 3"));

        Map<Integer, String> map = QuIterables.query(sampleData).toMap(x -> x.numberItem, x -> x.textItem);
        assertEquals(6, map.size());

        for(TestPojo tp: sampleData) {
            assertEquals(map.get(tp.numberItem), tp.textItem);
        }
    }

}
