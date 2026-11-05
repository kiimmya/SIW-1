public class RecipeHelper {

    public void showCount(int count) {
        System.out.println("Total recipes in collection: " + count);
    }

    public void showFastestRecipe(java.util.List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }

        Recipe fastest = recipes.get(0);

        for (Recipe recipe : recipes) {
            if (recipe.getCookingTime() < fastest.getCookingTime()) {
                fastest = recipe;
            }
        }

        System.out.println("Fastest recipe: " + fastest.getName());
        System.out.println("Cooking time: " + fastest.getCookingTime() + " minutes");
    }
}
