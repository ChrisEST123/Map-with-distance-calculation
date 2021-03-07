package assignment;

//exercise 4 methods

public abstract class MapCoordinate implements Comparable<MapCoordinate>{
    public final double longitude;
    public final double latitude;
    public final double altitude;

    public MapCoordinate(double longitude, double latitude, double altitude){
        this.altitude = altitude;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public abstract double distanceTo(MapCoordinate mapCoordinate);

    @Override
    public int compareTo(MapCoordinate mapCoordinate) {

        int comparedResult = Double.compare(this.altitude, mapCoordinate.altitude);

        if(comparedResult == 0){
            int comparedResult1 = Double.compare(this.latitude, mapCoordinate.latitude);
            if(comparedResult1 == 0){
                return Double.compare(this.longitude, mapCoordinate.longitude);
            }
            else{
                return comparedResult1;
            }
        }
        else{
            return comparedResult;
        }
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof MapCoordinate)) {
            return false;
        }

        MapCoordinate c = (MapCoordinate) o;

        return Double.compare(longitude, c.longitude) == 0 && Double.compare(latitude, c.latitude) == 0 && Double.compare(altitude, c. altitude) == 0;
    }
}

class solidMapCoordinate extends MapCoordinate{
    public solidMapCoordinate (double latitude, double longitude, double altitude) {
        super(latitude, longitude, altitude);
    }

    @Override
    public double distanceTo(MapCoordinate mapCoordinate) {
        int r = 6378; //Earth's mean equatorial radius
        double longRad1 = Math.toRadians(this.longitude);
        double latRad1 = Math.toRadians(this.latitude);
        double longRad2 = Math.toRadians(mapCoordinate.longitude);
        double latRad2 = Math.toRadians(mapCoordinate.latitude);
        Double latDistance = latRad2-latRad1;
        Double longDistance = longRad2-longRad1;
        Double x = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(latRad1) * Math.cos(latRad2) * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        Double y = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1-x));
        double distance = r * y;
        return distance;
    }

    @Override
    public String toString() {
        return String.format("[Longitude: " + longitude + " Latitude: " + latitude + " Altitude: " + altitude + "]");
    }
}
