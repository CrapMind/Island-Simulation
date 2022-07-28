package Data;

import Creatures.Animals.Animal;
import Creatures.Creature;
import Creatures.CreatureCreator;
import Creatures.CreatureType;
import Creatures.GlobalCreatureTypes;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

public class Location {
    @Getter
    private int id;     // ���������� ���������� ��� ��������, �� ���� �� �����
    @Getter
    private int x;      // ���������� x
    @Getter
    private int y;      // ���������� y
    private ThreadLocalRandom random = ThreadLocalRandom.current(); //���������� ���������� �� ������
    @Getter
    private volatile Set <Creature> caterpillars = createRandomCreatures(GlobalCreatureTypes.CATERPILLAR); //������ �������
    @Getter
    private volatile Set <Creature> plants = createRandomCreatures(GlobalCreatureTypes.PLANT); // ������ ��������
    @Getter
    private volatile Set <Creature> herbivores = createRandomCreatures(GlobalCreatureTypes.HERBIVORE); // ������ ����������
    @Getter
    private volatile Set <Creature> carnivores = createRandomCreatures(GlobalCreatureTypes.CARNIVORE); // ������ ����������
    @Getter
    private final ExecutorService creaturesService = Executors.newFixedThreadPool(2);   // ������ ����������� �������� � ������ �������
    @Getter
    private final ScheduledExecutorService babyService = Executors.newScheduledThreadPool(2); // ������ ����������� ����� �������� � ������ �������

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = PublicData.locationId.incrementAndGet();
    } //������� ������� � ����� ����������� ����������

    public boolean isExist(Location location) {     // ����� �� �������� ������������� �������
        return this != location && location != null;
    }
    public Location locationByDirection (Direction direction) {    // ����� ��� ��������� ������� �� �����������
        Location[][]world = Island.getWorld();
        try {
            return switch (direction) {
                case SOUTH -> world[getX() + 1][getY()];
                case NORTH -> world[getX() - 1][getY()];
                case EAST -> world[getX()][getY() + 1];
                case WEST -> world[getX()][getY() - 1];
            };
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return this; // ���������� ������� �������, ���� �� ���������� ����������� ������� �� ������� �����
        }
    } // ����� ��� ��������� ������� �� �����������

    private Set<Creature> createRandomCreatures(GlobalCreatureTypes type) {   // ����� ��� �������� ��������� ������� �� ����������� ���� (�������, ����������, ��������)
        var creatures = new HashSet<Creature>();
        switch (type) {
            case CARNIVORE -> {
                for (int i = 0; i < CreatureType.carnivores.length; i++) {
                    creatures.addAll(createCreaturesByType(CreatureType.carnivores[i]));
                }
            }
            case HERBIVORE -> {
                for (int i = 0; i < CreatureType.herbivores.length; i++) {
                    creatures.addAll(createCreaturesByType(CreatureType.herbivores[i]));
                }
            }
            case PLANT -> creatures.addAll(createCreaturesByType(CreatureType.PLANT));
            case CATERPILLAR -> creatures.addAll(createCreaturesByType(CreatureType.CATERPILLAR));
        }
        creatures.forEach(creature -> creature.setLocation(this)); // ��� �������� ����������� ������� �������
        creatures.forEach(creature -> creature.setCanMultiply(true)); // ������ � �������� ����������� ������������
        return creatures;
    }
    @Override
    public String toString() {
        return "Location " + getId() + ": | Coordinates: x[" + getX() + "] y[" + getY() + "]";
    }

    public void revive() {  // ����� "����������" ��������
        Set<Creature> creatureList = new HashSet<>(carnivores); // ��������� ���� �������� � ���������� � ���� ������
        creatureList.addAll(herbivores);
        creatureList.stream().filter(creature -> creature instanceof Animal).forEach(creature -> creaturesService.execute((Animal) creature)); // ��������� ���� �������� � �����
    }

    private List<Creature> createCreaturesByType(CreatureType creatureType) { // ����� ��� �������� ��������� ������� �� ������������� ����
        var creatures = new ArrayList<Creature>();
        int countOfCreatures = switch (creatureType) { // ����������, ������� �������� ���������� �������� ����������� ��� �������� (�� � ��)
            case WOLF, FOX, BOA -> ThreadLocalRandom.current().nextInt(5, 30);
            case EAGLE, HORSE, DEER -> ThreadLocalRandom.current().nextInt(5, 20);
            case GOAT, SHEEP -> ThreadLocalRandom.current().nextInt(30, 140);
            case DUCK, PLANT -> ThreadLocalRandom.current().nextInt(50, 200);
            case BEAR -> ThreadLocalRandom.current().nextInt(2, 5);
            case RABBIT -> ThreadLocalRandom.current().nextInt(80, 150);
            case MOUSE -> ThreadLocalRandom.current().nextInt(300, 500);
            case BOAR -> ThreadLocalRandom.current().nextInt(10, 50);
            case BUFFALO -> ThreadLocalRandom.current().nextInt(3, 10);
            case CATERPILLAR -> ThreadLocalRandom.current().nextInt(500, 1000);
        };
        for (int i = 0; i < countOfCreatures; i++) {
            creatures.add(CreatureCreator.create(creatureType)); // ������� �������� � ��������� � ������
        }
        return creatures;
    }
}

