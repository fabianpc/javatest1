import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Route manager
 */
public class RouteManager {
    /**
     * Route list load from file
     */
    private List<Route> routeList = new ArrayList<Route>();
    /**
     * Trip list over graph
     */
    private HashMap<String, Integer> tripList = new HashMap<String, Integer>();

    /**
     * Route manager constructor
     */
    public RouteManager() {
        String filename = System.getProperty("user.dir")+"/graph.txt";
        try {
            File routesFile = new File(filename);
            Scanner myReader = new Scanner(routesFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.length() != 3)
                    System.out.println("No valid route!");
                else {
                    String city_from = data.substring(0,1);
                    String city_to = data.substring(1,2);
                    Integer distance = Integer.parseInt(data.substring(2,3));
                    Route route = new Route(city_from, city_to, distance);
                    routeList.add(route);
                }
            }
            myReader.close();
            this.getTrips();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * Get trip list
     */
    private void getTrips() {
        String routeName;
        Integer distance;
        for (Route route : this.routeList) {
            routeName = route.getCityFrom() + route.getCityTo();
            distance = route.getDistance();
            getInternalTrips(route, routeName, distance);
        }
    }

    /**
     * Get internal trip list
     * @param route
     * @param routeName
     * @param distance
     */
    private void getInternalTrips(Route route, String routeName, Integer distance) {
        tripList.put(routeName,distance);
        for (Route searchRoute : this.routeList) {
            if(route.getCityTo().equals(searchRoute.getCityFrom()) && !routeName.contains(searchRoute.getCityFrom()+searchRoute.getCityTo())) {
                routeName = routeName + searchRoute.getCityTo();
                distance = distance + searchRoute.getDistance();
                getInternalTrips(searchRoute, routeName, distance);
            }
        }
    }

    /**
     * Get distance from a string trip
     * @param trip
     * @return
     */
    public String getDistance(String trip) {
        Integer distance = this.tripList.get(trip);
        if (distance == null) {
            return("NO SUCH ROUTE");
        } else {
            return(String.valueOf(distance));
        }

    }

    /**
     * Get trips counter
     * @param cityFrom
     * @param cityTo
     * @param limit
     * @return
     */
    public String getTripCounter(String cityFrom, String cityTo, Integer limit) {
        Integer trips = 0;
        for (String tripKey : this.tripList.keySet()) {
            if (tripKey.substring(0,1).equals(cityFrom) && tripKey.substring(tripKey.length() - 1).equals(cityTo) && tripKey.length()-1 <=limit) {
                trips++;
            }
        }
        return String.valueOf(trips);
    }

    /**
     * Get short distance between cities
     * @param cityFrom
     * @param cityTo
     * @return
     */
    public String getShortDistance(String cityFrom, String cityTo) {
        Integer distance = Integer.MAX_VALUE;
        for (String tripKey : this.tripList.keySet()) {
            if (tripKey.substring(0,1).equals(cityFrom) && tripKey.substring(tripKey.length() - 1).equals(cityTo)) {
                if(this.tripList.get(tripKey) < distance) {
                    distance = this.tripList.get(tripKey);
                }
            }
        }
        return String.valueOf(distance);
    }

    /**
     * Get trip list between cities
     * @param cityFrom
     * @param cityTo
     * @param limit
     * @return
     */
    public ArrayList<String> getTripList(String cityFrom, String cityTo, Integer limit) {
        ArrayList<String> tripList = new ArrayList<String>();
        for (String tripKey : this.tripList.keySet()) {
            if (tripKey.substring(0,1).equals(cityFrom) && tripKey.substring(tripKey.length() - 1).equals(cityTo) && tripKey.length()-1 < limit) {
                tripList.add(tripKey);
            }
        }
        return tripList;
    }

    /**
     * To print trips
     */
    public void printTripList() {
        for (String tripKey : this.tripList.keySet()) {
            System.out.print(tripKey + ":");
            System.out.println(this.tripList.get(tripKey));
        }
    }

}
