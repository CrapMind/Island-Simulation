package Data;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {

    private final static Statistics statistics = new Statistics();
    @Getter
    public static AtomicInteger countOfCreatures = new AtomicInteger(0);                                       //общее количество существ
    @Getter
    private static AtomicInteger countOfEatenAnimals = new AtomicInteger(0); //общее количество съеденных животных
    @Getter
    private static AtomicInteger countOfEatenPlants = new AtomicInteger(0); // общее количество съеденных растений
    @Getter
    private static AtomicInteger countOfDiedOfHunger = new AtomicInteger(0); // общее количество умерших от голода животных
    @Getter
    private static AtomicInteger countOfBirths = new AtomicInteger(0); // общее количество рожденных животных
    @Getter
    private static AtomicInteger countOfDissolved = new AtomicInteger(0); // общее количество уничтоженных существ посредством метода "dissolve..." в классе Creature
    @Getter
    private static AtomicInteger countOfDays = new AtomicInteger(0); // количество пройденных дней

    @Override
    public String toString() {  // вывод общей статистики
        return "День " + countOfDays + " завершен\n" +
                "Съедено животных: " + countOfEatenAnimals + "\n" +
                "Съедено растений: " + countOfEatenPlants + "\n" +
                "Умерло от голода: " + countOfDiedOfHunger + "\n" +
                "Рождено животных: " + countOfBirths + "\n" +
                "Уничтожено существ: " + countOfDissolved;
    }

    public static Statistics getInstance() {
        return statistics;
    }
}
