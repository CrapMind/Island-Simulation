package Creatures.Animals.Herbivore;

import Creatures.Creature;
import Creatures.CreatureType;
import lombok.Getter;

public class Caterpillar extends Creature {
    @Getter
    private final CreatureType creatureType = CreatureType.CATERPILLAR;

    public Caterpillar() {
        this.setWeight(0.01);
    }
}
