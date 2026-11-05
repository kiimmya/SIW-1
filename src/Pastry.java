class Pastry extends Recipe {

    private String flourType;

    public Pastry(int id, String name, int cookingTime, int portions, String mainIngredient, boolean vegetarian) {

        super(id, name, cookingTime, portions, mainIngredient, vegetarian);

        this.flourType = mainIngredient;
    }

    @Override
    public String getCategory() {
        return "Pastry";
    }

    @Override
    public void prepare() {
        System.out.println("Preparation: Bake the pastry in the oven.");
    }

    @Override
    public void serve() {
        System.out.println("Serving the pastry.");
    }

    @Override
    public void adjustPortion() {
        System.out.println("Pastry portion adjusted.");
    }


    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Flour type: " + flourType);
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