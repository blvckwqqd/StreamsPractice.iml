package practice2;

public class Main {

    public static void main(String[] args) {
        OktmoData d = new OktmoData();
        d.readFile("oktmo.csv");
        //System.out.println(d.getPlaces().get(50).getName());
        System.out.println(d.findPlace("”фа"));
        System.out.println(d.findAreaPlaces(52,629).count());
        System.out.println(d.findLargestRegionName(01));
        //System.out.println(d.listSortedDistinctPlaces());
        //System.out.println(d.findPlaces("ово","-"));
        System.out.println(d.findNamePopularity());

    }
}
