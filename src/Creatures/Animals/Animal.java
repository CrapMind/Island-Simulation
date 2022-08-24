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
    private volatile int id;// внутренняя переменная, нужна была для изначальных проверок, просто не чистил
    @Getter
    private double maxSatiety; // максимальная сытость
    @Getter
    @Setter
    private volatile double satiety; // сытость
    @Getter
    private volatile double weight; // вес
    @Getter
    private int movingSpeed; // максимальное количество клеток, на которые животное может походить
    @Getter
    private Map<CreatureType, Integer> dependencies; // мапа содержащая значения: тип животных, шанс съедения

    public Animal(double weight, int movingSpeed, double maxSatiety) {
        this.weight = weight;
        this.movingSpeed = movingSpeed;
        this.maxSatiety = maxSatiety;
    }

    public void searchForFood(Map<CreatureType, Integer> dependencies, List<Creature> potentialFood) { // Поиск еды. В параметрах мапа с типами животных и шансом съедения, и список животных для съедения
        try {
            Thread.sleep(200);
        }catch (InterruptedException e) {
            return;
        }
        synchronized (Collections.unmodifiableList(potentialFood)) {
            for (Creature creature : potentialFood) {
                if (dependencies.containsKey(creature.getCreatureType())) { //проверяем есть ли в мапе животное нужного типа
                    var dices = ThreadLocalRandom.current().nextInt(1, 100); // рандомные кубики
                    if (dices <= dependencies.get(creature.getCreatureType())) { // если шанс съедения совпадает с шансом в мапе - кушаем
                        eat(creature, creature instanceof Caterpillar ? getLocation().getCaterpillars() : getFood()); // если едим гусеницу - передаем список гусениц
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
        var dices = ThreadLocalRandom.current().nextInt(0, getMovingSpeed()); // рандомные кубики от 0 до максимально возможного хождения
        for (int i = 0; i < dices; i++) {
            Direction direction = Direction.directions[ThreadLocalRandom.current().nextInt(0, Direction.directions.length)]; // получаем случайное направление
            if (!getLocation().isExist(getLocation().locationByDirection(direction))) { // если локация не существует - пытаемся идти снова
                i--;
                continue;
            }
            goToLocation(getLocation().locationByDirection(direction)); // идем по переданному направлению
        }
    }

    private void goToLocation(Location nextLocation) { // метод меняющий значение локации у животного при удачном переходе
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

    public void feed(double creatureWeight) { // метод меняющий сытость при съедании
        this.satiety = this.satiety + creatureWeight <= this.maxSatiety ? this.satiety + creatureWeight : this.maxSatiety;
    }

    public void removeSatiety () { // метод убирающий сытость в таймере
        this.satiety -= (satiety / 100) * 30;
    }

    public boolean isFedUp () { // метод проверяющий не насытилось ли животное
        return getSatiety() >= getMaxSatiety();
    }

    private void eat(Creature creature, Set<Creature> potentialFood){ // метод координирующий списки животных
        feed(creature.getWeight()); // меняем сытость
        dissolveIntoEternity(potentialFood, creature); // уничтожаем съеденное существо и убираем его из списка
        if (creature instanceof Plants) { // если это растение - в статистику растений
            Statistics.getCountOfEatenPlants().incrementAndGet();
        }
        else {
            Statistics.getCountOfEatenAnimals().incrementAndGet(); // если животное - в статистику животных
        }

    }

    private void multiply(Set<Creature> creatures) {
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e) {
                    return;
                }
                var dices = ThreadLocalRandom.current().nextInt(1, 100); // рандомные кубики
                if (dices <= 30) { // 30% шанс на размножение
                    Animal babyCreature = (Animal) CreatureCreator.create(getCreatureType());
                    creatures.add(babyCreature); // добавляем животное в список животных в данной локации
                    this.setCanMultiply(false); // текущее животное больше не может размножаться
                    runBabies(babyCreature); // запускаем метод "оживляющий" новорожденных
                }
        }


    @Override
    public String toString() {
        return this.getCreatureType() + " " + this.getId();
    }

    @SneakyThrows
    @Override
    public void run() {
        boolean isTheEndOfTheWorld = getLocation().getCreaturesService().isShutdown();  // переменная проверяющая завершились ли дни
        boolean diedOfHunger = Statistics.getCountOfDays().get() > 1 && getSatiety() <= 0;  // переменная проверяющая сытость

        Timer hunger = new Timer();     // таймер, нужен для снижения сытости в определенный период
        List<Creature> potentialFood = new ArrayList<>(getFood()); // список из множества животных, чтобы можно было удобно работать в цикле (конкретно в методе searchForFood)
        if (getDependencies().containsKey(CreatureType.CATERPILLAR)) // если животное может есть гусениц - добавляем их в список потенциальной еды
        {
            potentialFood.addAll(getLocation().getCaterpillars());
        }
        while (!Thread.currentThread().isInterrupted()) {       // пока нить работает, делаем одни и те же действия
            hunger.schedule(new TimerTask() {
                public void run() {
                    removeSatiety();
                    }
                }, 1000, 2000); // после задержки в 1 сек (1 день) начинаем снижать сытость, каждые 2 дня
            if (!isFedUp() && !potentialFood.isEmpty()) {       // если животное не голодно, и список существ на локации не пуст - ищем еду
                searchForFood(getDependencies(), potentialFood);
            }
            if (isCanMultiply() && !isFromList().isEmpty()) {    // если животное способно к размножению, и животные такого типа выжили на локации - пробуем размножаться
                multiply(isFromList());
            }
        moving();
            if (diedOfHunger || isTheEndOfTheWorld) {       // при вышеописанных возможных концах жизни - останавливаем существование индивида
                dissolveIntoEternity(isFromList(), this); // передаем животное в метод удаляющий животное из списка, останавливающий нить и ведущий статистику
                if (diedOfHunger) {     // если умер от голода - завершаем таймер и добавляем в статистику
                    hunger.cancel();
                    Statistics.getCountOfDiedOfHunger().incrementAndGet();
                }
                break;
            }
        }
    }

    private void runBabies(Animal babyAnimal) {
        babyAnimal.setCanMultiply(true); // задаем возможность размножения
        babyAnimal.setLocation(getLocation()); // передаем в параметры животного текущую локацию
        getLocation().getBabyService().schedule(babyAnimal, 1, TimeUnit.SECONDS); // животное оживает через 1 день ( 1 сек )
        Statistics.getCountOfBirths().incrementAndGet(); // добавляем данные в статистику
    } // метод оживляющих новорожденных существ

    public void destroy() { // метод завершающий работу нити при каких-то обстоятельствах
        Thread.currentThread().interrupt();
    }

    public Set<Creature> getFood() {
        return this instanceof Carnivore ? getLocation().getHerbivores() : getLocation().getPlants();
    } // метод на получение списка еды для данного типа животного
    public Set<Creature> isFromList() {
        return this instanceof Carnivore ? getLocation().getCarnivores() : getLocation().getHerbivores();
    } // метод определяет из какого списка животное
}