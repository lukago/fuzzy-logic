package ksr;

import ksr.models.entities.Person;
import ksr.models.storage.CsvPersonDao;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AppTest {

    @Test
    public void shouldLoadCorrectSize() throws Exception {
        List<Person> people = new CsvPersonDao("data/adult.data").read();
        Assert.assertEquals(30162, people.size());
    }
}
