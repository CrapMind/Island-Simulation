package Creatures.Animals.Herbivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Duck extends Herbivore {

    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.CATERPILLAR, 90,
            CreatureType.PLANT, 100);
    @Getter
    private final CreatureType creatureType = CreatureType.DUCK;

    public Duck(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
        this.setId(PublicData.duckId.incrementAndGet());
    }
}
