package util;

import enums.Language;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


public class LanguageManager {

    private static final Map<String, Map<Language, String>> STRINGS = new HashMap<>();

    static {
        //  General
        add("general.invalid_choice", "Қате таңдау.", "Invalid choice.", "Неверный выбор.");
        add("general.invalid_input", "Жарамсыз енгізу.", "Invalid input.", "Неверный ввод.");
        add("general.goodbye", "Сау болыңыз!", "Goodbye!", "До свидания!");
        add("general.welcome", "Қош келдіңіз, ", "Welcome, ", "Добро пожаловать, ");
        add("general.choose", "Таңдаңыз: ", "Choose: ", "Выберите: ");
        add("general.back", "Артқа", "Back", "Назад");
        add("general.logout", "Шығу", "Logout", "Выйти");
        add("general.no_data", "Деректер жоқ.", "No data available.", "Данных нет.");
        add("general.saved", "Деректер сақталды.", "Data saved.", "Данные сохранены.");

        //  Login
        add("login.prompt_login", "Логин: ", "Login: ", "Логин: ");
        add("login.prompt_password", "Құпия сөз: ", "Password: ", "Пароль: ");
        add("login.failed", "Логин немесе құпия сөз қате. Қайталаңыз.",
                "Invalid login or password. Try again.",
                "Неверный логин или пароль. Попробуйте снова.");
        add("login.another", "Басқа пайдаланушы ретінде кіру? (y/n): ",
                "Log in as another user? (y/n): ",
                "Войти как другой пользователь? (y/n): ");
        add("login.title",
                "╔══════════════════════════════╗\n║  УНИВЕРСИТЕТ ЖҮЙЕСІНЕ КІР   ║\n╚══════════════════════════════╝",
                "╔══════════════════════════════╗\n║   UNIVERSITY SYSTEM LOGIN   ║\n╚══════════════════════════════╝",
                "╔══════════════════════════════╗\n║    ВХОД В СИСТЕМУ УНИВЕРА   ║\n╚══════════════════════════════╝");

        // Language switch
        add("lang.switch_prompt", "Тілді таңдаңыз (KZ / EN / RU): ", "Choose language (KZ / EN / RU): ", "Выберите язык (KZ / EN / RU): ");
        add("lang.switched", "Тіл өзгертілді: ", "Language switched to: ", "Язык изменён на: ");

        //  Student menu
        add("menu.student.title", "\n=== СТУДЕНТ МӘЗІРІ ===", "\n=== STUDENT MENU ===", "\n=== МЕНЮ СТУДЕНТА ===");
        add("menu.student.1", "Қол жетімді курстарды көру", "View available courses", "Просмотр доступных курсов");
        add("menu.student.2", "Курсқа тіркелу", "Register for course", "Запись на курс");
        add("menu.student.3", "Курстан шығу", "Drop course", "Отказаться от курса");
        add("menu.student.4", "Менің курстарым", "View my courses", "Мои курсы");
        add("menu.student.5", "Бағаларды көру", "View marks", "Просмотр оценок");
        add("menu.student.6", "Транскрипт алу", "Get transcript", "Получить транскрипт");
        add("menu.student.7", "Оқытушыны бағалау", "Rate teacher", "Оценить преподавателя");
        add("menu.student.8", "Студенттік ұйымдар", "View/join student organizations", "Студенческие организации");
        add("menu.student.9", "Техникалық қолдауға сұраныс", "Send request to tech support", "Отправить запрос в тех. поддержку");
        add("menu.student.10", "Тілді ауыстыру", "Switch language", "Сменить язык");  // ✅ FIX: was Logout
        add("menu.student.11", "Шығу", "Logout", "Выйти");          // ✅ FIX: new item

        //  Teacher menu
        add("menu.teacher.title", "\n=== ОҚЫТУШЫ МӘЗІРІ ===", "\n=== TEACHER MENU ===", "\n=== МЕНЮ ПРЕПОДАВАТЕЛЯ ===");
        add("menu.teacher.1", "Менің курстарым", "View my courses", "Мои курсы");
        add("menu.teacher.2", "Курстағы студенттер", "View students in course", "Студенты на курсе");
        add("menu.teacher.3", "Баға қою", "Put mark", "Выставить оценку");
        add("menu.teacher.4", "Деканға шағым жіберу", "Send complaint to dean", "Отправить жалобу декану");
        add("menu.teacher.5", "Қызметкерге хабарлама жіберу", "Send message to employee", "Отправить сообщение сотруднику");
        add("menu.teacher.6", "Тілді ауыстыру", "Switch language", "Сменить язык");
        add("menu.teacher.7", "Шығу", "Logout", "Выйти");

        //  Admin menu
        add("menu.admin.title", "\n=== ӘКІМШІ МӘЗІРІ ===", "\n=== ADMIN MENU ===", "\n=== МЕНЮ АДМИНИСТРАТОРА ===");
        add("menu.admin.1", "Пайдаланушы қосу", "Add user", "Добавить пользователя");
        add("menu.admin.2", "Пайдаланушыны жою", "Remove user", "Удалить пользователя");
        add("menu.admin.3", "Барлық пайдаланушылар", "View all users", "Все пользователи");
        add("menu.admin.4", "Журналдарды көру", "View logs", "Просмотр журналов");
        add("menu.admin.5", "Тілді ауыстыру", "Switch language", "Сменить язык");
        add("menu.admin.6", "Шығу", "Logout", "Выйти");

        //  Manager menu
        add("menu.manager.title", "\n=== МЕНЕДЖЕР МӘЗІРІ ===", "\n=== MANAGER MENU ===", "\n=== МЕНЮ МЕНЕДЖЕРА ===");
        add("menu.manager.1", "Тіркеуге курс қосу", "Add course for registration", "Добавить курс для записи");
        add("menu.manager.2", "Курсқа оқытушы тағайындау", "Assign teacher to course", "Назначить преподавателя на курс");
        add("menu.manager.3", "Студент тіркеуін бекіту", "Approve student registration", "Подтвердить запись студента");
        add("menu.manager.4", "Студенттерді көру", "View students", "Просмотр студентов");
        add("menu.manager.5", "Оқытушыларды көру", "View teachers", "Просмотр преподавателей");
        add("menu.manager.6", "Есеп жасау", "Create report", "Создать отчёт");
        add("menu.manager.7", "Жаңалықтарды басқару", "Manage news", "Управление новостями");
        add("menu.manager.8", "Сұраныстарды көру", "View requests", "Просмотр запросов");
        add("menu.manager.9", "Тілді ауыстыру", "Switch language", "Сменить язык");
        add("menu.manager.10", "Шығу", "Logout", "Выйти");

        // TechSupport menu
        add("menu.tech.title", "\n=== ТЕХНИКАЛЫҚ ҚОЛДАУ МӘЗІРІ ===", "\n=== TECH SUPPORT MENU ===", "\n=== МЕНЮ ТЕХ. ПОДДЕРЖКИ ===");
        add("menu.tech.1", "Жаңа сұраныстар", "View new requests", "Новые запросы");
        add("menu.tech.2", "Сұранысты қабылдау", "Accept request", "Принять запрос");
        add("menu.tech.3", "Сұранысты қабылдамау", "Reject request", "Отклонить запрос");
        add("menu.tech.4", "Орындалды деп белгілеу", "Mark as done", "Отметить как выполненное");
        add("menu.tech.5", "Менің сұраныстарым", "View my requests", "Мои запросы");
        add("menu.tech.6", "Тілді ауыстыру", "Switch language", "Сменить язык");
        add("menu.tech.7", "Шығу", "Logout", "Выйти");

        //  Researcher menu
        add("menu.researcher.title", "\n=== ЗЕРТТЕУШІ МӘЗІРІ ===", "\n=== RESEARCHER MENU ===", "\n=== МЕНЮ ИССЛЕДОВАТЕЛЯ ===");
        add("menu.researcher.1", "Ғылыми мақала қосу", "Add research paper", "Добавить статью");
        add("menu.researcher.2", "Менің мақалаларым", "View my papers", "Мои статьи");
        add("menu.researcher.3", "Жоба жасау", "Create research project", "Создать проект");
        add("menu.researcher.4", "Жобаға қатысушы қосу", "Add participant to project", "Добавить участника в проект");
        add("menu.researcher.5", "Менің жобаларым", "View my projects", "Мои проекты");
        add("menu.researcher.6", "Журналға жазылу", "Subscribe to journal", "Подписаться на журнал");
        add("menu.researcher.7", "Журналға мақала жариялау", "Publish paper to journal", "Опубликовать статью в журнале");
        add("menu.researcher.8", "Ең көп цитатталған зерттеуші", "View top cited researcher", "Топ-цитируемый исследователь");
        add("menu.researcher.9", "Шығу", "Logout", "Выйти");

        // News menu
        add("menu.news.title", "\n=== ЖАҢАЛЫҚТАР ===", "\n=== NEWS ===", "\n=== НОВОСТИ ===");
        add("menu.news.1", "Барлық жаңалықтарды көру", "View all news (pinned first)", "Все новости (закреплённые вверху)");
        add("menu.news.2", "Пікір қалдыру", "Add comment", "Добавить комментарий");
        add("menu.news.3", "Артқа", "Back", "Назад");

        // Course info
        add("course.no_available", "Қол жетімді курстар жоқ.", "No available courses.", "Нет доступных курсов.");
        add("course.registered", "Сәтті тіркелдіңіз.", "Registered successfully.", "Успешно зарегистрированы.");
        add("course.dropped", "Курстан шықтыңыз.", "Course dropped.", "Вы отказались от курса.");
        add("course.no_registered", "Тіркелген курстар жоқ.", "No registered courses.", "Нет зарегистрированных курсов.");

        //  Marks / Transcript─
        add("marks.no_marks", "Бағалар жоқ.", "No marks yet.", "Оценок пока нет.");
        add("transcript.header", "========== ТРАНСКРИПТ ==========", "========== TRANSCRIPT ==========", "========== ТРАНСКРИПТ ==========");
        add("transcript.student", "Студент  : ", "Student  : ", "Студент  : ");
        add("transcript.generated", "Жасалды  : ", "Generated: ", "Сформирован: ");
        add("transcript.gpa", "ОБК      : ", "GPA      : ", "ГПА      : ");

        // Requests
        add("request.description_prompt", "Сұраныс мазмұны: ", "Enter request description: ", "Описание запроса: ");
        add("request.urgency_prompt", "Шұғылдылық деңгейі (LOW / MEDIUM / HIGH): ", "Urgency level (LOW / MEDIUM / HIGH): ", "Уровень срочности (LOW / MEDIUM / HIGH): ");
        add("request.sent", "Сұраныс жіберілді. ID: ", "Request sent. ID: ", "Запрос отправлен. ID: ");
        add("request.no_requests", "Сұраныстар жоқ.", "No requests.", "Запросов нет.");
    }

    public static String get(Language lang, String key) {
        Map<Language, String> translations = STRINGS.get(key);
        if (translations == null) return "[?" + key + "?]";
        String result = translations.get(lang);
        return result != null ? result : translations.getOrDefault(Language.EN, "[?" + key + "?]");
    }

    public static void print(Language lang, String key) {
        System.out.println(get(lang, key));
    }

    public static void prompt(Language lang, String key) {
        System.out.print(get(lang, key));
    }

    private static void add(String key, String kz, String en, String ru) {
        Map<Language, String> map = new EnumMap<>(Language.class);
        map.put(Language.KZ, kz);
        map.put(Language.EN, en);
        map.put(Language.RU, ru);
        STRINGS.put(key, map);
    }
}