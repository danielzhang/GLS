package cn.gbs.geocoding.engine.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cn.gls.data.Place;

import cn.gls.geocoding.context.GeoCodingContext;
import cn.gls.geocoding.context.GeoCodingContextLoader;
import cn.gls.geocoding.engine.dao.impl.GeoCodingDaoImpl;
import cn.gls.geocoding.engine.data.GeoCodingResult;

@SuppressWarnings("deprecation")
public class GeoCodingEngineTest extends
        AbstractDependencyInjectionSpringContextTests {

    private GeoCodingDaoImpl geoCodingDao;

    public void setGeoCodingDao(GeoCodingDaoImpl geoCodingDao) {
        this.geoCodingDao = geoCodingDao;
    }

    protected String[] getConfigLocations() {
        return new String[] { "spring-configuration/*" };
    }

    // @Override
    // protected void setUp() throws Exception {
    // super.setUp();
    // //loader = new GeoCodingContextLoader();
    //
    // }
    private GeoCodingContext context;
@Override
protected void onSetUp() throws Exception {
	
	super.onSetUp();
	GeoCodingContextLoader loader=new GeoCodingContextLoader();
	loader.create();
context=loader.getGeoCodingContext();
	
}
    public void testGeoCodingEngine() {

        List<Place> places = new ArrayList<Place>();
        Place place = new Place();
        place.setAllName("上海市");
        place.setPlaceLevel(3);
        places.add(place);
        Place place1 = new Place();
        place1.setAllName("青浦区");
        place1.setPlaceLevel(4);
        places.add(place1);
        Place place2 = new Place();
        place2.setAllName("诸光路");
        place2.setPlaceLevel(8);
        place2.setSuffix("1169号");
        places.add(place2);
        
        List<GeoCodingResult> results = geoCodingDao.getGeoCodingResultsToAddress(
                places,  context);
        System.out.println(results.size());
        // place.setCityCode(cityCode)
    }

}
