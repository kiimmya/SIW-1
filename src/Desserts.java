class Desserts extends Recipe {

    private String dessertType;

    public Desserts(int id, String name, int cookingTime, int portions, String mainIngredient, boolean vegetarian) {

        super(id, name, cookingTime, portions, mainIngredient, vegetarian);

        this.dessertType = mainIngredient;
    }

    @Override
    public String getCategory() {
        return "Dessert";
    }

    @Override
    public void prepare() {
        System.out.println("Preparation: Prepare the dessert carefully.");
    }

    @Override
    public void serve() {
        System.out.println("Serving the dessert.");
    }

    @Override
    public void adjustPortion() {
        System.out.println("Dessert portion adjusted.");
    }


    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Dessert type: " + dessertType);
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
