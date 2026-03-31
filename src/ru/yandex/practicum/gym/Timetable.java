package ru.yandex.practicum.gym;


import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>())
                .computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>())
                .add(trainingSession);

//         Сначала написал код ниже, сильно не понравилось, как он выглядит, решил поглядеть, как можно его сократить
//        и наткнулся на метод computeIfAbsent. Странно, что в курсе его не дают, он как будто идеально для этого задания подходит

//        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
//            TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeOfDayTraining =
//                    timetable.get(trainingSession.getDayOfWeek());
//            ArrayList<TrainingSession> list;
//            if (timeOfDayTraining.containsKey(trainingSession.getTimeOfDay())) {
//                list = timeOfDayTraining.get(trainingSession.getTimeOfDay());
//            } else {
//                list = new ArrayList<>();
//            }
//            list.add(trainingSession);
//            timeOfDayTraining.put(trainingSession.getTimeOfDay(), list);
//            timetable.put(trainingSession.getDayOfWeek(), timeOfDayTraining);
//        } else {
//            TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeOfDayTraining = new TreeMap<>();
//            ArrayList<TrainingSession> list = new ArrayList<>();
//            list.add(trainingSession);
//            timeOfDayTraining.put(trainingSession.getTimeOfDay(), list);
//            timetable.put(trainingSession.getDayOfWeek(), timeOfDayTraining);
//        }
    }


    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());//как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public ArrayList<CoachTrainingCounter> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>();
        ArrayList<CoachTrainingCounter> list = new ArrayList<>();
        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> time : timetable.values()) {
            for (ArrayList<TrainingSession> sessions : time.values()) {
                for (TrainingSession session : sessions) {
                    int count = countByCoaches.getOrDefault(session.getCoach(), 0);
                    count++;
                    countByCoaches.put(session.getCoach(), count);
                }
            }
        }
        for (Map.Entry<Coach, Integer> coachIntegerEntry : countByCoaches.entrySet()) {
            Coach key = coachIntegerEntry.getKey();
            Integer value = coachIntegerEntry.getValue();
            list.add(new CoachTrainingCounter(key, value));
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

