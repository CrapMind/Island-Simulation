package Data;

import lombok.Getter;

import java.util.Arrays;

public class Island {       //класс, хранящий в себе создание острова
    private static final Island island = new Island();
    @Getter
    private static final Location[][] world = createWorld(10, 10);

    private Island(){}

    private static Location[][] createWorld(int x, int y) { //Создаем мир
        var world = new Location[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                world[i][j] = new Location(i, j);
                Statistics.countOfCreatures.addAndGet(world[i][j].getCarnivores().size());
                Statistics.countOfCreatures.addAndGet(world[i][j].getHerbivores().size());
                Statistics.countOfCreatures.addAndGet(world[i][j].getPlants().size());
                Statistics.countOfCreatures.addAndGet(world[i][j].getCaterpillars().size());
            }
        }
        return world;
    }

    public static Island getInstance() {
        return island;
    }

    public static void revive() { // метод "оживляющий" мир, запускает потоки в аналогичном методе класса Location
        Arrays.stream(world).forEach(locations -> Arrays.stream(locations).forEach(Location::revive));
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Location[] locations : world) {
            for (Location location : locations) {
                result.append(location).append("\n");
            }
        }
        return result.toString();
    }
}
