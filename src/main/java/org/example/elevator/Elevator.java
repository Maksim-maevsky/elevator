package org.example.elevator;


import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public final class Elevator implements Runnable {

    private static final int FLOORS_CAPACITY = 10;
    private static volatile AtomicInteger currentFloor =  new AtomicInteger(0);
    private static Elevator elevator;
    private static volatile boolean isUP = true;

    private Elevator() {

    }

    @Override
    public void run() {

        moveBetweenFloors();

    }

    @SneakyThrows
    public static void launch() {

        if (elevator == null) {

            elevator = new Elevator();
            Thread d = new Thread(elevator);
            d.start();

        }
    }


    private static void decrementCurrentFloor() {

        if (!isUP && currentFloor.get() != 1) {

            currentFloor.getAndDecrement();

        }
    }

    private static void incrementCurrentFloor() {

        if (isUP && currentFloor.get() < FLOORS_CAPACITY) {

            currentFloor.getAndIncrement();

        }
    }

    @SneakyThrows
    private static void moveBetweenFloors() {

        while (true) {

            incrementCurrentFloor();

            decrementCurrentFloor();

            log.info("CURRENT FLOOR : =======> " + Elevator.currentFloor.get());

            tryToSleep(2000);

            if (currentFloor.get() == FLOORS_CAPACITY) {

                isUP = false;

            }

            if (currentFloor.get() == 1) {

                isUP = true;

            }
        }
    }

    private static void tryToSleep(int mls) {

        try {

            Thread.sleep(mls);

        } catch (InterruptedException e) {

            log.error(e.getMessage());
            Thread.currentThread().interrupt();

        }
    }

    public static int getFloorsCapacity() {
        return FLOORS_CAPACITY;
    }

    public static int getCurrentFloor() {
        return currentFloor.get();
    }

    public static boolean isIsUP() {
        return isUP;
    }
}
