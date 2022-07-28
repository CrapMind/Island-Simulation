package Creatures.Animals.Herbivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Rabbit extends Herbivore{
    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(CreatureType.PLANT, 100);
    @Getter
    private final CreatureType creatureType = CreatureType.RABBIT;

    public Rabbit(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
        this.setId(PublicData.rabbitId.incrementAndGet());
    }
}
