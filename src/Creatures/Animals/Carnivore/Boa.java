package Creatures.Animals.Carnivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Boa extends Carnivore {

    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.FOX, 15,
            CreatureType.RABBIT, 20,
            CreatureType.MOUSE, 40,
            CreatureType.DUCK, 10);

    @Getter
    private final CreatureType creatureType = CreatureType.BOA;

    public Boa(double weight, int movingSpeed, double maxSatiety) {
        super(weight, movingSpeed, maxSatiety);
        this.setId(PublicData.boaId.incrementAndGet());
    }
}
