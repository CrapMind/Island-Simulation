package Creatures.Animals.Carnivore;

import Creatures.CreatureType;
import Data.PublicData;
import lombok.Getter;

import java.util.Map;

public class Wolf extends Carnivore {

    @Getter
    private final Map<CreatureType, Integer> dependencies = Map.of(
            CreatureType.HORSE, 10,
            CreatureType.DEER, 15,
            CreatureType.RABBIT, 60,
            CreatureType.MOUSE, 80,
            CreatureType.GOAT, 60,
            CreatureType.SHEEP, 70,
            CreatureType.BOAR, 15,
            CreatureType.BUFFALO, 10,
            CreatureType.DUCK, 40);
    @Getter
    private final CreatureType creatureType = CreatureType.WOLF;

    public Wolf(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
        this.setId(PublicData.wolfId.incrementAndGet());
    }
}
