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

    @Test
    public void randomJerzytask1to3() throws Exception{
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();
        List<Double> weights1 = new JsonWeightsDao("data/weights1.json").read();
        List<Double> weights2 = new JsonWeightsDao("data/weights2.json").read();
        List<Double> weights4 = new JsonWeightsDao("data/weights4.json").read();
        List<Double> weights3 = new JsonWeightsDao("data/weights3.json").read();
        List<Double> weights5 = new JsonWeightsDao("data/weights5.json").read();
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

        List<Qualifier> qualifiers = new ArrayList<>();
        /*
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


        System.out.println(oldAge.getValues().stream().mapToInt(Double::intValue).average());
        System.out.println(highSalary.getValues().stream().mapToInt(Double::intValue).average());
        System.out.println(highWeeklyLoad.getValues().stream().mapToInt(Double::intValue).average());
        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers, weights1)); //T1-T5
        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers, weights3)); //T1
        System.out.println("");

        Qualifier avarageAgeTriangleAvarageSalaries = new Qualifier(
                "middle age",
                new TrapezoidalFunction(25, 35, 45, 50),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                lowSalary);

        Qualifier avarageAgeTriangleAvarageSalaries2 = new Qualifier(
                "middle age",
                new TrapezoidalFunction(25, 35, 45, 50),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                averageSalary);

        Qualifier avarageAgeTriangleAvarageSalaries3 = new Qualifier(
                "middle age",
                new TrapezoidalFunction(25, 35, 45, 50),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                highSalary);

        Qualifier lavarageAgeTriangleAvarageSalaries = new Qualifier(
                "middle age",
                new TrapezoidalFunction(0, 0, 25, 30),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                lowSalary);

        Qualifier lavarageAgeTriangleAvarageSalaries2 = new Qualifier(
                "middle age",
                new TrapezoidalFunction(0, 0, 25, 30),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                averageSalary);

        Qualifier lavarageAgeTriangleAvarageSalaries3 = new Qualifier(
                "middle age",
                new TrapezoidalFunction(0, 0, 25, 30),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                highSalary);

        Qualifier havarageAgeTriangleAvarageSalaries = new Qualifier(
                "old age",
                new TrapezoidalFunction(60, 65, 80, 95),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                lowSalary);

        Qualifier havarageAgeTriangleAvarageSalaries2 = new Qualifier(
                "old age",
                new TrapezoidalFunction(60, 65, 80, 95),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                averageSalary);

        Qualifier havarageAgeTriangleAvarageSalaries3 = new Qualifier(
                "old age",
                new TrapezoidalFunction(60, 65, 80, 95),
                people.stream().map(Person::getAge).collect(Collectors.toList()),
                highSalary);

        qualifiers.add(avarageAgeTriangleAvarageSalaries);
        qualifiers.add(avarageAgeTriangleAvarageSalaries2);
        qualifiers.add(avarageAgeTriangleAvarageSalaries3);

        qualifiers.add(lavarageAgeTriangleAvarageSalaries);
        qualifiers.add(lavarageAgeTriangleAvarageSalaries2);
        qualifiers.add(lavarageAgeTriangleAvarageSalaries3);

        qualifiers.add(havarageAgeTriangleAvarageSalaries);
        qualifiers.add(havarageAgeTriangleAvarageSalaries2);
        qualifiers.add(havarageAgeTriangleAvarageSalaries3);

        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights4)); //T1
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights5)); //T1-T5
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights2)); //T1-T11
    }

    @Test
    public void randomJerzyTask4() throws Exception{
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();
        List<Double> weights1 = new JsonWeightsDao("data/weights1.json").read();
        List<Double> weights2 = new JsonWeightsDao("data/weights2.json").read();
        List<Double> weights4 = new JsonWeightsDao("data/weights4.json").read();
        List<Double> weights3 = new JsonWeightsDao("data/weights3.json").read();
        List<Double> weights5 = new JsonWeightsDao("data/weights5.json").read();
        List<FuzzySet> fuzzySets = new ArrayList<>();
        List<Qualifier> qualifiers = new ArrayList<>();

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

        fuzzySets.add(youngAge);
        fuzzySets.add(middleAge);
        fuzzySets.add(oldAge);

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

        Qualifier task41 = new Qualifier(
                "low salary",
                new TrapezoidalFunction(10000, 25000, 45000, 60000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                youngAge);

        Qualifier task42 = new Qualifier(
                "average salary",
                new TrapezoidalFunction(80000, 170000, 250000, 300000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                youngAge);

        Qualifier task43 = new Qualifier(
                "high salary",
                new TrapezoidalFunction(300000, 450000, 3000000, 5000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                youngAge);

        Qualifier task44 = new Qualifier(
                "low salary",
                new TrapezoidalFunction(10000, 25000, 45000, 60000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                middleAge);

        Qualifier task45 = new Qualifier(
                "average salary",
                new TrapezoidalFunction(80000, 170000, 250000, 300000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                middleAge);

        Qualifier task46 = new Qualifier(
                "high salary",
                new TrapezoidalFunction(300000, 450000, 3000000, 5000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                middleAge);

        Qualifier task47 = new Qualifier(
                "low salary",
                new TrapezoidalFunction(10000, 25000, 45000, 60000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                oldAge);

        Qualifier task48 = new Qualifier(
                "average salary",
                new TrapezoidalFunction(80000, 170000, 250000, 300000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                oldAge);

        Qualifier task49 = new Qualifier(
                "high salary",
                new TrapezoidalFunction(300000, 450000, 3000000, 5000000),
                people.stream().map(s -> (double) s.getSalary()).collect(Collectors.toList()),
                oldAge);



        qualifiers.add(task41);
        qualifiers.add(task42);
        qualifiers.add(task43);
        qualifiers.add(task44);
        qualifiers.add(task45);
        qualifiers.add(task46);
        qualifiers.add(task47);
        qualifiers.add(task48);
        qualifiers.add(task49);

        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights4)); //T1
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights5)); //T1-T5
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights2)); //T1-T11
    }

    @Test
    public void randomJerzyTask5() throws Exception{
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();
        List<Double> weights1 = new JsonWeightsDao("data/weights1.json").read();
        List<Double> weights2 = new JsonWeightsDao("data/weights2.json").read();
        List<Double> weights4 = new JsonWeightsDao("data/weights4.json").read();
        List<Double> weights3 = new JsonWeightsDao("data/weights3.json").read();
        List<Double> weights5 = new JsonWeightsDao("data/weights5.json").read();
        List<FuzzySet> fuzzySets = new ArrayList<>();
        List<Qualifier> qualifiers = new ArrayList<>();

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

        fuzzySets.add(youngAge);
        fuzzySets.add(middleAge);
        fuzzySets.add(oldAge);

        Qualifier task51 = new Qualifier(
                "high weekly load",
                new TrapezoidalFunction(50, 55, 80, 100),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                youngAge);

        Qualifier task52 = new Qualifier(
                "average weekly load",
                new TrapezoidalFunction(30, 35, 45, 50),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                youngAge);

        Qualifier task53 = new Qualifier(
                "low weekly load",
                new TrapezoidalFunction(15, 18, 22, 30),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                youngAge);

        Qualifier task54 = new Qualifier(
                "high weekly load",
                new TrapezoidalFunction(50, 55, 80, 100),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                middleAge);

        Qualifier task55 = new Qualifier(
                "average weekly load",
                new TrapezoidalFunction(30, 35, 45, 50),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                middleAge);

        Qualifier task56 = new Qualifier(
                "low weekly load",
                new TrapezoidalFunction(15, 18, 22, 30),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                middleAge);

        Qualifier task57 = new Qualifier(
                "high weekly load",
                new TrapezoidalFunction(50, 55, 80, 100),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                oldAge);

        Qualifier task58 = new Qualifier(
                "average weekly load",
                new TrapezoidalFunction(30, 35, 45, 50),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                oldAge);

        Qualifier task59 = new Qualifier(
                "low weekly load",
                new TrapezoidalFunction(15, 18, 22, 30),
                people.stream().map(s -> (double) s.getHoursPerWeek()).collect(Collectors.toList()),
                oldAge);



        qualifiers.add(task51);
        qualifiers.add(task52);
        qualifiers.add(task53);
        qualifiers.add(task54);
        qualifiers.add(task55);
        qualifiers.add(task56);
        qualifiers.add(task57);
        qualifiers.add(task58);
        qualifiers.add(task59);

        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights4)); //T1
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights5)); //T1-T5
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights2)); //T1-T11

    }

    @Test
    public void randomJerzyTask6() throws Exception{

        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();

        List<Double> weights2 = new JsonWeightsDao("data/weights2.json").read();
        List<Double> weights4 = new JsonWeightsDao("data/weights4.json").read();

        List<Double> weights5 = new JsonWeightsDao("data/weights5.json").read();
        List<FuzzySet> fuzzySets = new ArrayList<>();
        List<Qualifier> qualifiers = new ArrayList<>();

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

        fuzzySets.add(youngAge);
        fuzzySets.add(middleAge);
        fuzzySets.add(oldAge);

        Qualifier task61 = new Qualifier(
                "bachelors",
                new DiscreteFunction(Education.BACHELORS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task62 = new Qualifier(
                "doctorates",
                new DiscreteFunction(Education.DOCTORATE.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task63 = new Qualifier(
                "masters",
                new DiscreteFunction(Education.MASTERS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task64 = new Qualifier(
                "bachelors",
                new DiscreteFunction(Education.BACHELORS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task65 = new Qualifier(
                "doctorates",
                new DiscreteFunction(Education.DOCTORATE.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task66 = new Qualifier(
                "masters",
                new DiscreteFunction(Education.MASTERS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task67 = new Qualifier(
                "bachelors",
                new DiscreteFunction(Education.BACHELORS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                oldAge);

        Qualifier task68 = new Qualifier(
                "doctorates",
                new DiscreteFunction(Education.DOCTORATE.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                oldAge);

        Qualifier task69 = new Qualifier(
                "masters",
                new DiscreteFunction(Education.MASTERS.ordinal()),
                people.stream().map(s -> (double) s.getEducation().ordinal()).collect(Collectors.toList()),
                oldAge);



        qualifiers.add(task61);
        qualifiers.add(task62);
        qualifiers.add(task63);
        qualifiers.add(task64);
        qualifiers.add(task65);
        qualifiers.add(task66);
        qualifiers.add(task67);
        qualifiers.add(task68);
        qualifiers.add(task69);

        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights4)); //T1
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights5)); //T1-T5
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights2)); //T1-T11

    }

    @Test
    public void randomJerzyTask7() throws Exception{
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();

        List<Double> weights2 = new JsonWeightsDao("data/weights2.json").read();
        List<Double> weights4 = new JsonWeightsDao("data/weights4.json").read();

        List<Double> weights5 = new JsonWeightsDao("data/weights5.json").read();
        List<FuzzySet> fuzzySets = new ArrayList<>();
        List<Qualifier> qualifiers = new ArrayList<>();

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

        fuzzySets.add(youngAge);
        fuzzySets.add(middleAge);
        fuzzySets.add(oldAge);

        Qualifier task71 = new Qualifier(
                "black race",
                new DiscreteFunction(Race.BLACK.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task72 = new Qualifier(
                "white race",
                new DiscreteFunction(Race.WHITE.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task73 = new Qualifier(
                "asian race",
                new DiscreteFunction(Race.ASIAN_PAC_ISLANDER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task74 = new Qualifier(
                "other race",
                new DiscreteFunction(Race.OTHER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                youngAge);

        Qualifier task75 = new Qualifier(
                "black race",
                new DiscreteFunction(Race.BLACK.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task76 = new Qualifier(
                "white race",
                new DiscreteFunction(Race.WHITE.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task77 = new Qualifier(
                "asian race",
                new DiscreteFunction(Race.ASIAN_PAC_ISLANDER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task78 = new Qualifier(
                "other race",
                new DiscreteFunction(Race.OTHER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                middleAge);

        Qualifier task79 = new Qualifier(
                "black race",
                new DiscreteFunction(Race.BLACK.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                oldAge);

        Qualifier task710 = new Qualifier(
                "white race",
                new DiscreteFunction(Race.WHITE.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                oldAge);

        Qualifier task711 = new Qualifier(
                "asian race",
                new DiscreteFunction(Race.ASIAN_PAC_ISLANDER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                oldAge);

        Qualifier task712 = new Qualifier(
                "other race",
                new DiscreteFunction(Race.OTHER.ordinal()),
                people.stream().map(s -> (double) s.getRace().ordinal()).collect(Collectors.toList()),
                oldAge);


        qualifiers.add(task71);
        qualifiers.add(task72);
        qualifiers.add(task73);
        qualifiers.add(task74);
        qualifiers.add(task75);
        qualifiers.add(task76);
        qualifiers.add(task77);
        qualifiers.add(task78);
        qualifiers.add(task79);
        qualifiers.add(task710);
        qualifiers.add(task711);
        qualifiers.add(task712);

        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights4)); //T1
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights5)); //T1-T5
        System.out.println(LinguisticAnswer.answerQualifiers(qualifiers, quantyfiers, weights2)); //T1-T11

    }
}
