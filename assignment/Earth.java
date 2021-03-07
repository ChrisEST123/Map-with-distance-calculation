package assignment;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Earth extends JComponent{
    private static double[][] newArrayOfEarth;
    private double[][] arrayOfEarth = {{1}, {2}};
    private int countOfArray = 0;
    private Map<String, Double> mapOfEarth = new HashMap<>();
    private List<Double> altitudes = new ArrayList<Double>();
    private List<Double> longitudes = new ArrayList<Double>();
    private List<Double> latitudes = new ArrayList<Double>();
    private List<Double> upperLong = new ArrayList<Double>();
    private List<Double> moddedAltitudes = new ArrayList<Double>();

    //exercise 1 methods

    public void readDataArray(String earth) throws IOException {
        File readFile = new File(earth);
        Scanner sc = new Scanner(readFile);
        countOfArray = 0;
        while (sc.hasNextLine()) {
            countOfArray++;
            sc.nextLine();
        }
        sc.close();

        arrayOfEarth = new double[countOfArray][3];
        sc = new Scanner(readFile);

        int lengthOfArray = 0;
        while (sc.hasNextLine()) {
            String[] nowLine = sc.nextLine().trim().split("\\s+");
            for (int i = 0; i < nowLine.length; i++) {
                arrayOfEarth[lengthOfArray][i] = Double.parseDouble(nowLine[i]);
            }
            lengthOfArray++;
        }
        newArrayOfEarth = arrayOfEarth;
    }

    public List<double[]> coordinatesAbove(double altitude) {

        List<double[]> coorAbove = new ArrayList<>();
        for (int i = 0; i < countOfArray; i++) {
            int colNumber = 3;
            for (int j = 0; j < colNumber; j++) {
                if (j == 2) {
                    if (arrayOfEarth[i][j] > altitude) {
                        double[] tempAbove = arrayOfEarth[i];
                        coorAbove.add(tempAbove);
                    }
                }
            }
        }
        return coorAbove;
    }

    public List<double[]> coordinatesBelow(double altitude) {
        List<double[]> coorBelow = new ArrayList<>();
        for (int i = 0; i < countOfArray; i++) {
            int colNumber = 3;
            for (int j = 0; j <= colNumber; j++) {
                if (j == 2) {
                    if (arrayOfEarth[i][j] < altitude) {
                        double[] tempBelow = arrayOfEarth[i];
                        coorBelow.add(tempBelow);
                    }
                }
            }
        }
        return coorBelow;
    }

    public void percentageAbove(double altitude) {
        List<double[]> coorAbove = coordinatesAbove(altitude);
        double workingCount = countOfArray;
        double percentAbove = 0;
        percentAbove = Math.round((coorAbove.size() * 100 / workingCount) * 10.0) / 10.0;
        System.out.println("Proportion of coordinates above " + altitude + " meters: " + percentAbove);
    }

    public void percentageBelow(double altitude) {
        List<double[]> coorBelow = coordinatesBelow(altitude);
        double workingCount = countOfArray;
        double percentBelow = 0;
        percentBelow = Math.round((coorBelow.size() * 100 / workingCount) * 10.0) / 10.0;
        System.out.println("Proportion of coordinates below " + altitude + " meters: " + percentBelow);
    }

    // exercise 2 methods

    public void readDataMap(String earth) throws IOException {
        File readFile = new File(earth);
        Scanner sc = new Scanner(readFile);
        while (sc.hasNextLine()) {
            String[] mapParts = sc.nextLine().trim().split("\\s+", 3);
            if (mapParts.length >= 3) {
                String key = mapParts[0] + " " + mapParts[1];
                double value = Double.parseDouble(mapParts[2]);
                mapOfEarth.put(key, value);
            }
        }
    }

    public double getAltitude(double longitude, double latitude){
        if (longitude % 1 == 0 && latitude % 1 == 0) {
            int longi = 0;
            longi = (int) longitude;
            int lat = 0;
            lat = (int) latitude;
            String insertedKey = "";
            insertedKey = longi + " " + lat;
            double foundAlt = 0;
            if (mapOfEarth.containsKey(insertedKey)) {
                foundAlt = mapOfEarth.get(insertedKey);
                System.out.println("The altitude at longitude " + longitude + " and latitude " + latitude + " is " + foundAlt + " meters.");
                return foundAlt;
            } else {
                System.out.println("Such a value does not exist in the map.");
                return foundAlt;
            }
        } else if (longitude % 1 == 0) {
            int longi = 0;
            longi = (int) longitude;
            String insertedKey = "";
            insertedKey = longi + " " + latitude;
            double foundAlt = 0;
            if (mapOfEarth.containsKey(insertedKey)) {
                foundAlt = mapOfEarth.get(insertedKey);
                System.out.println("The altitude at longitude " + longitude + " and latitude " + latitude + " is " + foundAlt + " meters.");
                return foundAlt;
            } else {
                System.out.println("Such a value does not exist in the map.");
                return foundAlt;
            }
        } else if (latitude % 1 == 0) {
            int lat = 0;
            lat = (int) latitude;
            String insertedKey = "";
            insertedKey = longitude + " " + lat;
            double foundAlt = 0;
            if (mapOfEarth.containsKey(insertedKey)) {
                foundAlt = mapOfEarth.get(insertedKey);
                System.out.println("The altitude at longitude " + longitude + " and latitude " + latitude + " is " + foundAlt + " meters.");
                return foundAlt;
            } else {
                System.out.println("Such a value does not exist in the map.");
                return foundAlt;
            }
        } else {
            String insertedKey = "";
            insertedKey = longitude + " " + latitude;
            double foundAlt = 0;
            if (mapOfEarth.containsKey(insertedKey)) {
                foundAlt = mapOfEarth.get(insertedKey);
                System.out.println("The altitude at longitude " + longitude + " and latitude " + latitude + " is " + foundAlt + " meters.");
                return foundAlt;
            } else {
                System.out.println("Such a value does not exist in the map.");
                return foundAlt;
            }
        }
    }

    public void generateMap(double resolution) {
        double longitude = 360;
        double latitude = 90;
        double tempLongitude = 0;
        double tempLatitude = -90;
        while (tempLongitude <= longitude) {
            while (tempLatitude <= latitude) {
                Random randAlt = new Random();
                int newAlt = randAlt.nextInt(5000 - (-7000) + 1) + (-7000);
                String key = tempLongitude + " " + tempLatitude;
                double value = Double.parseDouble(String.valueOf(newAlt));

                mapOfEarth.put(key, value);
                tempLatitude = tempLatitude + resolution;
            }
            tempLongitude = tempLongitude + resolution;
            tempLatitude = 0;
        }
    }

    // methods for exercise 3


    public void initEarthMap() {
        for (int i = 0; i < countOfArray; i++) {
            int colNumber = 3;
            for (int j = 0; j <= colNumber; j++) {
                if (j == 2) {
                    double tempAlt = arrayOfEarth[i][j];
                    altitudes.add(tempAlt);
                } else if (j == 0) {
                    double tempLong = arrayOfEarth[i][j];
                    longitudes.add(tempLong);
                    if (tempLong > 180) {
                        upperLong.add(arrayOfEarth[i][j]);
                    }
                } else if (j == 1) {
                    double tempLat = arrayOfEarth[i][j];
                    latitudes.add(tempLat);
                }
            }
        }
    }

    public void seaLevels(int mod){
        for (int i = 0; i < countOfArray; i++) {
            int colNumber = 3;
            for (int j = 0; j <= colNumber; j++) {
                if (j == 2) {
                    double tempAlt = arrayOfEarth[i][j] - mod;
                    newArrayOfEarth[i][j] = tempAlt;
                    moddedAltitudes.add(tempAlt);
                }
            }
        }
    }

    public void writeFile() throws IOException {
        File fout = new File("src/earth2.xyz");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < newArrayOfEarth.length; i++) {
            for (int j = 0; j < 3; j++) {
                bw.write(Double.toString(newArrayOfEarth[i][j]));
                bw.write(" ");
            }
            bw.newLine();
        }
        bw.close();
    }

    // exercise 5 side method, required to convert actual x and y coordinates from click into the coordinates given in the file.

    public List<Double> getCoordinates(int x, int y) throws IOException {
        readDataArray("src/earth2.xyz");
        List<Double> foundCoordinates = new ArrayList<Double>();
        double foundLong = 0;
        double foundLat = 0;
        double foundAlt = 0;
        double longX = (x / 0.6) * 1/6;
        double latY = (y / 0.6) * 1/6;
        if(latY <= 90){
            latY = 90-latY;
        }else{
            latY = (latY * (-1)) + 90;
        }
        if(longX % 1 == 0){
            longX = (int) longX;
        }else if(longX % 0.5 == 0){
            longX = longX;
        }else{
            longX = Math.round(longX * 1000000000) / 1000000000;
        }
        if(latY % 1 == 0){
            latY = (int) latY;
        }else if(latY % 0.5 == 0){
            latY = latY;
        }else{
            latY = Math.round(latY * 1000000000) / 1000000000;
        }
        for(int i=0; i<newArrayOfEarth.length; i++){
            if(longX == newArrayOfEarth[i][0] && latY == newArrayOfEarth[i][1]){
                foundLong = newArrayOfEarth[i][0];
                foundLat = newArrayOfEarth[i][1];
                foundAlt = newArrayOfEarth[i][2];
                foundCoordinates.add(foundLong);
                foundCoordinates.add(foundLat);
                foundCoordinates.add(foundAlt);
            }
        }
        return foundCoordinates;
    }

    // exercise 3, visualisation of data

    public JPanel panel;

    static class Panel extends JPanel {

        Panel() {
            setPreferredSize(new Dimension(1400, 720));
            setLayout(new GridLayout(180 * 6, 360 * 6, 0, 0));
        }

        Earth xyz = new Earth();

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            Color startColor1 = new Color(0x12FF01);
            Color endColor1 = new Color(0x7B3D2A);
            Color startColor2 = new Color(0x68CAFF);
            Color endColor2 = new Color(0x08497F);

            double overallX = (360 * 6) * 0.6;
            double upperNowX = overallX / 2;
            double lowerNowX = 0;
            double nowY = 0;

            double longit = 0;
            double latit = 0;
            double altit = 0;

            try {
                xyz.readDataMap("src/earth2.xyz");
                xyz.readDataArray("src/earth2.xyz");
            } catch (IOException e) {
                e.printStackTrace();
            }

            xyz.initEarthMap();


            double maxAlt = Collections.max(xyz.altitudes);
            double minAlt = Collections.min(xyz.altitudes);
            double usableMinAlt = minAlt * (-1);

            for (int i = 0; i < xyz.newArrayOfEarth.length; i++) {
                int colNumber = 3;
                for (int j = 0; j <= colNumber; j++) {
                    if (j == 2) {
                        double tempAlt = xyz.newArrayOfEarth[i][j];
                        altit = tempAlt;
                    } else if (j == 0) {
                        double tempLong = xyz.newArrayOfEarth[i][j];
                        longit = tempLong;
                    } else if (j == 1) {
                        double tempLat = xyz.newArrayOfEarth[i][j];
                        latit = tempLat;
                    }
                }
                if (longit <= 180) {
                    if (altit >= 0) {
                        if (latit <= -60) {
                            startColor1 = new Color(0x8D8C8C);
                            endColor1 = new Color(0xFFFFFF);
                        }
                        double ratio = altit / maxAlt;
                        int red = (int) (endColor1.getRed() * ratio + startColor1.getRed() * (1 - ratio));
                        int green = (int) (endColor1.getGreen() * ratio + startColor1.getGreen() * (1 - ratio));
                        int blue = (int) (endColor1.getBlue() * ratio + startColor1.getBlue() * (1 - ratio));
                        Color c = new Color(red, green, blue);
                        Rectangle2D r = new Rectangle2D.Double(upperNowX, nowY, 0.6, 0.6);
                        g2d.setPaint(c);
                        g2d.fill(r);
                        upperNowX = upperNowX + 0.6;
                        if (upperNowX > overallX) {
                            upperNowX = overallX / 2;
                            nowY = nowY + 0.6;
                        }
                    } else {
                        altit = altit * (-1);
                        double ratio = altit / usableMinAlt;
                        int red = (int) (endColor2.getRed() * ratio + startColor2.getRed() * (1 - ratio));
                        int green = (int) (endColor2.getGreen() * ratio + startColor2.getGreen() * (1 - ratio));
                        int blue = (int) (endColor2.getBlue() * ratio + startColor2.getBlue() * (1 - ratio));
                        Color c = new Color(red, green, blue);
                        Rectangle2D r = new Rectangle2D.Double(upperNowX, nowY, 0.6, 0.6);
                        g2d.setPaint(c);
                        g2d.fill(r);
                        upperNowX = upperNowX + 0.6;
                        if (upperNowX > overallX) {
                            upperNowX = overallX / 2;
                            nowY = nowY + 0.6;
                        }
                    }
                } else {
                    if (altit >= 0) {
                        if (latit <= -60) {
                            startColor1 = new Color(0x8D8C8C);
                            endColor1 = new Color(0xFFFFFF);
                        }
                        double ratio = altit / maxAlt;
                        int red = (int) (endColor1.getRed() * ratio + startColor1.getRed() * (1 - ratio));
                        int green = (int) (endColor1.getGreen() * ratio + startColor1.getGreen() * (1 - ratio));
                        int blue = (int) (endColor1.getBlue() * ratio + startColor1.getBlue() * (1 - ratio));
                        Color c = new Color(red, green, blue);
                        Rectangle2D r = new Rectangle2D.Double(lowerNowX, nowY, 0.6, 0.6);
                        g2d.setPaint(c);
                        g2d.fill(r);
                        lowerNowX = lowerNowX + 0.6;
                        if (lowerNowX > (overallX / 2)) {
                            lowerNowX = 0;
                        }
                    } else {
                        altit = altit * (-1);
                        double ratio = altit / usableMinAlt;
                        int red = (int) (endColor2.getRed() * ratio + startColor2.getRed() * (1 - ratio));
                        int green = (int) (endColor2.getGreen() * ratio + startColor2.getGreen() * (1 - ratio));
                        int blue = (int) (endColor2.getBlue() * ratio + startColor2.getBlue() * (1 - ratio));
                        Color c = new Color(red, green, blue);
                        Rectangle2D r = new Rectangle2D.Double(lowerNowX, nowY, 0.6, 0.6);
                        g2d.setPaint(c);
                        g2d.fill(r);
                        lowerNowX = lowerNowX + 0.6;
                        if (lowerNowX > (overallX / 2)) {
                            lowerNowX = 0;
                        }
                    }
                }
            }
        }
    }

    // the main "remote" for the whole programme.

    public static void main(String[] args) throws IOException{
        Earth xyz = new Earth();
        xyz.readDataArray("src/earth.xyz");
        xyz.readDataMap("src/earth.xyz");
        xyz.writeFile();

        boolean run = true;

        while (run == true) {
            boolean run1 = true;
            boolean run2 = true;
            boolean run3 = true;
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter 1 for percentage, 2 for altitude, write map to see the map or write quit to end the program: ");
            if (input.hasNext(String.valueOf(1))) {
                while (run1 == true) {
                    int altitude;
                    Scanner input1 = new Scanner(System.in);
                    System.out.println("Please enter an altitude: ");
                    if (input1.hasNextInt()) {
                        altitude = input1.nextInt();
                        xyz.percentageAbove(altitude);
                        run1 = false;
                    } else {
                        System.out.println("Invalid altitude. Please enter a valid altitude.");
                    }
                }
            } else if (input.hasNext(String.valueOf(2))) {
                while (run2 == true) {
                    double longitude = 0;
                    double latitude = 0;
                    Scanner input2 = new Scanner(System.in);
                    System.out.println("Please enter a longitude (0-360) and latitude (-90-90): ");
                    String written = input2.nextLine();
                    try {
                        String[] givenCoor = written.split(" ", 2);
                        longitude = Double.parseDouble(givenCoor[0]);
                        latitude = Double.parseDouble(givenCoor[1]);
                        if (longitude >= 0 && longitude <= 360) {
                            if (latitude >= -90 && latitude <= 90) {
                                xyz.getAltitude(longitude, latitude);
                                run2 = false;
                            } else {
                                System.out.println("Enter a valid latitude.");
                            }
                        } else {
                            System.out.println("Enter a valid longitude.");
                        }
                    } catch (NumberFormatException ime) {
                        System.out.println("Please enter a valid longitude and latitude.");
                    }
                }
            } else if (input.hasNext()) {
                String falsealt = input.next();
                if (falsealt.equalsIgnoreCase("quit")) {
                    System.out.println("Bye!");
                    run = false;
                } else if (falsealt.equalsIgnoreCase("map")) {
                    while(run3 == true){
                        Scanner input3 = new Scanner(System.in);
                        System.out.println("Do you want to go back to the first choices and see the normal map or do you want to rise/lower sea level? (yes to go back/no for sea levels)");
                        String mapString = input3.nextLine();
                        if (mapString.equalsIgnoreCase("no")) {
                            Scanner input4 = new Scanner(System.in);
                            System.out.println("Please insert how much do you want to lower or rise the sea levels. For lowering write in negative numbers.");
                            if (input4.hasNextInt()) {
                                int change = input4.nextInt();
                                xyz.seaLevels(change);
                                xyz.writeFile();
                                System.out.println("Use your mouses left click to add a coordinate to a list. Press 2 locations, and a distance between" +
                                        " the locations shall be generated. Press right click to delete your last coordinate from the list. When you close the window" +
                                        "then the remaining coordinate click history shall be printed to a file named clickResults.txt in the src folder.");
                                Frame.jFrame();
                                run3 = false;
                            } else {
                                System.out.println("Invalid value. Please enter a valid value.");
                            }
                        } else if (mapString.equalsIgnoreCase("yes")) {
                            run3 = false;
                            xyz.readDataMap("src/earth.xyz");
                            xyz.readDataArray("src/earth.xyz");
                            xyz.writeFile();
                            System.out.println("Use your mouses left click to add a coordinate to a list. Press 2 locations, and a distance between" +
                                    " the locations shall be generated. Press right click to delete your last coordinate from the list. When you close the window" +
                                    "then the remaining coordinate click history shall be printed to a file named clickResults.txt in the src folder.");
                            Frame.jFrame();
                        } else {
                            System.out.println("Invalid value. Please enter a given value.");
                        }
                    }
                } else {
                    System.out.println("Invalid value. Please enter a given value or \"quit\" to end program.");
                }
            }
        }
    }
}

