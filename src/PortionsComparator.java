import java.util.Comparator;

public class PortionsComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe r1, Recipe r2) {
        return Integer.compare(r1.getPortions(), r2.getPortions());
    }
}
