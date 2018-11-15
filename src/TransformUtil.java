import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TransformUtil {

    /**
     * Constant for file paths
     */
    private final static String CSV_PATH = "./src/file.csv";
    private final static String DATA_RESULTS_FILE_PATH = "./src/results.csv";
    private final static int MIN = 6;
    private final static int MAX = 10;

    /**
     * The separator of the CSV file
     */
    private static final String CSV_SEPARATOR = ",";
    private static StringBuilder resultText = new StringBuilder("question,1,2,3,4,5,6,7,didn't notice,total\n");
    private static int totalParticipants = -1;

    @SuppressWarnings("deprecation")
    public static String writeIt(){
        List<String> r = new ArrayList<>();
        List<String> result = new ArrayList<>();
        buildKeywordsOccurrencesNumberMap1Word(r,result);
        Map<String, Integer> marksMap;
        for(int i=0; i<r.size();i++){
            String line=r.get(i);
            marksMap=countByVal(line);
            marksMap.size();
            if(marksMap.get("\"1\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"1\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("\"2\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"2\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("\"3\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"3\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("\"4\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"4\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("\"5\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"5\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("\"6\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"6\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("\"7\"")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("\"7\""));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
            if(marksMap.get("didn't notice")!=null) {
                result.set(i,result.get(i)+CSV_SEPARATOR+marksMap.get("1"));
            }
            else{
                result.set(i,result.get(i)+CSV_SEPARATOR+"0");
            }
        }


        for(String line:result){
            resultText.append(line);
            resultText.append(CSV_SEPARATOR);
            resultText.append(totalParticipants);
            resultText.append("\n");
        }
        //The result is stored in a String static var
        BufferedWriter writer;
        try {
            writer = new BufferedWriter( new FileWriter(DATA_RESULTS_FILE_PATH));
            writer.write(resultText.toString());
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Logger.global.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
        return resultText.toString();
    }

    private static Map<String, Integer> countByVal(String line) {
        Map<String, Integer> marksMap = new ConcurrentHashMap<>();

            List<String> columns = Arrays.asList(line.trim().split(CSV_SEPARATOR));
            for (int i=1; i<columns.size(); i++){
                if(marksMap.get(columns.get(i))==null){
                    marksMap.put(columns.get(i),1);
                }
                else{
                    marksMap.put(columns.get(i),marksMap.get(columns.get(i))+1);
                }
            }

        return marksMap;
    }

    private static Consumer<String> getConsumer(List<String> r, List<String> result) {
        return (x) ->{
            totalParticipants++;
            //Second part of the line - the type
            List<String> columns = Arrays.asList(x.trim().split(CSV_SEPARATOR));
            for (int i=MIN; i<=MAX; i++){
                if(r.size()<MAX-MIN+1){
                    r.add(columns.get(i));
                    result.add(columns.get(i));
                }
                else{
                    r.set(i-MIN,r.get(i-MIN)+CSV_SEPARATOR+columns.get(i));
                }
            }
        };
    }


    @SuppressWarnings("deprecation")
    private static void buildKeywordsOccurrencesNumberMap1Word(List<String> r, List<String> result) {
        try (Stream<String> stream = Files.lines(Paths.get(CSV_PATH))) {
            stream.forEach(getConsumer(r,result));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.global.log(Level.SEVERE, e.getMessage(), e.getCause());
        }
    }

    public static void main(String[] args) {

        writeIt();
    }
}