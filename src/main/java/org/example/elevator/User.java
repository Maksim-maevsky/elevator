package org.example.elevator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.math3.random.RandomDataGenerator;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Thread {

    private String userName;

    private volatile int currentFloor = 0;
    private volatile int neededFloor = 0;

    public User(String name) {

        this.userName = name;
    }

    @Override
    public void run() {

        while (true) {

            if (currentFloor == 0 && neededFloor == 0) {

                goToElevator();

            }
        }
    }

    private synchronized void goToElevator() {

        randomizer();
        ElevatorManager.actionManage(this);

    }

    private void randomizer() {

        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        currentFloor = randomDataGenerator.nextInt(1, Elevator.getFloorsCapacity());
        neededFloor = randomDataGenerator.nextInt(1, Elevator.getFloorsCapacity());

    }
}

