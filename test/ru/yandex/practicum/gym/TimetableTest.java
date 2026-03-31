package ru.yandex.practicum.gym;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());//Проверить, что за понедельник вернулось одно занятие
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());//Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());// Проверить, что за понедельник вернулось одно занятие

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> thursdayMap =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdayMap.size());
        Iterator<TimeOfDay> iterator = thursdayMap.keySet().iterator();
        TimeOfDay first = iterator.next();
        TimeOfDay second = iterator.next();
        assertEquals(new TimeOfDay(13, 0), first);
        assertEquals(new TimeOfDay(20, 0), second);
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        ArrayList<TrainingSession> existingList =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        ArrayList<TrainingSession> notExistingList =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(1, existingList.size());//Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(0, notExistingList.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void  getCountByCoachesShouldReturn1Coach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        ArrayList<CoachTrainingCounter> list = timetable.getCountByCoaches();
        assertEquals(1, list.size());
    }

    @Test
    void getCountByCoachesShouldReturn2Coaches() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach anotherCoach = new Coach("Васильев1", "Николай1", "Сергеевич1");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, anotherCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        ArrayList<CoachTrainingCounter> list = timetable.getCountByCoaches();
        assertEquals(2, list.size());
    }

    @Test
    void getCountByCoachesShouldReturnSortedList() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("1", "2", "3");
        Coach coach2 = new Coach("2", "3", "4");
        Coach coach3 = new Coach("4", "6", "5");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session2 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session3 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session4 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session5 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session6 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);
        timetable.addNewTrainingSession(session5);
        timetable.addNewTrainingSession(session6);

        ArrayList<CoachTrainingCounter> list = timetable.getCountByCoaches();

        Iterator<CoachTrainingCounter> iterator = list.iterator();
        CoachTrainingCounter first = iterator.next();
        CoachTrainingCounter second = iterator.next();
        CoachTrainingCounter third = iterator.next();

        assertEquals(3, first.getTrainingCounter());
        assertEquals(2, second.getTrainingCounter());
        assertEquals(1, third.getTrainingCounter());
    }
}
