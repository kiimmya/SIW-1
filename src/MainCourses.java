class MainCourses extends Recipe {

    private String meatType;

    public MainCourses(int id, String name, int cookingTime, int portions, String mainIngredient, boolean vegetarian) {

        super(id, name, cookingTime,portions, mainIngredient, vegetarian);

        this.meatType = mainIngredient;
    }

    @Override
    public String getCategory() {
        return "Main Course";
    }

    @Override
    public void prepare() {
        System.out.println("Preparation: Cook the main course carefully.");
    }

    @Override
    public void serve() {
        System.out.println("Serving the main course.");
    }

    @Override
    public void adjustPortion() {
        System.out.println("Main course portion adjusted.");
    }


    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Meat type: " + meatType);
        prepare();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}