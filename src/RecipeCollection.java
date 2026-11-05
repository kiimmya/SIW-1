import java.util.*;

public class RecipeCollection {

    private List<Recipe> recipes;
    private Map<Integer, Recipe> recipeIndex;
    private Map<String, List<Recipe>> categoryIndex;
    private Set<String> ingredients;
    private Queue<RecipeRequest> requestQueue;
    private RecipeBook recipeBook;
    private RecipeHelper helper;

    public RecipeCollection() {
        recipes = new ArrayList<>();
        recipeIndex = new HashMap<>();
        categoryIndex = new HashMap<>();
        ingredients = new HashSet<>();
        requestQueue = new ArrayDeque<>();
        recipeBook = new RecipeBook();
        helper = new RecipeHelper();
    }

// ADD RECIPE
    public synchronized boolean addRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }

        if (recipeIndex.containsKey(recipe.getId())) {
            throw new IllegalArgumentException("Recipe with this ID already exists.");
        }

        recipes.add(recipe);
        recipeIndex.put(recipe.getId(), recipe);
        categoryIndex.computeIfAbsent(recipe.getCategory().toLowerCase(), k -> new ArrayList<>()).add(recipe);
        ingredients.add(recipe.getMainIngredient());

        System.out.println(recipe.getName() + " added successfully.");
        return true;
    }

// FIND RECIPE BY ID
    public synchronized Recipe findRecipeById(int id) {
        Recipe recipe = recipeIndex.get(id);

        if (recipe == null) {
            throw new IllegalArgumentException("Recipe not found.");
        }
        return recipe;
    }

// SEARCH RECIPE BY NAME
    public synchronized List<Recipe> searchByName(String name) {
        List<Recipe> result = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return result;
        }

        String searchName = name.trim().toLowerCase();
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(searchName)) {
                result.add(recipe);
            }
        }
        return result;
    }


// FILTER BY CATEGORY
    public synchronized List<Recipe> filterByCategory(String category) {
        if (category == null || category.isBlank()) {
            return new ArrayList<>();
        }

        List<Recipe> result = categoryIndex.get(category.trim().toLowerCase());

        if (result == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(result);
    }

// UPDATE RECIPE
    public synchronized boolean updateRecipe(int id, Recipe updatedRecipe) {
        if (updatedRecipe == null) {
            throw new IllegalArgumentException("Updated recipe cannot be null.");
        }

        Recipe oldRecipe = recipeIndex.get(id);

        if (oldRecipe == null) {
            throw new IllegalArgumentException("Recipe not found.");
        }

        if (updatedRecipe.getId() != id) {
            throw new IllegalArgumentException("Recipe ID cannot be changed.");
        }

        int index = recipes.indexOf(oldRecipe);
        recipes.set(index, updatedRecipe);
        recipeIndex.put(id, updatedRecipe);
        rebuildIndexes();

        System.out.println("Recipe updated successfully.");
        return true;
    }

// DELETE RECIPE
    public synchronized boolean deleteRecipe(int id) {
        Recipe recipe = recipeIndex.get(id);

        if (recipe == null) {
            throw new IllegalArgumentException("Recipe not found.");
        }

        recipes.remove(recipe);
        recipeIndex.remove(id);
        rebuildIndexes();

        System.out.println(recipe.getName() + " deleted successfully.");
        return true;
    }

// REBUILD ALL INDEXES
    private synchronized void rebuildIndexes() {
        categoryIndex.clear();
        ingredients.clear();

        for (Recipe recipe : recipes) {
            String category = recipe.getCategory().toLowerCase();

            categoryIndex.computeIfAbsent(category, k -> new ArrayList<>()).add(recipe);

            ingredients.add(recipe.getMainIngredient());
        }
    }

// DISPLAY ALL RECIPES
    public synchronized void showRecipes() {
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }

        for (Recipe recipe : recipes) {
            recipe.printInfo();
            System.out.println("---------------------------------");
        }
    }

// DISPLAY UNIQUE INGREDIENTS
    public synchronized void showIngredients() {
        System.out.println("Unique ingredients:");

        if (ingredients.isEmpty()) {
            System.out.println("No ingredients available.");
            return;
        }

        for (String ingredient : ingredients) {
            System.out.println(ingredient);
        }
    }

// COMPARATOR
    public synchronized void sortRecipes(Comparator<Recipe> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }

        List<Recipe> sorted = new ArrayList<>(recipes);

        sorted.sort(comparator);

        if (sorted.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }

        for (Recipe recipe : sorted) {
            System.out.println(recipe.getId() + ". " + recipe.getName() + " - " + recipe.getCookingTime() + " minutes" + ", " + recipe.getPortions() + " portions");
        }
    }

// COMPARABLE
    public synchronized void sortById() {

        List<Recipe> sorted = new ArrayList<>(recipes);

        Collections.sort(sorted);

        if (sorted.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }

        for (Recipe recipe : sorted) {
            System.out.println(recipe.getId() + ". " + recipe.getName());
        }
    }

// ANALYTICS
    public synchronized void printRecipeCount() {
        helper.showCount(recipes.size());

        System.out.println("Unique ingredients: " + ingredients.size());

        System.out.println("Recipes by category:");

        for (Map.Entry<String, List<Recipe>> entry : categoryIndex.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
        }
        helper.showFastestRecipe(recipes);
    }

// USER SUBMITS REQUEST
    public synchronized void submitRequest(RecipeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        if (request.getRequester().getRole() != UserRole.USER) {
            throw new IllegalArgumentException("Only USER can submit requests.");
        }

        requestQueue.offer(request);

        System.out.println("Request submitted successfully.");
        System.out.println("Pending requests: " + requestQueue.size());
    }

// ADMIN PROCESSES NEXT REQUEST
    public synchronized void processNextRequest(User administrator) {
        if (administrator == null || administrator.getRole() != UserRole.ADMIN) {
            System.out.println("Only ADMIN can process requests.");
            return;
        }

// Extract the first request
        RecipeRequest request = requestQueue.poll();

        if (request == null) {
            System.out.println("Request queue is empty.");
            return;
        }

        System.out.println("\n--- PROCESSING REQUEST ---");
        System.out.println("Request type: " + request.getType());
        System.out.println("Requester: " + request.getRequester().getUsername());

        try {
            switch (request.getType()) {

                case ADD:
                    addRecipe(request.getRecipe());
                    break;

                case UPDATE:
                    updateRecipe(request.getRecipeId(), request.getRecipe());
                    break;

                case DELETE:
                    deleteRecipe(request.getRecipeId());
                    break;

                default:
                    throw new IllegalArgumentException("Unknown request type.");
            }

            System.out.println("Request processed successfully.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Request failed: " + e.getMessage());
        }

        System.out.println("Remaining requests: " + requestQueue.size());
    }

// GET NUMBER OF PENDING REQUESTS
    public synchronized int getPendingRequestsCount() {
        return requestQueue.size();
    }

// SHOW RECIPE BOOK
    public synchronized void showRecipeBook() {
        recipeBook.showBookInfo();
    }

// GETTERS
    public synchronized List<Recipe> getRecipes() {
        return Collections.unmodifiableList(new ArrayList<>(recipes));
    }

    public synchronized Map<Integer, Recipe> getRecipeIndex() {
        return Collections.unmodifiableMap(new HashMap<>(recipeIndex));
    }

    public synchronized Set<String> getIngredients() {
        return Collections.unmodifiableSet(new HashSet<>(ingredients));
    }
}
