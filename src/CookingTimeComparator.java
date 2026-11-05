import java.util.Comparator;

public class CookingTimeComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe r1, Recipe r2) {
        return Integer.compare(r1.getCookingTime(), r2.getCookingTime());
    }
}
