package practice2;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OktmoData {
    private List<Place> places = new ArrayList<>();

    public void readFile(String filename) {
        places.clear();
        Path p = Paths.get(filename);
        try {
            List<String> lines = Files.readAllLines(p, Charset.forName("cp1251"));
            for (String s : lines) {
                readLine(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //"01";"512";"000";"146";"9";"2";"п Калиновка";;;"493";"3";12.08.2021;01.01.2022
    // (\d+)";"(\d+)";"(\d+)";"(\d+)";"\d";"\d";"(.*)\s([A-Я]\w*)";
    /// \"(.*)\s([A-Я][а-я]+\w*)
    /// (\d+)";"(\d+)";"(\d+)";"(\d+)";"\d";"\d";"(.*?)\s([A-Я].*?)";
    private static final Pattern RE = Pattern.compile("(\\d+)\";\"(\\d+)\";\"(\\d+)\";\"(\\d+)\";\"\\d\";\"\\d\";\"(.*?)\\s([A-Я].*?)\";");

    private void readLine(String s) {
        Matcher m = RE.matcher(s);
        if (m.find()) {
            places.add(new Place(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)),
                    Integer.parseInt(m.group(4)),
                    m.group(6),
                    m.group(5)
            ));
        }
    }

    public Place findPlace(String name) {
        return getPlaces()
                .stream()
                .filter(place -> place.getName().contains(name))
                .findFirst()
                .orElse(null);
    }

    public List<Place> findPlaces(String end, String cond) {
        return getPlaces()
                .stream()
                .filter(place -> place.getName().endsWith(end) || place.getName().contains(cond))
                .collect(Collectors.toList());
    }

    public List<Place> listSortedDistinctPlaces() {
        return getPlaces()
                .stream()
                .sorted(Comparator.comparing(Place::getName))
                .distinct()
                .collect(Collectors.toList());
    }

    public Stream<Place> findAreaPlaces(int code1, int code2) {
        return getPlaces()
                .stream()
                .filter(place -> place.getCode1() == code1
                        && place.getCode2() == code2
                        && place.getCode4() != 0);
    }

    public Stream<Place> findRegionPlaces(int code1) {
        return getPlaces()
                .stream()
                .filter(place -> place.getCode1() == code1 && place.getCode4() != 0);
    }

    public Place findLargestRegionName(int code1) {
        List<Place> placesList = findRegionPlaces(code1)
                .sorted(Comparator.comparingInt(o -> o.getName().length()))
                .collect(Collectors.toList());
        return placesList.size() > 0 ? placesList.get(placesList.size() - 1) : null;
    }

    public Map<String, Long> findNamePopularity() {
        return getPlaces()
                .parallelStream()
                //.stream()
                .filter(place -> place.getCode4() != 0)
                .collect(Collectors.groupingBy(
                        Place::getName,
                        Collectors.counting()
                ))
                .entrySet()
                //.parallelStream()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(100)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public String toString() {
        return "OktmoData{" +
                "places=" + places +
                '}';
    }

    public List<Place> getPlaces() {
        return places;
    }
}
