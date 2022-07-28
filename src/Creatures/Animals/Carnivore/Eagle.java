package Creatures.Animals.Carnivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Eagle extends Carnivore {
    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.FOX, 10,
            CreatureType.RABBIT, 90,
            CreatureType.MOUSE, 90,
            CreatureType.DUCK, 80);
    @Getter
    private final CreatureType creatureType = CreatureType.EAGLE;

    public Eagle(double weight, int movingSpeed, double maxSatiety) {
        super(weight, movingSpeed, maxSatiety);
        this.setId(PublicData.eagleId.incrementAndGet());
    }
}
