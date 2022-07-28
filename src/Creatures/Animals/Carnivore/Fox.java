package Creatures.Animals.Carnivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Fox extends Carnivore{

    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.RABBIT, 70,
            CreatureType.MOUSE, 90,
            CreatureType.DUCK, 60,
            CreatureType.CATERPILLAR, 40);

    @Getter
    private final CreatureType creatureType = CreatureType.FOX;

    public Fox(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
        this.setId(PublicData.foxId.incrementAndGet());
    }
}
