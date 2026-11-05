import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String adminName = "Diana";

    public static void main(String[] args) {

        System.out.println("\n----- RECIPE ORGANIZER -----");

        MainCourses chickenCurry = new MainCourses(1, "Chicken Curry", 45, 4, "Chicken", false);
        MainCourses beefSteak = new MainCourses(2, "Beef Steak", 30, 2, "Beef", false);
        Desserts chocolateCake = new Desserts(3, "Chocolate Cake", 60, 8, "Chocolate", true);
        Desserts fruitSalad = new Desserts(4, "Fruit Salad", 15, 3, "Fruit", true);
        Pastry applePie = new Pastry(5, "Apple Pie", 50, 6, "Wheat", true);

        RecipeCollection collection = new RecipeCollection();

        collection.addRecipe(chickenCurry);
        collection.addRecipe(beefSteak);
        collection.addRecipe(chocolateCake);
        collection.addRecipe(fruitSalad);
        collection.addRecipe(applePie);

        System.out.print("\nEnter your username: ");

        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            scanner.close();
            return;
        }

        UserRole role;
        if (username.equalsIgnoreCase(adminName)) {
            role = UserRole.ADMIN;
            System.out.println("Welcome, Administrator, " + adminName + "!");
        } else {
            role = UserRole.USER;
            System.out.println("You don't have admin rights. You are logged in as User.");
        }

        User currentUser = new User(username, role);

        System.out.println("Logged in as: " + currentUser.getUsername());
        System.out.println("Your role: " + currentUser.getRole());


        boolean running = true;

        while (running) {
            if (currentUser.getRole() == UserRole.ADMIN) {
                showAdminMenu();
            } else {
                showUserMenu();
            }

            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1:
                        collection.showRecipes();
                        break;

                    case 2:
                        searchRecipe(collection);
                        break;

                    case 3:
                        collection.showRecipeBook();
                        break;

                    case 4:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            addRecipe(collection);
                        } else {
                            submitRecipeRequest(collection, currentUser);
                        }
                        break;

                    case 5:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            updateRecipe(collection);
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 6:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            deleteRecipe(collection);
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 7:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            collection.showIngredients();
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 8:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            collection.processNextRequest(currentUser);
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 9:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            System.out.println("Pending requests: " + collection.getPendingRequestsCount());
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 10:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            sortRecipes(collection);
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 11:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            collection.printRecipeCount();
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 12:
                        if (currentUser.getRole() == UserRole.ADMIN) {
                            demonstratePolymorphism(collection);
                        } else {
                            System.out.println("Access denied.");
                        }
                        break;

                    case 13: {
                        System.out.print("\nEnter your username: ");
                        String newUsername = scanner.nextLine().trim();

                        if (newUsername.isEmpty()) {
                            System.out.println("Username cannot be empty.");
                            break;
                        }

                        UserRole newRole;

                        if (newUsername.equalsIgnoreCase(adminName)) {
                            newRole = UserRole.ADMIN;
                            System.out.println("Welcome, Administrator, " + adminName + "!");
                        } else {
                            newRole = UserRole.USER;
                            System.out.println("You are logged in as User.");
                        }

                        currentUser = new User(newUsername, newRole);

                        System.out.println("Logged in as: " + currentUser.getUsername());
                        System.out.println("Your role: " + currentUser.getRole());
                        break;
                    }

                    case 0:
                        running = false;
                        System.out.println("Program finished.");
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

// ADMIN MENU
    private static void showAdminMenu() {

        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. Show all recipes");
        System.out.println("2. Search recipe");
        System.out.println("3. Show recipe book");
        System.out.println("4. Add recipe");
        System.out.println("5. Update recipe");
        System.out.println("6. Delete recipe");
        System.out.println("7. Show ingredients");
        System.out.println("8. Process next request");
        System.out.println("9. Show pending requests");
        System.out.println("10. Sort recipes");
        System.out.println("11. Show analytics");
        System.out.println("12. Demonstrate polymorphism");
        System.out.println("13. Switch user");
        System.out.println("0. Exit");
        System.out.println("----------------------------");
    }

// USER MENU
    private static void showUserMenu() {

        System.out.println("\n--- USER MENU ---");
        System.out.println("1. Show all recipes");
        System.out.println("2. Search recipe");
        System.out.println("3. Show recipe book");
        System.out.println("4. Submit recipe request");
        System.out.println("13. Switch user");
        System.out.println("0. Exit");
        System.out.println("----------------------------");
    }


    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


    private static boolean readBoolean(String message) {
        while (true) {
            System.out.print(message);
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("true") || answer.equalsIgnoreCase("yes")) {
                return true;
            }

            if (answer.equalsIgnoreCase("false") || answer.equalsIgnoreCase("no")) {
                return false;
            }

            System.out.println("Enter true/false or yes/no.");
        }
    }

