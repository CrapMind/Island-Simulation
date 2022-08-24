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
    private int id;     // внутренняя переменная для проверок, по сути не нужна
    @Getter
    private int x;      // координата x
    @Getter
    private int y;      // координата y
    private ThreadLocalRandom random = ThreadLocalRandom.current(); //переменная отвечающая за рандом
    @Getter
    private volatile Set <Creature> caterpillars = createRandomCreatures(GlobalCreatureTypes.CATERPILLAR); //список гусениц
    @Getter
    private volatile Set <Creature> plants = createRandomCreatures(GlobalCreatureTypes.PLANT); // список растений
    @Getter
    private volatile Set <Creature> herbivores = createRandomCreatures(GlobalCreatureTypes.HERBIVORE); // список травоядных
    @Getter
    private volatile Set <Creature> carnivores = createRandomCreatures(GlobalCreatureTypes.CARNIVORE); // список плотоядных
    @Getter
    private final ExecutorService creaturesService = Executors.newFixedThreadPool(2);   // сервис запускающий животных в данной локации
    @Getter
    private final ScheduledExecutorService babyService = Executors.newScheduledThreadPool(2); // сервис запускающий детей животных в данной локации

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = PublicData.locationId.incrementAndGet();
    } //Создаем локацию и сразу присваиваем координаты

    public boolean isExist(Location location) {     // метод на проверку существования локации
        return this != location && location != null;
    }
    public Location locationByDirection (Direction direction) {    // метод для получения локации по направлению
        Location[][]world = Island.getWorld();
        try {
            return switch (direction) {
                case SOUTH -> world[getX() + 1][getY()];
                case NORTH -> world[getX() - 1][getY()];
                case EAST -> world[getX()][getY() + 1];
                case WEST -> world[getX()][getY() - 1];
            };
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return this; // возвращает текущую локацию, если по указанному направлению выходим за пределы карты
        }
    } // Метод для получения локации по направлению

    private Set<Creature> createRandomCreatures(GlobalCreatureTypes type) {   // метод для создания случайных существ по глобальному типу (хищники, травоядные, растения)
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
        creatures.forEach(creature -> creature.setLocation(this)); // при создании присваиваем текущую локацию
        creatures.forEach(creature -> creature.setCanMultiply(true)); // задаем с рождения возможность размножаться
        return creatures;
    }
    @Override
    public String toString() {
        return "Location " + getId() + ": | Coordinates: x[" + getX() + "] y[" + getY() + "]";
    }

    public void revive() {  // метод "оживляющий" животных
        Set<Creature> creatureList = new HashSet<>(carnivores); // добавляем всех хищников и травоядных в один список
        creatureList.addAll(herbivores);
        creatureList.stream().filter(creature -> creature instanceof Animal).forEach(creature -> creaturesService.execute((Animal) creature)); // запускаем всех животных в жизнь
    }

    private List<Creature> createCreaturesByType(CreatureType creatureType) { // метод для создания случайных существ по определенному типу
        var creatures = new ArrayList<Creature>();
        int countOfCreatures = switch (creatureType) { // переменная, которая содержит количество животных необходимое для создания (от и до)
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
            creatures.add(CreatureCreator.create(creatureType)); // создаем животных и добавляем в список
        }
        return creatures;
    }
}
