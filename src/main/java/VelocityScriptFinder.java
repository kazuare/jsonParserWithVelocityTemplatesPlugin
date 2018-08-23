import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VelocityScriptFinder {
    public static List<CoordRange> findVelocityScripts(String beforeEvaluation) {
        List<CoordRange> coords = new ArrayList<>();

        Pattern pattern = Pattern.compile("(?<!#)#(?!#)[^\\s]+");
        Matcher m = pattern.matcher(beforeEvaluation);
        while(m.find()) {
            coords.add(new CoordRange(m.start(), m.end()-1));
        }

        return coords;
    }
}