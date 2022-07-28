import Data.Island;
import Data.Location;
import Data.Statistics;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class Runner {


    public static void main(String[] args) {
        var executorServices = new ArrayList<ExecutorService>();        //������ �������� � ���������
        var babyServices = new ArrayList<ExecutorService>();            //������ �������� � �������������� ���������
        for (Location[] locations : Island.getWorld()) {
            for (Location location : locations) {                       // ���� �� �������� - ��������� ��� ������� � ������
                executorServices.add(location.getCreaturesService());
                babyServices.add(location.getBabyService());
            }
        }
        Island.revive();        // �������� ������ - ��������� ����
        System.out.println("������� �������: " + Statistics.countOfCreatures);
        Timer lifeTimer = new Timer();   // ������, ��������� ���������� ����
        lifeTimer.schedule(new TimerTask() {
            public void run() {
                Days();
                if (Statistics.getCountOfDays().get() > 9) { //���� ������ ������ 9 ����, ��� ���������������
                    executorServices.forEach(ExecutorService::shutdownNow);
                    babyServices.forEach(ExecutorService::shutdownNow);
                    lifeTimer.cancel();
                    System.err.println("��� ��� ��������� � ����������� �������");
                    System.exit(0);
                }
            }
        }, 1000, 3000);
    }
    static void Days() {
        Statistics.getCountOfDays().getAndIncrement();
        System.err.println("____________________");
        System.out.println(Statistics.getInstance()); // ����� ���������� �� ������ ����
    }
}


