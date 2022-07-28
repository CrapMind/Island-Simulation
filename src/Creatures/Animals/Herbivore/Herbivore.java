package Creatures.Animals.Herbivore;

import Creatures.Animals.Animal;
import Creatures.GlobalCreatureTypes;
import lombok.Getter;

public abstract class Herbivore extends Animal {
    @Getter
    private final GlobalCreatureTypes globalCreatureType = GlobalCreatureTypes.HERBIVORE;

    public Herbivore(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
    }
}
