import org.apache.velocity.runtime.parser.*;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class VelocityScriptFinder {
    public static List<CoordRange> findVelocityScripts(String beforeEvaluation, String afterEvaluation) throws ParseException {
        List<CoordRange> coords = new ArrayList<>();
        CharStream charStream = new VelocityCharStream(new StringReader(beforeEvaluation), 0, 0);
        Parser parser = new Parser(charStream);

        Token nextToken = parser.getNextToken();
        while(nextToken.kind != ParserConstants.EOF) {
            System.out.println(nextToken.toString());
            System.out.println("==>" + nextToken.next);
            System.out.println("==>" + nextToken.kind);
            System.out.println("==>" + nextToken.specialToken);
            System.out.println("==>" + nextToken.beginColumn + " " + nextToken.endColumn);
            if (nextToken.specialToken != null) {
                System.out.println(nextToken.specialToken.kind);
                System.out.println(nextToken.specialToken.next);
                System.out.println(nextToken.specialToken.specialToken);
            }
            System.out.println("--------");
            nextToken = parser.getNextToken();
        }
        return coords;
    }
}