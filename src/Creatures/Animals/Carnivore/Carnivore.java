package Creatures.Animals.Carnivore;

import Creatures.Animals.Animal;
import Creatures.GlobalCreatureTypes;
import lombok.Getter;

public abstract class Carnivore extends Animal {
    @Getter
    private final GlobalCreatureTypes globalCreatureType = GlobalCreatureTypes.CARNIVORE;

    public Carnivore(double weight, int movingSpeed, double satiety) {
        super(weight, movingSpeed, satiety);
    }

}
