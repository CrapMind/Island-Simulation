package Creatures.Animals;

import Creatures.Animals.Carnivore.Carnivore;
import Creatures.Animals.Herbivore.Caterpillar;
import Creatures.Creature;
import Creatures.CreatureCreator;
import Creatures.CreatureType;
import Creatures.Plants.Plants;
import Data.Direction;
import Data.Location;
import Data.Statistics;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public abstract class Animal extends Creature implements Runnable {

    @Getter
    @Setter
    private volatile int id;// ���������� ����������, ����� ���� ��� ����������� ��������, ������ �� ������
    @Getter
    private double maxSatiety; // ������������ �������
    @Getter
    @Setter
    private volatile double satiety; // �������
    @Getter
    private volatile double weight; // ���
    @Getter
    private int movingSpeed; // ������������ ���������� ������, �� ������� �������� ����� ��������
    @Getter
    private Map<CreatureType, Integer> dependencies; // ���� ���������� ��������: ��� ��������, ���� ��������

    public Animal(double weight, int movingSpeed, double maxSatiety) {
        this.weight = weight;
        this.movingSpeed = movingSpeed;
        this.maxSatiety = maxSatiety;
    }

    public void searchForFood(Map<CreatureType, Integer> dependencies, List<Creature> potentialFood) { // ����� ���. � ���������� ���� � ������ �������� � ������ ��������, � ������ �������� ��� ��������
        try {
            Thread.sleep(200);
        }catch (InterruptedException e) {
            return;
        }
        synchronized (Collections.unmodifiableList(potentialFood)) {
            for (Creature creature : potentialFood) {
                if (dependencies.containsKey(creature.getCreatureType())) { //��������� ���� �� � ���� �������� ������� ����
                    var dices = ThreadLocalRandom.current().nextInt(1, 100); // ��������� ������
                    if (dices <= dependencies.get(creature.getCreatureType())) { // ���� ���� �������� ��������� � ������ � ���� - ������
                        eat(creature, creature instanceof Caterpillar ? getLocation().getCaterpillars() : getFood()); // ���� ���� �������� - �������� ������ �������
                        break;
                    }
                }
            }
        }
    }

    public void moving() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            return;
        }
        var dices = ThreadLocalRandom.current().nextInt(0, getMovingSpeed()); // ��������� ������ �� 0 �� ����������� ���������� ��������
        for (int i = 0; i < dices; i++) {
            Direction direction = Direction.directions[ThreadLocalRandom.current().nextInt(0, Direction.directions.length)]; // �������� ��������� �����������
            if (!getLocation().isExist(getLocation().locationByDirection(direction))) { // ���� ������� �� ���������� - �������� ���� �����
                i--;
                continue;
            }
            goToLocation(getLocation().locationByDirection(direction)); // ���� �� ����������� �����������
        }
    }

    private void goToLocation(Location nextLocation) { // ����� �������� �������� ������� � ��������� ��� ������� ��������
        if (this instanceof Carnivore) {
            nextLocation.getCarnivores().add(this);
            getLocation().getCarnivores().remove(this);
        }
        else {
            nextLocation.getHerbivores().add(this);
            getLocation().getHerbivores().remove(this);
        }
            setLocation(nextLocation);
    }

    public void feed(double creatureWeight) { // ����� �������� ������� ��� ��������
        this.satiety = this.satiety + creatureWeight <= this.maxSatiety ? this.satiety + creatureWeight : this.maxSatiety;
    }

    public void removeSatiety () { // ����� ��������� ������� � �������
        this.satiety -= (satiety / 100) * 30;
    }

    public boolean isFedUp () { // ����� ����������� �� ���������� �� ��������
        return getSatiety() >= getMaxSatiety();
    }

    private void eat(Creature creature, Set<Creature> potentialFood){ // ����� �������������� ������ ��������
        feed(creature.getWeight()); // ������ �������
        dissolveIntoEternity(potentialFood, creature); // ���������� ��������� �������� � ������� ��� �� ������
        if (creature instanceof Plants) { // ���� ��� �������� - � ���������� ��������
            Statistics.getCountOfEatenPlants().incrementAndGet();
        }
        else {
            Statistics.getCountOfEatenAnimals().incrementAndGet(); // ���� �������� - � ���������� ��������
        }

    }

    private void multiply(Set<Creature> creatures) {
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e) {
                    return;
                }
                var dices = ThreadLocalRandom.current().nextInt(1, 100); // ��������� ������
                if (dices <= 30) { // 30% ���� �� �����������
                    Animal babyCreature = (Animal) CreatureCreator.create(getCreatureType());
                    creatures.add(babyCreature); // ��������� �������� � ������ �������� � ������ �������
                    this.setCanMultiply(false); // ������� �������� ������ �� ����� ������������
                    runBabies(babyCreature); // ��������� ����� "����������" �������������
                }
        }


    @Override
    public String toString() {
        return this.getCreatureType() + " " + this.getId();
    }

    @SneakyThrows
    @Override
    public void run() {
        boolean isTheEndOfTheWorld = getLocation().getCreaturesService().isShutdown();  // ���������� ����������� ����������� �� ���
        boolean diedOfHunger = Statistics.getCountOfDays().get() > 1 && getSatiety() <= 0;  // ���������� ����������� �������

        Timer hunger = new Timer();     // ������, ����� ��� �������� ������� � ������������ ������
        List<Creature> potentialFood = new ArrayList<>(getFood()); // ������ �� ��������� ��������, ����� ����� ���� ������ �������� � ����� (��������� � ������ searchForFood)
        if (getDependencies().containsKey(CreatureType.CATERPILLAR)) // ���� �������� ����� ���� ������� - ��������� �� � ������ ������������� ���
        {
            potentialFood.addAll(getLocation().getCaterpillars());
        }
        while (!Thread.currentThread().isInterrupted()) {       // ���� ���� ��������, ������ ���� � �� �� ��������
            hunger.schedule(new TimerTask() {
                public void run() {
                    removeSatiety();
                    }
                }, 1000, 2000); // ����� �������� � 1 ��� (1 ����) �������� ������� �������, ������ 2 ���
            if (!isFedUp() && !potentialFood.isEmpty()) {       // ���� �������� �� �������, � ������ ������� �� ������� �� ���� - ���� ���
                searchForFood(getDependencies(), potentialFood);
            }
            if (isCanMultiply() && !isFromList().isEmpty()) {    // ���� �������� �������� � �����������, � �������� ������ ���� ������ �� ������� - ������� ������������
                multiply(isFromList());
            }
        moving();
            if (diedOfHunger || isTheEndOfTheWorld) {       // ��� ������������� ��������� ������ ����� - ������������� ������������� ��������
                dissolveIntoEternity(isFromList(), this); // �������� �������� � ����� ��������� �������� �� ������, ��������������� ���� � ������� ����������
                if (diedOfHunger) {     // ���� ���� �� ������ - ��������� ������ � ��������� � ����������
                    hunger.cancel();
                    Statistics.getCountOfDiedOfHunger().incrementAndGet();
                }
                break;
            }
        }
    }

    private void runBabies(Animal babyAnimal) {
        babyAnimal.setCanMultiply(true); // ������ ����������� �����������
        babyAnimal.setLocation(getLocation()); // �������� � ��������� ��������� ������� �������
        getLocation().getBabyService().schedule(babyAnimal, 1, TimeUnit.SECONDS); // �������� ������� ����� 1 ���� ( 1 ��� )
        Statistics.getCountOfBirths().incrementAndGet(); // ��������� ������ � ����������
    } // ����� ���������� ������������� �������

    public void destroy() { // ����� ����������� ������ ���� ��� �����-�� ���������������
        Thread.currentThread().interrupt();
    }

    public Set<Creature> getFood() {
        return this instanceof Carnivore ? getLocation().getHerbivores() : getLocation().getPlants();
    } // ����� �� ��������� ������ ��� ��� ������� ���� ���������
    public Set<Creature> isFromList() {
        return this instanceof Carnivore ? getLocation().getCarnivores() : getLocation().getHerbivores();
    } // ����� ���������� �� ������ ������ ��������
}