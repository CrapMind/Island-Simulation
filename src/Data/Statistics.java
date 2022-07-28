package Data;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {

    private final static Statistics statistics = new Statistics();
    @Getter
    public static AtomicInteger countOfCreatures = new AtomicInteger(0);                                       //����� ���������� �������
    @Getter
    private static AtomicInteger countOfEatenAnimals = new AtomicInteger(0); //����� ���������� ��������� ��������
    @Getter
    private static AtomicInteger countOfEatenPlants = new AtomicInteger(0); // ����� ���������� ��������� ��������
    @Getter
    private static AtomicInteger countOfDiedOfHunger = new AtomicInteger(0); // ����� ���������� ������� �� ������ ��������
    @Getter
    private static AtomicInteger countOfBirths = new AtomicInteger(0); // ����� ���������� ��������� ��������
    @Getter
    private static AtomicInteger countOfDissolved = new AtomicInteger(0); // ����� ���������� ������������ ������� ����������� ������ "dissolve..." � ������ Creature
    @Getter
    private static AtomicInteger countOfDays = new AtomicInteger(0); // ���������� ���������� ����

    @Override
    public String toString() {  // ����� ����� ����������
        return "���� " + countOfDays + " ��������\n" +
                "������� ��������: " + countOfEatenAnimals + "\n" +
                "������� ��������: " + countOfEatenPlants + "\n" +
                "������ �� ������: " + countOfDiedOfHunger + "\n" +
                "������� ��������: " + countOfBirths + "\n" +
                "���������� �������: " + countOfDissolved;
    }

    public static Statistics getInstance() {
        return statistics;
    }
}
