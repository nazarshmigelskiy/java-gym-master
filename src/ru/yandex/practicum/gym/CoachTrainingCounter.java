package ru.yandex.practicum.gym;

public class CoachTrainingCounter {
    private Coach coach;
    private int trainingCounter;

    public CoachTrainingCounter(Coach coach, int trainingCounter) {
        this.coach = coach;
        this.trainingCounter = trainingCounter;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCounter() {
        return trainingCounter;
    }

    @Override
    public String toString() {
        return "CoachTrainingCounter{" +
                "coach=" + coach +
                ", trainingCounter=" + trainingCounter +
                '}';
    }
}
