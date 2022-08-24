import Data.Island;
import Data.Location;
import Data.Statistics;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class Runner {


    public static void main(String[] args) {
        var executorServices = new ArrayList<ExecutorService>();        //список сервисов с животными
        var babyServices = new ArrayList<ExecutorService>();            //список сервисов с новорожденными животными
        for (Location[] locations : Island.getWorld()) {
            for (Location location : locations) {                       // идем по локациям - добавляем все сервисы в списки
                executorServices.add(location.getCreaturesService());
                babyServices.add(location.getBabyService());
            }
        }
        Island.revive();        // оживляем остров - запускаем нити
        System.out.println("Создано существ: " + Statistics.countOfCreatures);
        Timer lifeTimer = new Timer();   // таймер, считающий количество дней
        lifeTimer.schedule(new TimerTask() {
            public void run() {
                Days();
                if (Statistics.getCountOfDays().get() > 9) { //если прошло больше 9 дней, все останавливается
                    executorServices.forEach(ExecutorService::shutdownNow);
                    babyServices.forEach(ExecutorService::shutdownNow);
                    lifeTimer.cancel();
                    System.err.println("Мир был превращен в изначальную пустоту");
                    System.exit(0);
                }
            }
        }, 1000, 3000);
    }
    static void Days() {
        Statistics.getCountOfDays().getAndIncrement();
        System.err.println("____________________");
        System.out.println(Statistics.getInstance()); // вывод статистики за каждый день
    }
}


