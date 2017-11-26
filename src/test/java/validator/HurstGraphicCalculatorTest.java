package validator;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.HurstGraphicCalculator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HurstGraphicCalculatorTest {

    private HurstGraphicCalculator hurstGraphicCalculator;

    private List<TrafficPoint> points;

    private int m = 0;

    private Logger logger = LoggerFactory.getLogger(HurstGraphicCalculatorTest.class);

    @Before
    public void init() {
        hurstGraphicCalculator = new HurstGraphicCalculator();
        m = 2;
        points = new ArrayList<>();
        points.add(new TrafficPoint(1,1));
        points.add(new TrafficPoint(2,2));
        points.add(new TrafficPoint(3,3));
        points.add(new TrafficPoint(1,4));
        points.add(new TrafficPoint(5,5));
        points.add(new TrafficPoint(1,6));
        points.add(new TrafficPoint(12,7));
        points.add(new TrafficPoint(5,8));
        points.add(new TrafficPoint(12,9));
    }

    @Test
    public void testXJM() {
        hurstGraphicCalculator.xjm(points, m).forEach(
                e -> logger.info(e.toString())
        );
    }

    @Test
    public void testVar() {
        hurstGraphicCalculator.varxjm(hurstGraphicCalculator.xjm(points, m), points, m);
    }

    @Test
    public void calc() {
        hurstGraphicCalculator.calc(points, 5);
    }

    @Test
    public void logs() {
        hurstGraphicCalculator.getLog(points, 5)
                .forEach(e -> {
                    logger.info(e.toString());
                });
    }

}
