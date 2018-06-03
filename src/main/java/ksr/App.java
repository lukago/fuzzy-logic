package ksr;

import ksr.models.entities.Person;
import ksr.models.entities.enums.Education;
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
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators3.json", people.size()).read();
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

        /*FuzzySet avarageSalaries = new FuzzySet(
                "average salaries",
                new TrapezoidalFunction(1000, 2000, 30000000, 40000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()));
*/
        fuzzySets.add(youngAge);
        fuzzySets.add(middleAge);
        fuzzySets.add(oldAge);
        //fuzzySets.add(avarageSalaries);



        //System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers));


        FuzzySet highSalary = new FuzzySet(
                "high salary",
                new TrapezoidalFunction(300000, 450000, 3000000, 5000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()));

        FuzzySet averageSalary = new FuzzySet(
                "average salary",
                new TrapezoidalFunction(80000, 170000, 250000, 300000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()));

        FuzzySet lowSalary = new FuzzySet(
                "low salary",
                new TrapezoidalFunction(10000, 25000, 45000, 60000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()));


        fuzzySets.add(highSalary);
        fuzzySets.add(averageSalary);
        fuzzySets.add(lowSalary);

        FuzzySet highWeeklyLoad = new FuzzySet(
                "high weekly load",
                new TrapezoidalFunction(50, 55, 80, 100),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()));

        FuzzySet averageWeeklyLoad = new FuzzySet(
                "average weekly load",
                new TrapezoidalFunction(30, 35, 45, 50),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()));

        FuzzySet lowWeeklyLoad = new FuzzySet(
                "low weekly load",
                new TrapezoidalFunction(15, 18, 22, 30),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()));

        fuzzySets.add(highWeeklyLoad);
        fuzzySets.add(averageWeeklyLoad);
        fuzzySets.add(lowWeeklyLoad);

      /*  List<Qualifier> qualifiers = new ArrayList<>();
        Qualifier raceQualifier = new Qualifier(
                "black race",
                new DiscreteFunction(Race.BLACK.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                averageSalary
        );
        Qualifier avarageAgeTriangleAvarageSalaries = new Qualifier(
                "middle age",
                new TrapezoidalFunction(25, 35, 45, 50),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                averageSalary);
        Qualifier managersAvarageSalaries = new Qualifier(
                "managers",
                new DiscreteFunction(Occupation.EXEC_MANAGERIAL.ordinal()),
                people.stream().map(s -> (double) s.getOccupation().ordinal()).collect(Collectors.toList()),
                averageSalary
        );

        qualifiers.add(avarageAgeTriangleAvarageSalaries);
        qualifiers.add(raceQualifier);
        qualifiers.add(managersAvarageSalaries);
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers));
*/
        FuzzySet bachelors = new FuzzySet(
                "bachelors",
                new DiscreteFunction(Education.BACHELORS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()));

        FuzzySet masters = new FuzzySet(
                "masters",
                new DiscreteFunction(Education.MASTERS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()));

        FuzzySet doctorates = new FuzzySet(
                "doctorates",
                new DiscreteFunction(Education.DOCTORATE.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()));

        fuzzySets.add(bachelors);
        fuzzySets.add(masters);
        fuzzySets.add(doctorates);

        FuzzySet blacks = new FuzzySet(
                "black race",
                new DiscreteFunction(Race.BLACK.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()));

        FuzzySet whites = new FuzzySet(
                "white race",
                new DiscreteFunction(Race.WHITE.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()));

        FuzzySet asians = new FuzzySet(
                "asian race",
                new DiscreteFunction(Race.ASIAN_PAC_ISLANDER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()));

        FuzzySet others = new FuzzySet(
                "other race",
                new DiscreteFunction(Race.OTHER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()));

        fuzzySets.add(blacks);
        fuzzySets.add(whites);
        fuzzySets.add(asians);
        fuzzySets.add(others);



        System.out.println(youngAge.getValues().stream().mapToInt(Double::intValue).average());



        System.out.println(oldAge.getValues().stream().mapToInt(Double::intValue).average());
        System.out.println(highSalary.getValues().stream().mapToInt(Double::intValue).average());
        System.out.println(highWeeklyLoad.getValues().stream().mapToInt(Double::intValue).average());
        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers));
        System.out.println(LinguisticAnswer.answerSets2(fuzzySets, quantyfiers));


    }
}
