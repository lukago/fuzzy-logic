package ksr.models.entities;

import ksr.models.entities.enums.Education;
import ksr.models.entities.enums.MartialStatus;
import ksr.models.entities.enums.Occupation;
import ksr.models.entities.enums.Race;
import ksr.models.entities.enums.Relationship;
import ksr.models.entities.enums.Sex;
import ksr.models.entities.enums.WorkClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private WorkClass workclass;
    private Education education;
    private MartialStatus martialStatus;
    private Occupation occupation;
    private Relationship relationship;
    private Race race;
    private Sex sex;
    private int salary;
    private double age;
    private int capitalGain;
    private int capitalLoss;
    private int hoursPerWeek;
}
