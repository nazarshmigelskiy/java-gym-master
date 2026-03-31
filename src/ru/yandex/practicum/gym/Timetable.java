package ru.yandex.practicum.gym;


import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    private HashMap<Coach, Integer> coachesCounter = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>())
                .computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>())
                .add(trainingSession);
        Coach currentCoach = trainingSession.getCoach();
        coachesCounter.put(currentCoach, coachesCounter.getOrDefault(currentCoach, 0) + 1);
    }


    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());//как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public ArrayList<CoachTrainingCounter> getCountByCoaches() {
        ArrayList<CoachTrainingCounter> list = new ArrayList<>();
        for (Coach coach : coachesCounter.keySet()) {
            CoachTrainingCounter coachTrainingCounter = new CoachTrainingCounter(coach, coachesCounter.get(coach));
            list.add(coachTrainingCounter);
        }

        list.sort((o1, o2) -> o2.getTrainingCounter() - o1.getTrainingCounter());
        return list;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "timetable=" + timetable +
                '}';
    }
}

