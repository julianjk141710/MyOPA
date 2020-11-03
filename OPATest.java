import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class OPATest {

    public String getSentence(String pathname) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathname))));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {
                sb.append(lineTxt);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("we get an error during reading the file");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        OPATest opaTest = new OPATest();
//        String pathName = "/Users/apple/Desktop/hello.txt";
        String pathName = args[0];

        String tmpSentence = opaTest.getSentence(pathName);
        //System.out.println(tmpSentence);
        System.out.println(tmpSentence);
        OPAHandler opahandler = new OPAHandler(tmpSentence);

        opahandler.opaHandler();
    }
}
