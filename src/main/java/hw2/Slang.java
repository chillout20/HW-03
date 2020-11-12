import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Slang{
    static String fixAbbreviations(String sentence){
        sentence = sentence.replace("PLZ", "please");
        sentence = sentence.replace("FYI", "for your information");
        sentence = sentence.replace("GTFO", "please, leave me alone");
        sentence = sentence.replace("ASAP", "as soon as possible");
        return sentence;
    }
    static String fixSmiles(String sentence){
        sentence = sentence.replace(":)", "[smiling]");
        sentence = sentence.replace(":(", "[sad]");
        sentence = sentence.replace("¯\\_(ツ)_/¯", "[such is life]");
        System.out.println(sentence);
        return sentence;
    }

    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("Enter your sentence: ");
        String sentence = br.readLine();
        sentence = fixAbbreviations(sentence);
        sentence = fixSmiles(sentence);
        System.out.println("Output: " + sentence);
    }
}
