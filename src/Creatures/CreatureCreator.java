package Creatures;

import Creatures.Animals.Carnivore.*;
import Creatures.Animals.Herbivore.*;
import Creatures.Plants.Plants;

public abstract class CreatureCreator {

    public static Creature create (CreatureType type) {
        return switch (type) {
            case WOLF -> new Wolf(50, 3, 8);
            case BOA -> new Boa(15, 1, 3);
            case FOX -> new Fox(8, 2, 2);
            case BEAR -> new Bear(500, 2, 80);
            case EAGLE -> new Eagle(6, 3, 1);
            case HORSE -> new Horse(400,4,60);
            case DEER -> new Deer(300, 4,50);
            case RABBIT -> new Rabbit(2,2,0.45);
            case MOUSE -> new Mouse(0.05, 1, 0.01);
            case GOAT -> new Goat(60,3,10);
            case SHEEP -> new Sheep(70,3,15);
            case BOAR -> new Boar(400,2,50);
            case BUFFALO -> new Buffalo(700,3,100);
            case DUCK -> new Duck(1, 4, 0.15);
            case CATERPILLAR -> new Caterpillar();
            case PLANT -> new Plants();
        };
    }
}
