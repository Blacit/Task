import java.io.*;

public class task {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/VGilenko/Desktop/file.txt"),"CP1251"));
        String s;
        while((s = br.readLine()) != null) {
            System.out.println(s);
        }
        br.close();
        }
    }