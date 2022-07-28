package Creatures;

import Creatures.Animals.Animal;
import Data.Location;
import Data.Statistics;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

public abstract class Creature {
    @Getter
    @Setter
    private Location location; // ������� � ������� ��������� ��������
    @Getter
    private CreatureType creatureType; // ���������� ���������� ��� ���������
    @Getter
    @Setter
    private double weight; //���
    @Getter
    @Setter
    private boolean canMultiply; // ���������� �������� �������� �� ����������� �����������
    @Override
    public String toString() {
        return getCreatureType().toString();
    }
    public synchronized void dissolveIntoEternity(Set<Creature> creatures, Creature creature) { //����� ���������� �� ����������� ��������
        synchronized (Collections.unmodifiableSet(creatures)) {
            creatures.remove(creature);
            if (creature instanceof Animal animal) {
                animal.destroy(); // ���������� ��������
            }
        }
        Statistics.getCountOfDissolved().incrementAndGet();
    }
}
