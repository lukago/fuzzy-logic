package ksr;

import ksr.models.entities.Person;
import ksr.models.fuzzy.FuzzySet;
import ksr.models.fuzzy.functions.TrapezoidalFunction;
import ksr.models.storage.CsvDao;

import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        List<Person> people = new CsvDao("data/adult.data").read();
        System.out.println(people.size());

        FuzzySet avarageAgeTrapezoid = new FuzzySet(
                "Midle Age",
                new TrapezoidalFunction(25, 20, 35, 40),
                people.stream().map(Person::getAge).collect(Collectors.toList()));


    }
}
