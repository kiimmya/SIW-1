public class RecipeRequest {

    public enum RequestType {
        ADD, UPDATE, DELETE
    }

    private RequestType type;
    private int recipeId;
    private Recipe recipe;
    private User requester;

    public RecipeRequest(RequestType type, int recipeId, Recipe recipe, User requester) {

        if (type == null) {
            throw new IllegalArgumentException("Request type cannot be null.");
        }

        if (requester == null) {
            throw new IllegalArgumentException("Requester cannot be null");
        }


        if (type == RequestType.ADD || type == RequestType.UPDATE) {
            if (recipe == null) {
                throw new IllegalArgumentException("Recipe is required.");
            }

            if (recipe.getId() != recipeId) {
                throw new IllegalArgumentException("Request ID must match recipe ID.");
            }
        }

        if (type == RequestType.DELETE && recipeId <= 0) {
            throw new IllegalArgumentException("Invalid recipe ID.");
        }


        this.type = type;
        this.recipeId = recipeId;
        this.recipe = recipe;
        this.requester = requester;
    }

    public RequestType getType() {
        return type;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public User getRequester() {
        return requester;
    }
}
