import org.apache.velocity.runtime.parser.ParseException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VelocityScriptFinderTest {
    @Test
    public void searchTest() throws ParseException {
        String before =
                "#parse(\"aaa\")\n" +
                "#parse(\"bbb\")\n" +
                "ab cd ef \n" +
                "#aaa(\"a\")#bbb(\"b ) fs\"))\n" +
                "abcd #bbb(\"b\")\n";

        String after = "\n\n" +
                "ab cd ef \n" +
                "aresultbresult)\n" +
                "abcd bresult\n";

        List<CoordRange> expected = new ArrayList<CoordRange>(){{
            add(new CoordRange(0,12));
            add(new CoordRange(14,26));
            add(new CoordRange(38,60));
            add(new CoordRange(68,76));
        }};

        for (CoordRange item: expected) {
            System.out.println(before.substring(item.a(), item.b() + 1));
        }
        assertEquals(expected, VelocityScriptFinder.findVelocityScripts(before, after));
    }

}