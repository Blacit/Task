import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class task {
    public static void main(String[] args) throws IOException {
        String s;
        ArrayList<String> inputs = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\VGilenko\\IdeaProjects\\Task1\\src\\main\\resources\\File.txt"), "CP1251"));
        } catch (FileNotFoundException e) {
            System.out.println("Файл не был найден, нужно скорректировать путь");
            System.exit(0);
        }
        for (int i = 0; (s = br.readLine()) != null; i++) {
            inputs.add(s);
        }
        br.close();

        for (String input : inputs) {
            String parts[] = isLineOkay(input);
            if (parts != null) {
                System.out.println(Arrays.asList(parts));
            }
        }
        System.out.println("---------------------------");
        System.out.println("Выбрали корректные варианты");
        System.out.println("---------------------------");
    }

    public static String[] isLineOkay(String line) {
        final String PATTERN = "^\\s*?([А-Яа-яЁё]+)\\s*?(?:\\/\\s*?)?(\\d+\\.?\\d{0,3})\\s*?(?:\\/\\s*?)?([А-Яа-яЁё]+)$";
        Pattern pattern = Pattern.compile(PATTERN);
        String[] parts = new String[3];
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            parts[0] = matcher.group(1);
            parts[1] = matcher.group(2);
            parts[2] = matcher.group(3);
            return parts;
        } else {
            return null;
        }
    }
}
// средняя заработная плата = зп1 сотрудника + зп2 сотрудника / количество сотрудников
// Вместо String args принимаем файл