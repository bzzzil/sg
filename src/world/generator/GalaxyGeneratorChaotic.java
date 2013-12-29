package world.generator;

import util.Coordinate;
import util.StarArray;
import util.StarName;
import util.Trace;
import world.Galaxy;
import world.Star;

public class GalaxyGeneratorChaotic implements GalaxyGenerator {
    private int maxDistance;
    private int minDistance;

    public GalaxyGeneratorChaotic(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public StarArray generate() {
        Trace.message("1. God #" + Math.abs(Galaxy.getGenerator().nextInt())
                + " made heaven & earth ");

        int count = Galaxy.getGenerator().nextInt(2000)+200;

        StarArray stars = new StarArray();

        for (int i = 0; i < count; i++) {
            Star currentStar = new Star();

            // Generate star location: not so far (maxDistance) from previous
            // and not so close (minDistance) to any another
            if (i == 0) {
                currentStar.getLocation().set(0, 0);
            } else {
                // Star position
                Star previousStar = stars.get(i - 1);

                Coordinate location = new Coordinate();
                int iteration = 0;
                int currentMaxDistance = this.maxDistance;
                do {
                    location.setX(previousStar.getLocation().getX()
                            + Galaxy.getGenerator().nextInt(currentMaxDistance * 2)
                            - currentMaxDistance);
                    location.setY(previousStar.getLocation().getY()
                            + Galaxy.getGenerator().nextInt(currentMaxDistance * 2)
                            - currentMaxDistance);
                    if (iteration++ > 10) {
                        currentMaxDistance *= 2;
                    }
                } while (stars.getNearestDistance(location) < this.minDistance);
                currentStar.getLocation().set(location);
            }

            // Star temperature
            // Star temperature should be in 2000-40000K
            // http://en.wikipedia.org/wiki/Stellar_classification
            //
            int temperature = (int)(Math.abs(Galaxy.getGenerator().nextGaussian()*7000) + 2000);
            if (temperature>40000)
            {
                temperature = 40000;
            }
            else if (temperature < 2200)
            {
                // Special case for classes below M
                temperature = Galaxy.getGenerator().nextInt(2000);
            }
            currentStar.setTemperature(temperature);

            // Generate and set unique star name
            String currentStarName;
            do {
                currentStarName = StarName.getName();
            } while (stars.getByName(currentStarName) != null);
            currentStar.setName(currentStarName);

            currentStar.setId(i);

            currentStar.create();

            Trace.message(currentStar.toString());

            stars.add(i, currentStar);
        }

        Trace.message("2. " + stars.size() + " stars are shining brightly");


        Trace.message("3. Universe is awaiting you");

        return stars;
    }
}
