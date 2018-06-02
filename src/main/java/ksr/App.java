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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json").read();
        List<FuzzySet> fuzzySets = new ArrayList<>();

        FuzzySet middleAge = new FuzzySet(
                "middle age",
                new TrapezoidalFunction(25, 35, 45, 60),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        FuzzySet youngAge = new FuzzySet(
                "young age",
                new TrapezoidalFunction(0, 0, 20, 25),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        FuzzySet oldAge = new FuzzySet(
                "old age",
                new TrapezoidalFunction(56, 65, 100, 110),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        FuzzySet avarageSalaries = new FuzzySet(
                "average salaries",
                new TrapezoidalFunction(1000, 2000, 30000000, 40000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()));

        fuzzySets.add(middleAge);
        fuzzySets.add(youngAge);
        fuzzySets.add(oldAge);
        fuzzySets.add(avarageSalaries);

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

        System.out.println(avarageSalaries.getValues().stream().mapToInt(Double::intValue).average());

        qualifiers.add(avarageAgeTriangleAvarageSalaries);
        qualifiers.add(raceQualifier);
        qualifiers.add(managersAvarageSalaries);

        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers));
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers));
    }
}