// exercise 3 and 5, some instances from exercise 4 as well

class Frame extends JFrame implements MouseListener, WindowListener {
    List<MapCoordinate> allPressedCoords = new ArrayList<MapCoordinate>();
    Earth xyz = new Earth();
    private int clicks = 1;
    double latitude = 0;
    double longitude = 0;
    double altitude = 0;
    double latitude2 = 0;
    double longitude2 = 0;
    double altitude2 = 0;
    public MapCoordinate coor = new solidMapCoordinate(longitude, latitude, altitude);
    public MapCoordinate coor2 = new solidMapCoordinate(longitude2, latitude2, altitude2);
    public MapCoordinate removed = new solidMapCoordinate(longitude, latitude, altitude);

    public void mouseClicked(MouseEvent e) { }

    Frame(){
        xyz.panel = new Earth.Panel();
        JFrame frame = new JFrame("Earth Map");

        addMouseListener(this);
        addWindowListener(this);
        frame.addWindowListener(this);
        xyz.panel.addMouseListener(this);


        frame.setContentPane(xyz.panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    // start of exercise 5 only methods

    public void removeRow() {
        for(int i = 0; i <= allPressedCoords.size(); i++){
            if(i == allPressedCoords.size()){
                allPressedCoords.remove(i);
            }
        }
    }

    public void writeFile2() throws IOException {
        File fout = new File("src/clickResults.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < allPressedCoords.size(); i++) {
            bw.write(allPressedCoords.get(i).toString());
            bw.newLine();
        }
        bw.close();
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == java.awt.event.MouseEvent.BUTTON3){
            for(int i = 0; i <= allPressedCoords.size(); i++){
                if(allPressedCoords.size() == 0){
                    System.out.println("No values to delete!");
                }
                else if(i == allPressedCoords.size()){
                    removed = allPressedCoords.get(i-1);
                    allPressedCoords.remove(i-1);
                    System.out.println("Successfully removed the following coordinate from the list: " + removed.toString());
                }
            }
        }else if(e.getButton() == java.awt.event.MouseEvent.BUTTON1){
            if(clicks == 1){
                int currentX = e.getX();
                int currentY = e.getY();
                List<Double> getCoord = null;
                try {
                    getCoord = xyz.getCoordinates(currentX, currentY);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if(getCoord == null){
                    System.out.println("Do not click out of bounds please!");
                }else{
                    longitude = getCoord.get(0);
                    latitude = getCoord.get(1);
                    altitude = getCoord.get(2);
                    coor = new solidMapCoordinate(longitude, latitude, altitude);
                    allPressedCoords.add(coor);
                    System.out.println("You clicked on these coordinates: Longitude: " + longitude + " Latitude: " + latitude + " Altitude: " + altitude);
                    clicks = 2;
                }
            }else if(clicks == 2){
                int currentX2 = e.getX();
                int currentY2 = e.getY();
                List<Double> getCoord = null;
                try {
                    getCoord = xyz.getCoordinates(currentX2, currentY2);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if(getCoord == null){
                    System.out.println("Do not click out of bounds please!");
                }else{
                    longitude2 = getCoord.get(0);
                    latitude2 = getCoord.get(1);
                    altitude2 = getCoord.get(2);
                    coor2 = new solidMapCoordinate(longitude2, latitude2, altitude2);
                    System.out.println("You clicked on these coordinates: Longitude: " + longitude2 + " Latitude: " + latitude2 + " Altitude: " + altitude2);
                    System.out.println("The distance between the 2 points is " + coor2.distanceTo(coor) + " kilometers.");
                    allPressedCoords.add(coor2);
                    clicks = 3;
                }
            }else{
                int currentX = e.getX();
                int currentY = e.getY();
                List<Double> getCoord = null;
                try {
                    getCoord = xyz.getCoordinates(currentX, currentY);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if(getCoord == null){
                    System.out.println("Do not click out of bounds please!");
                }else{
                    longitude = getCoord.get(0);
                    latitude = getCoord.get(1);
                    altitude = getCoord.get(2);
                    coor = new solidMapCoordinate(longitude, latitude, altitude);
                    System.out.println("You clicked on these coordinates: Longitude: " + longitude + " Latitude: " + latitude + " Altitude: " + altitude);
                    System.out.println("The distance between the 2 points is " + coor.distanceTo(coor2) + " kilometers.");
                    allPressedCoords.add(coor);
                    clicks = 2;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) { }

    public void mouseEntered(MouseEvent mouseEvent) { }

    public void mouseExited(MouseEvent mouseEvent) { }

    @Override
    public void windowOpened(WindowEvent windowEvent) { }

    @Override
    public void windowClosing(WindowEvent windowEvent) { }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        Collections.sort(allPressedCoords);
        try {
            writeFile2();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter 1 for percentage, 2 for altitude, write map to see the map or write quit to end the program: ");
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) { }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) { }

    @Override
    public void windowActivated(WindowEvent windowEvent) { }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) { }

    //initiates the window for exercise 3 and 5

    public static void jFrame(){
        new Frame();
    }
}

