package Creatures.Animals.Carnivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Bear extends Carnivore {

    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.BOA, 80,
            CreatureType.HORSE, 40,
            CreatureType.DEER, 80,
            CreatureType.RABBIT, 80,
            CreatureType.MOUSE, 90,
            CreatureType.GOAT, 70,
            CreatureType.SHEEP, 70,
            CreatureType.BOAR, 50,
            CreatureType.BUFFALO, 20,
            CreatureType.DUCK, 10);
    @Getter
    private final CreatureType creatureType = CreatureType.BEAR;

    public Bear(double weight, int movingSpeed, double maxSatiety) {
        super(weight, movingSpeed, maxSatiety);
        this.setId(PublicData.bearId.incrementAndGet());
    }
}
