package practice2;

import java.util.Objects;

public class Place {
    int code1;
    int code2;
    int code3;
    int code4;
    String name;
    String status;

    public Place(int code1, int code2, int code3, int code4, String name, String status) {
        this.code1 = code1;
        this.code2 = code2;
        this.code3 = code3;
        this.code4 = code4;
        this.name = name;
        this.status = status;
    }

    public int getCode1() {
        return code1;
    }

    public int getCode2() {
        return code2;
    }

    public int getCode3() {
        return code3;
    }

    public int getCode4() {
        return code4;
    }

    public String getName() {
        return name;
    }

    public String getStatus() { return status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Place{" +
                "code1=" + code1 +
                ", code2=" + code2 +
                ", code3=" + code3 +
                ", code4=" + code4 +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                "}";
    }
}
