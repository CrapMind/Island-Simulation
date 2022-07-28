package Creatures.Plants;

import Creatures.Creature;
import Creatures.CreatureType;
import Creatures.GlobalCreatureTypes;
import lombok.Getter;

public class Plants extends Creature {

    @Getter
    private final GlobalCreatureTypes globalCreatureTypes = GlobalCreatureTypes.PLANT;

    @Getter
    private final CreatureType creatureType = CreatureType.PLANT;

    public Plants() {
        this.setWeight(1);
    }

}
