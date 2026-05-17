package menu;

import enums.Language;
import model.communication.Comment;
import model.communication.News;
import model.users.User;
import patterns.DataStorage;
import util.LanguageManager;

import java.util.List;
import java.util.Scanner;

public class NewsMenu {

    private static Language lang(User user) {
        return user.getLanguage();
    }

    public static void open(User user, Scanner sc) {
        while (true) {
            LanguageManager.print(lang(user), "menu.news.title");
            System.out.println("1. " + LanguageManager.get(lang(user), "menu.news.1"));
            System.out.println("2. " + LanguageManager.get(lang(user), "menu.news.2"));
            System.out.println("3. " + LanguageManager.get(lang(user), "menu.news.3"));
            LanguageManager.prompt(lang(user), "general.choose");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllNews(user);
                    break;
                case "2":
                    addComment(user, sc);
                    break;
                case "3":
                    return;
                default:
                    LanguageManager.print(lang(user), "general.invalid_choice");
            }
        }
    }

    private static void viewAllNews(User user) {
        List<News> newsList = DataStorage.getInstance().getSortedNews();
        if (newsList.isEmpty()) {
            LanguageManager.print(lang(user), "general.no_data");
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
            LanguageManager.print(lang(user), "general.no_data");
            return;
        }

        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }

        LanguageManager.prompt(lang(user), "general.choose");
        try {
            int index = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (index < 0 || index >= newsList.size()) {
                LanguageManager.print(lang(user), "general.invalid_choice");
                return;
            }

            System.out.print("Comment text: ");
            String text = sc.nextLine();

            newsList.get(index).addComment(new Comment(user, text));
            System.out.println("Comment added.");
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(user), "general.invalid_input");
        }
    }
}