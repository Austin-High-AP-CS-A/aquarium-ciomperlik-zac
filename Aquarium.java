import java.util.*;

public class Aquarium {
    // The list of all tanks in the aquarium; guaranteed never to be null
    private ArrayList<Tank> tanks;

    public Aquarium(){
        this.tanks = new ArrayList<Tank>();
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    /**
     * Returns a tank in this aquarium with a temperature fishy can tolerate and
     * that does not contain a fish that is not compatible with fishy. Returns
     * null if there is no such tank in this aquarium.
     * Postcondition: The state of this aquarium is unchanged.
     * @param fishy the fish to be checked
     * @return a suitable tank for fishy or null if no such tank exists
     */
    private Tank findTank(Fish fishy){
        for (Tank tank : tanks) {
            if (tank.temp() >= fishy.minTemp() && tank.temp() <= fishy.maxTemp()) {
                if (tank.getFish().stream().allMatch(f -> fishy.isCompatible(f))) {
                    return tank;
                }
            }
        }

        return null;
    }

    /**
     * Adds each fish in fishes to a suitable tank in this aquarium if such a
     * tank exists. Each fish should be added to at most 1 tank.
     * @param fishes the list of fish to add
     * @return a list of the fish in fishes that could not be added
     */
    public ArrayList<Fish> addFish(ArrayList<Fish> fishies) {
        ArrayList<Fish> badFishies = new ArrayList<>();

        for (Fish fish : fishies) {
            Tank tank = findTank(fish);

            if (tank == null) {
                badFishies.add(fish);
            } else {
                tank.addFish(fish);
            }
        }

        return badFishies;
    }

    /**
     * Adds fishTank to this aquarium if a suitable position can be found. The
     * temperature of fishTank can be no more than 5 degrees different (lower or
     * higher) than each of any adjacent tanks.
     * Postcondition: the order of the other tanks in the aquarium relative to each other is not changed
     * @param fishTank the tank to add
     * @return true if fishTank was added, false otherwise
     */
    public boolean addTank(Tank newTank) {
        int temp = newTank.temp();

        if (tanks.size() == 0) {
            tanks.add(newTank);
            return true;
        } else if (tanks.size() == 1) {
            if (Math.abs(tanks.get(0).temp() - temp) <= 5) {
                tanks.add(newTank);
                return true;
            }
        } else {
            for (int i = 0; i < tanks.size() - 1; i ++) {
                if (Math.abs(tanks.get(i).temp() - temp) <= 5 && Math.abs(tanks.get(i + 1).temp() - temp) <= 5) {
                    tanks.add(i + 1, newTank);
                    return true;
                }
            }

            if ((Math.abs(tanks.get(tanks.size() - 1).temp() - temp) <= 5)) {
                tanks.add(newTank);
                return true;
            }
        }

        return false;
    }
}