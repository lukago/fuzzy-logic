package ksr;

import ksr.models.entities.Person;
import ksr.models.fuzzy.LinguisticAnswer;
import ksr.models.fuzzy.functions.TrapezoidalFunction;
import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Quantyfier;
import ksr.models.storage.CsvPersonDao;
import ksr.models.storage.JsonQuantifierDao;
import ksr.models.storage.JsonWeightsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        List<Person> people = new CsvPersonDao("data/adult.test").read();
        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json", people.size()).read();
        List<Double> weights1 = new JsonWeightsDao("data/weights1.json").read();
        List<Double> weights3 = new JsonWeightsDao("data/weights3.json").read();
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

        fuzzySets.add(youngAge);
        fuzzySets.add(middleAge);
        fuzzySets.add(oldAge);

        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers, weights1));
        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers, weights3));
    }
}
