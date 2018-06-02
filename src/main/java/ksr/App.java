package ksr;

import com.google.gson.Gson;
import ksr.models.entities.Person;
import ksr.models.fuzzy.LinguisticAnswer;
import ksr.models.fuzzy.functions.TrapezoidalFunction;
import ksr.models.fuzzy.sets.FuzzySet;
import ksr.models.fuzzy.sets.Quantyfier;
import ksr.models.storage.CsvPersonDao;
import ksr.models.storage.JsonQuantifierDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        List<Person> people = new CsvPersonDao("data/adult.data").read();
        System.out.println(people.size());

        FuzzySet avarageAgeTrapezoid = new FuzzySet(
                "Midle Age",
                new TrapezoidalFunction(20, 25, 35, 40),
                people.stream().map(Person::getAge).collect(Collectors.toList()));

        Quantyfier quantyfier = new Quantyfier(
                new TrapezoidalFunction(0, 1, 30000, 30002),
                "Majority",
                false);

        List<FuzzySet> fuzzySets = new ArrayList<>();
        fuzzySets.add(avarageAgeTrapezoid);

        List<Quantyfier> quantyfiers = new JsonQuantifierDao("data/quantificators.json").read();
        quantyfiers.add(quantyfier);

        System.out.println(LinguisticAnswer.answerSets(fuzzySets, quantyfiers));
    }
}
