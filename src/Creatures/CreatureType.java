package Creatures;

public enum CreatureType {

        WOLF,
        FOX,
        BEAR,
        PLANT,
        DUCK,
        MOUSE,
        BOA,
        EAGLE,
        HORSE,
        DEER,
        RABBIT,
        GOAT,
        SHEEP,
        BOAR,
        BUFFALO,
        CATERPILLAR;

        public final static CreatureType[] herbivores = {DUCK, MOUSE, HORSE, DEER, RABBIT, GOAT, SHEEP, BOAR, BUFFALO};
        public final static CreatureType[] carnivores = {WOLF, FOX, BEAR, BOA, EAGLE};

}
