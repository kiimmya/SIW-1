public class RecipeBook {

    private String title;

    public RecipeBook() {
        title = "My Recipe Book";
    }

    public void showBookInfo() {
        System.out.println("Recipe book: " + title);
        System.out.println("The recipe book belongs to the collection.");
    }
}