// CREATE RECIPE
    private static Recipe readRecipe(int id) {

        System.out.println("\nChoose recipe category:");
        System.out.println("1. Main Course");
        System.out.println("2. Dessert");
        System.out.println("3. Pastry");

        int category = readInt("Enter category: ");

        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine().trim();

        int cookingTime = readInt("Enter cooking time: ");

        int portions = readInt("Enter number of portions: ");

        System.out.print("Enter main ingredient: ");
        String ingredient = scanner.nextLine().trim();

        boolean vegetarian = readBoolean("Is it vegetarian? (true/false): ");

        switch (category) {

            case 1:
                return new MainCourses(id, name, cookingTime, portions, ingredient, vegetarian);

            case 2:
                return new Desserts(id, name, cookingTime, portions, ingredient, vegetarian);

            case 3:
                return new Pastry(id, name, cookingTime, portions, ingredient, vegetarian);

            default:
                throw new IllegalArgumentException("Invalid recipe category.");
        }
    }

// SEARCH RECIPE
    private static void searchRecipe(RecipeCollection collection) {
        System.out.print("Enter recipe name to search: ");
        String name = scanner.nextLine();

        List<Recipe> results = collection.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }

        for (Recipe recipe : results) {
            recipe.printInfo();
            System.out.println("----------------------------");
        }
    }

// ADD RECIPE DIRECTLY
    private static void addRecipe(RecipeCollection collection) {
        int id = readInt("Enter new recipe ID: ");
        Recipe recipe = readRecipe(id);
        collection.addRecipe(recipe);
    }


// UPDATE RECIPE DIRECTLY
    private static void updateRecipe(RecipeCollection collection) {
        int id = readInt("Enter recipe ID to update: ");

        collection.findRecipeById(id);

        System.out.println("Enter new recipe information:");

        Recipe updatedRecipe = readRecipe(id);

        collection.updateRecipe(id, updatedRecipe);
    }

// DELETE RECIPE DIRECTLY
    private static void deleteRecipe(RecipeCollection collection) {
        int id = readInt("Enter recipe ID to delete: ");
        collection.deleteRecipe(id);
    }

// SUBMIT REQUEST TO ADMIN
    private static void submitRecipeRequest(RecipeCollection collection, User currentUser) {

        System.out.println("\n--- SUBMIT RECIPE REQUEST ---");
        System.out.println("1. Request to add a recipe");
        System.out.println("2. Request to update a recipe");
        System.out.println("3. Request to delete a recipe");

        int choice = readInt("Choose request type: ");

        switch (choice) {

            case 1: {
                int id = readInt("Enter new recipe ID: ");
                Recipe recipe = readRecipe(id);

                RecipeRequest request = new RecipeRequest(RecipeRequest.RequestType.ADD, id, recipe, currentUser);

                collection.submitRequest(request);
                break;
            }

            case 2: {
                int id = readInt("Enter recipe ID to update: ");
                collection.findRecipeById(id);

                System.out.println("Enter proposed new information:");
                Recipe updatedRecipe = readRecipe(id);

                RecipeRequest request = new RecipeRequest(RecipeRequest.RequestType.UPDATE, id, updatedRecipe, currentUser);

                collection.submitRequest(request);
                break;
            }

            case 3: {
                int id = readInt("Enter recipe ID to delete: ");
                collection.findRecipeById(id);

                RecipeRequest request = new RecipeRequest(RecipeRequest.RequestType.DELETE, id, null, currentUser);

                collection.submitRequest(request);
                break;
            }

            default:
                System.out.println("Invalid request type.");
        }
    }

// SORT RECIPES
    private static void sortRecipes(RecipeCollection collection) {

        System.out.println("\n--- SORT RECIPES ---");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by name");
        System.out.println("3. Sort by cooking time");
        System.out.println("4. Sort by portions");

        int choice = readInt("Choose sorting method: ");

        switch (choice) {

            case 1:
                collection.sortById();
                break;

            case 2:
                collection.sortRecipes(new RecipeNameComparator());
                break;

            case 3:
                collection.sortRecipes(new CookingTimeComparator());
                break;

            case 4:
                collection.sortRecipes(new PortionsComparator());
                break;

            default:
                System.out.println("Invalid sorting option.");
        }
    }

// POLYMORPHISM
    private static void demonstratePolymorphism(RecipeCollection collection) {

        System.out.println("\n--- POLYMORPHISM ---");

        for (Recipe recipe : collection.getRecipes()) {
            recipe.serve();
            recipe.adjustPortion();

            System.out.println("----------------------------");
        }
    }
}