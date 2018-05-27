package ksr.models.storage;

import ksr.models.entities.Person;
import ksr.models.entities.enums.Education;
import ksr.models.entities.enums.MartialStatus;
import ksr.models.entities.enums.Occupation;
import ksr.models.entities.enums.Race;
import ksr.models.entities.enums.Relationship;
import ksr.models.entities.enums.Sex;
import ksr.models.entities.enums.WorkClass;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvDao implements IDao<List<Person>> {
    private String filepath;

    public CsvDao(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Person> read() throws Exception {
        List<Person> people = new ArrayList<>();

        Files.readAllLines(Paths.get(filepath)).forEach(line -> {
            if (line.contains("?")) {
                return;
            }

            line = line.replace("11th", "eleventh")
                    .replace("9th", "nineth")
                    .replace("12th", "twelveth")
                    .replace("1st-4th", "first_fourth")
                    .replace("7th-8th", "seventh_eighth")
                    .replace("10th", "tenth")
                    .replace("5th-6th", "fiveth_sixth")
                    .replace('-', '_')
                    .toUpperCase();

            String[] records = line.split(", ");
            Person person = new Person();

            person.setAge(Integer.valueOf(records[0]));
            person.setWorkclass(WorkClass.valueOf(records[1]));
            person.setEducation(Education.valueOf(records[3]));
            person.setMartialStatus(MartialStatus.valueOf(records[5]));
            person.setOccupation(Occupation.valueOf(records[6]));
            person.setRelationship(Relationship.valueOf(records[7]));
            person.setRace(Race.valueOf(records[8]));
            person.setSex(Sex.valueOf(records[9]));
            person.setCapitalGain(Integer.valueOf(records[10]));
            person.setCapitalLoss(Integer.valueOf(records[11]));
            person.setHoursPerWeek(Integer.valueOf(records[12]));

            people.add(person);
        });

        return people;
    }

    @Override
    public void write(List<Person> in) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }
}
