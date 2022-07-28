package Creatures.Animals.Herbivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Mouse extends Herbivore{

    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.CATERPILLAR, 90,
            CreatureType.PLANT, 100);
    @Getter
    private final CreatureType creatureType = CreatureType.MOUSE;

    public Mouse(double weight, int movingSpeed, double maxSatiety) {
        super(weight, movingSpeed, maxSatiety);
        this.setId(PublicData.mouseId.incrementAndGet());
    }
}
