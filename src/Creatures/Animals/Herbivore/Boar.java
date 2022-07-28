package Creatures.Animals.Herbivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Boar extends Herbivore{
    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.MOUSE, 50,
            CreatureType.CATERPILLAR, 90,
            CreatureType.PLANT, 100);
    @Getter
    private final CreatureType creatureType = CreatureType.BOAR;

    public Boar(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
        this.setId(PublicData.boarId.incrementAndGet());
    }
}
