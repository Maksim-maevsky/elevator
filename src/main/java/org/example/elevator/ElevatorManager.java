package org.example.elevator;


import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Data
public class ElevatorManager extends Thread {

    private static ElevatorManager elevatorManager;
    private static CopyOnWriteArrayList<User> personsOUT = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<User> personsIN = new CopyOnWriteArrayList<>();
    private static User nextStopIN;
    private static User nextStopOUT;

    private ElevatorManager() {
    }

    @SneakyThrows
    @Override
    public void run() {

        while (true) {

            Thread.sleep(500);
            int currentFloor = Elevator.getCurrentFloor();

            enterTheElevator(currentFloor);
            getOffTheElevator(currentFloor);

        }
    }

    public static void actionManage(User user) {

        synchronized (User.class) {

            if (elevatorManager == null) {

                elevatorManager = new ElevatorManager();
                elevatorManager.start();
                Elevator.launch();

            }

            personsOUT.add(user);

        }
    }

    private void enterTheElevator(int currentFloor) {

        for (User user : personsOUT) {

            boolean isNeededUserUP = getUserDirection(user);

            if (user.getCurrentFloor() == user.getNeededFloor()) {

                user.setCurrentFloor(0);
                user.setNeededFloor(0);
                personsOUT.remove(user);

                continue;

            }

            if (currentFloor == user.getCurrentFloor() && isNeededUserUP == Elevator.isIsUP()) {

                personsIN.add(user);

                log.info(user.getUserName() + " вошел в лифт на " + currentFloor +
                        " этаже, до " + user.getNeededFloor() + " этажа");

                personsOUT.remove(user);

            }
        }
    }

    private boolean getUserDirection(User p) {

        if (p.getCurrentFloor() < p.getNeededFloor()) {

            return true;

        } else if (p.getCurrentFloor() > p.getNeededFloor()) {

            return false;

        }

        return true;
    }

    @SneakyThrows
    private void getOffTheElevator(int currentFloor) {

        for (User user : personsIN) {

            if (currentFloor == user.getNeededFloor()) {

                log.info(user.getUserName() + " вышел из лифта на " + user.getNeededFloor() + " этаже");

                user.setCurrentFloor(0);
                user.setNeededFloor(0);
                personsIN.remove(user);

            }
        }
    }
}
