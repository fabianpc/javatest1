/**
 * Main application
 */
public class Trains {

    /**
     * Init method
     * @param args
     */
    public static void main(String[] args) {
        RouteManager routeManager = new RouteManager();
        System.out.println(routeManager.getDistance("ABC"));
        System.out.println(routeManager.getDistance("AD"));
        System.out.println(routeManager.getDistance("ADC"));
        System.out.println(routeManager.getDistance("AEBCD"));
        System.out.println(routeManager.getDistance("AED"));
        System.out.println(routeManager.getTripCounter("C","C",3));
        System.out.println(routeManager.getTripCounter("A","C", 4));
        System.out.println(routeManager.getShortDistance("A", "C"));
        System.out.println(routeManager.getShortDistance("B", "B"));
        System.out.println(routeManager.getTripList("C", "C", 30).stream().count());
    }
}
