package Creatures;

import Creatures.Animals.Animal;
import Data.Location;
import Data.Statistics;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

public abstract class Creature {
    @Getter
    @Setter
    private Location location; // локация в которой находится существо
    @Getter
    private CreatureType creatureType; // переменная содержащая тип животного
    @Getter
    @Setter
    private double weight; //вес
    @Getter
    @Setter
    private boolean canMultiply; // переменная хранящая значение на возможность размножения
    @Override
    public String toString() {
        return getCreatureType().toString();
    }
    public synchronized void dissolveIntoEternity(Set<Creature> creatures, Creature creature) { //метод отвечающий за уничтожение существа
        synchronized (Collections.unmodifiableSet(creatures)) {
            creatures.remove(creature);
            if (creature instanceof Animal animal) {
                animal.destroy(); // уничтожаем животное
            }
        }
        Statistics.getCountOfDissolved().incrementAndGet();
    }
}
