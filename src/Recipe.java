import java.util.Objects;

abstract class Recipe implements Serveable, Adjustable, Comparable<Recipe> {

    private int id;
    private String name;
    private int cookingTime;
    private int portions;
    private String mainIngredient;
    private boolean vegetarian;

    public Recipe(int id, String name, int cookingTime, int portions, String mainIngredient, boolean vegetarian) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }

        setName(name);
        setCookingTime(cookingTime);
        setPortions(portions);
        setMainIngredient(mainIngredient);

        this.id = id;
        this.vegetarian = vegetarian;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getPortions() {
        return portions;
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }


    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }


    public void setCookingTime(int cookingTime) {
        if (cookingTime <= 0) {
            throw new IllegalArgumentException("Cooking time must be positive");
        }
        this.cookingTime = cookingTime;
    }


    public void setPortions(int portions) {
        if (portions <= 0) {
            throw new IllegalArgumentException("Portions must be positive");
        }
        this.portions = portions;
    }


    public void setMainIngredient(String mainIngredient) {
        if (mainIngredient == null || mainIngredient.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient cannot be empty");
        }
        this.mainIngredient = mainIngredient;
    }


    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public abstract String getCategory();

    public void category() {
        System.out.println("Category: " + getCategory());
    }

    public abstract void prepare();


    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Recipe: " + name);
        System.out.println("Cooking time: " + cookingTime + " minutes");
        System.out.println("Portions: " + portions);
        System.out.println("Main ingredient: " + mainIngredient);
        System.out.println("Vegetarian: " + vegetarian);
        System.out.println("Category: " + getCategory());
    }

    @Override
    public int compareTo(Recipe other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Recipe))
            return false;
        Recipe recipe = (Recipe) obj;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}