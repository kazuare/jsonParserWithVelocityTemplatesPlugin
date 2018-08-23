import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VelocityScriptFinderTest {
    @Test
    public void searchTest(){
        String before =
                "#parse('aaa')\n" +
                "#parse('bbb')\n" +
                "ab cd ef \n" +
                "#aaa('a')#bbb('b')\n" +
                "abcd #bbb('b')\n";

        List<CoordRange> expected = new ArrayList<CoordRange>(){{
            add(new CoordRange(0,12));
            add(new CoordRange(14,26));
            add(new CoordRange(38,55));
            add(new CoordRange(62,70));
        }};

        for (CoordRange item: expected) {
            System.out.println(before.substring(item.a(), item.b() + 1));
        }
        assertEquals(expected, VelocityScriptFinder.findVelocityScripts(before));
    }

}