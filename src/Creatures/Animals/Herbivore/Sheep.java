package Creatures.Animals.Herbivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Sheep extends Herbivore{
    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(CreatureType.PLANT, 100);
    @Getter
    private final CreatureType creatureType = CreatureType.SHEEP;

    public Sheep(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
        this.setId(PublicData.sheepId.incrementAndGet());
    }
}
