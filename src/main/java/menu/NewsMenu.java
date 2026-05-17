package menu;

import model.communication.Comment;
import model.communication.News;
import model.users.User;
import patterns.DataStorage;

import java.util.List;
import java.util.Scanner;

public class NewsMenu {

    public static void open(User user, Scanner sc) {
        while (true) {
            System.out.println("\n=== NEWS ===");
            System.out.println("1. View all news");
            System.out.println("2. Add comment");
            System.out.println("3. Back");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewAllNews();
                    break;
                case "2":
                    addComment(user, sc);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void viewAllNews() {
        List<News> newsList = DataStorage.getInstance().getSortedNews();
        if (newsList.isEmpty()) {
            System.out.println("No news.");
            return;
        }

        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i));
            for (Comment c : newsList.get(i).getComments()) {
                System.out.println("   - " + c);
            }
        }
    }

    private static void addComment(User user, Scanner sc) {
        List<News> newsList = DataStorage.getInstance().getSortedNews();
        if (newsList.isEmpty()) {
            System.out.println("No news.");
            return;
        }

        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }

        System.out.print("Choose news number: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        System.out.print("Comment text: ");
        String text = sc.nextLine();

        newsList.get(index).addComment(new Comment(user, text));
        System.out.println("Comment added.");
    }
}