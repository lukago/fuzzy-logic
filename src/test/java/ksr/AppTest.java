package ksr;

import ksr.models.entities.Person;
import ksr.models.entities.enums.Occupation;
import ksr.models.entities.enums.Race;
import ksr.models.fuzzy.LinguisticAnswer;
import ksr.models.fuzzy.functions.DiscreteFunction;
import ksr.models.fuzzy.functions.TrapezoidalFunction;
import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Qualifier;
import ksr.models.fuzzy.sets.Quantyfier;
import ksr.models.storage.CsvPersonDao;
import ksr.models.storage.JsonQuantifierDao;
import ksr.models.storage.JsonWeightsDao;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppTest {

    @Test
    public void shouldLoadCorrectSize() throws Exception {
        List<Person> people = new CsvPersonDao("data/adult.data").read();
        Assert.assertEquals(30162, people.size());
    }

    @Test
    public void randomTests() throws Exception {
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();
        List<Double> weights1 = new JsonWeightsDao("data/weights1.json").read();
        List<Double> weights2 = new JsonWeightsDao("data/weights2.json").read();
        List<FuzzySet> fuzzySets = new ArrayList<>();

        FuzzySet middleAge = new FuzzySet(
                "middle age",
                new TrapezoidalFunction(30, 35, 45, 60),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        FuzzySet youngAge = new FuzzySet(
                "young age",
                new TrapezoidalFunction(0, 0, 25, 30),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        FuzzySet oldAge = new FuzzySet(
                "old age",
                new TrapezoidalFunction(60, 65, 80, 95),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        FuzzySet avarageSalaries = new FuzzySet(
                "average salaries",
                new TrapezoidalFunction(1000, 2000, 30000000, 40000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()));

        FuzzySet whiteRace = new FuzzySet(
                "white race",
                new DiscreteFunction(Race.WHITE.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()));

        fuzzySets.add(middleAge);
        fuzzySets.add(youngAge);
        fuzzySets.add(oldAge);
        fuzzySets.add(avarageSalaries);
        fuzzySets.add(whiteRace);

        List<Qualifier> qualifiers = new ArrayList<>();
        Qualifier raceQualifier = new Qualifier(
                "black race",
                new DiscreteFunction(Race.BLACK.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                avarageSalaries
        );
        Qualifier avarageAgeTriangleAvarageSalaries = new Qualifier(
                "middle age",
                new TrapezoidalFunction(25, 35, 45, 50),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                avarageSalaries);
        Qualifier managersAvarageSalaries = new Qualifier(
                "managers",
                new DiscreteFunction(Occupation.EXEC_MANAGERIAL.ordinal()),
                people.stream().map(s -> (double) s.getOccupation().ordinal()).collect(Collectors.toList()),
                avarageSalaries
        );

        qualifiers.add(avarageAgeTriangleAvarageSalaries);
        qualifiers.add(raceQualifier);
        qualifiers.add(managersAvarageSalaries);

        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers, weights1));
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights2));
    }
}
