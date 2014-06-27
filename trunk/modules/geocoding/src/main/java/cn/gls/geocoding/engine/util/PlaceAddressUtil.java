package cn.gls.geocoding.engine.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.postgresql.translation.messages_bg;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import cn.gls.data.Place;
import cn.gls.data.StandardAddress;
import cn.gls.geocoding.engine.dao.impl.GeoCodingAttributeDaoImpl;
import cn.gls.geocoding.engine.dao.impl.GeoCodingDaoImpl;
import cn.gls.geocoding.engine.data.GeoCodingResult;
import cn.gls.util.PlaceUtil;

/**
 * @ClassName PlaceAddressUtil.java
 * @Createdate 2012-6-5
 * @Description 与地名，地址及中文地理编码得分等相关的工具类
 * @Version 1.0
 * @Update 2012-7-10
 * @author "Daniel Zhang"
 * 
 */
public class PlaceAddressUtil {
	/***
	 * 由地名词来得到地址.....
	 * 
	 * @param places
	 * @return 返回地理编码地址
	 */
	public static StandardAddress getAddressByPlaces(List<Place> places) {
		StandardAddress address = new StandardAddress();
		int high_level = 0;
		String addr_type = null;
		for (Iterator<Place> it = places.iterator(); it.hasNext();) {
			final Place place = it.next();
			switch (place.getPlaceLevel()) {
			case 2:
				address.setProvince(place.getName());
				high_level = high_level > 2 ? high_level : 2;
				addr_type = high_level > 2 ? addr_type : "省行政区";
				break;
			case 3:
				address.setCity(place.getName());
				high_level = high_level > 3 ? high_level : 3;
				addr_type = high_level > 3 ? addr_type : "市行政区";
				break;
			case 4:
				address.setCounty(place.getName());
				high_level = high_level > 4 ? high_level : 4;
				addr_type = high_level > 4 ? addr_type : "区县行政区划";
				break;
			case 5:
				address.setTown(place.getName());
				high_level = high_level > 5 ? high_level : 5;
				addr_type = high_level > 5 ? addr_type : "镇行政区划";
				break;
			case 6:
				address.setVillage(place.getName());
				high_level = high_level > 6 ? high_level : 6;
				addr_type = high_level > 6 ? addr_type : "村行政区划";
				break;
			case 8:
				if (address.getStreet() != null)
					address.setStreet(address.getStreet() + "-"
							+ place.getName());
				else
					address.setStreet(place.getName());
				if(place.getSuffix()!=null)
					address.setStreet_suffix(place.getSuffix());
				// 判断是否含有门牌号
				if (place.getSuffix() != null
						|| !"".equalsIgnoreCase(place.getSuffix())) {
					address.setStreet_all(place.getName() + place.getSuffix());
				}
				high_level = high_level > 8 ? high_level : 8;
				addr_type = high_level > 8 ? addr_type : "路";
				break;
			case 9:

				address.setCommunitis(place.getName());
				if(place.getSuffix()!=null)
					address.setCommunitis_suffix(place.getSuffix());
				// 判断是否含有门牌号
				if (place.getSuffix() != null
						|| !"".equalsIgnoreCase(place.getSuffix())) {
					address.setCommunitis_all(place.getName()
							+ place.getSuffix());

				}
				high_level = high_level > 9 ? high_level : 9;
				addr_type = high_level > 9 ? addr_type : "区";
				break;
			case 10:
				address.setBuilding(place.getName());
				if(place.getSuffix()!=null)
					address.setBuilding_suffix(place.getSuffix());
				if (place.getSuffix() != null
						|| !"".equalsIgnoreCase(place.getSuffix())) {
					address.setBuilding_all(place.getName() + place.getSuffix());
				}
				high_level = high_level > 10 ? high_level : 10;
				addr_type = high_level > 10 ? addr_type : "大厦";
				break;

			}
		}
		address.setHigh_level(high_level);
		address.setAddr_type(addr_type);
		return address;

	}

	public static List<Place> getPlacesByStandardAddress(StandardAddress address) {
		List<Place> places = new ArrayList<Place>();
		// 标准化地址转化为地名词组。
		if (address.getCity() != null) {
			places.add(new Place(address.getCity(), 3, address.getAddr_type()));

		}
		if (address.getCounty() != null)
			places.add(new Place(address.getCounty(), 4, address.getAddr_type()));
		if (address.getTown() != null)
			places.add(new Place(address.getTown(), 5, address.getAddr_type()));
		if (address.getVillage() != null)
			places.add(new Place(address.getVillage(), 6, address
					.getAddr_type()));
		if (address.getStreet() != null) {
			Place place = new Place(address.getStreet(), 8,
					address.getAddr_type());
			if (address.getStreet_suffix() != null
					&& "".equalsIgnoreCase(address.getStreet_suffix()))

				place.setSuffix(address.getStreet_all().substring(
						address.getStreet().length(),
						address.getStreet_all().length()));

			places.add(place);
		}
		if (address.getCommunitis() != null) {
			Place place = new Place(address.getCommunitis(), 9,
					address.getAddr_type());
			if (address.getCommunitis_suffix() != null
					&& "".equalsIgnoreCase(address.getCommunitis_suffix()))

				place.setSuffix(address.getCommunitis_all().substring(
						address.getCommunitis().length(),
						address.getCommunitis_all().length()));
			places.add(place);
		}
		if (address.getBuilding() != null) {
			if (address.getBuilding_all() != null)
				places.add(new Place(address.getBuilding_all(), 10, address
						.getAddr_type()));
			else
				places.add(new Place(address.getBuilding(), 10, address
						.getAddr_type()));
		}
		return places;

	}

	public static float getScore(float orlg,Map<Integer, Integer> map, StandardAddress address) {
                 return orlg/getLg(map,address)*100;
	}
 

	public static List<Place> getPlaces(StandardAddress address,int cityCode){
	  List<Place> places=new ArrayList<Place>();
//	  
//	  if(address.getProvince()!=null)
//		  places.add(new Place(address.getProvince(),2,cityCode));
//	  if(address.getCity()!=null)
//		  places.add(new Place(address.getCity(),3,cityCode));
//	  if(address.getCounty()!=null)
//          places.add(new Place(address.getCounty(),4,cityCode));
//	  if(address.getTown()!=null)
//		  places.add(new Place)
	  return places;
  }
	
	public static float getLg(Map<Integer, Integer> map,
			StandardAddress address) {
		float lg = 0;
     if(address.getCounty()!=null)
    	 lg+=Math.log10(map.get(4));
     if(address.getTown()!=null)
    	 lg+=Math.log10(map.get(5));
     if(address.getVillage()!=null)
    	 lg+=Math.log10(map.get(6));
     if(address.getVillage_suffix()!=null)
    	 lg+=Math.log10(map.get(16));
     if(address.getStreet()!=null)
    	 lg+=Math.log10(map.get(8));
     if(address.getStreet_suffix()!=null)
    	 lg+=Math.log10(map.get(18));
     if(address.getCommunitis()!=null)
    	 lg+=Math.log10(map.get(9));
     if(address.getCommunitis_suffix()!=null)
    	 lg+=Math.log10(map.get(19));
     if(address.getBuilding()!=null)
    	 lg+=Math.log10(map.get(10));
     if(address.getBuilding_suffix()!=null)
    	 lg+=Math.log10(map.get(20));
     return lg;
		
	}

	/**
	 * 通过最小差值和目前差值数来获得分数。
	 * @param maxDif
	 * @param dif
	 * @param 当前值与最小值对应的距离
	 * @return
	 */
//	public static float getScore(int mindif,int dif,double dis){
//		
//	}
	/**
	 * 求的结果的门牌号平均距离。
	 * @param difsTree
	 * @return
	 */
	public static double aveDistance(Map<Object, GeoCodingResult> difsTree){
		
		double dis=0;
		
		Iterator<Map.Entry<Object, GeoCodingResult>> dIterator=difsTree.entrySet().iterator();
		Geometry geom=null;
		int sum=0;
		int preNumber=0;
		while(dIterator.hasNext()){
			Map.Entry<Object, GeoCodingResult> mentry=dIterator.next();
			GeoCodingResult result=mentry.getValue();
			int number=Integer.valueOf(mentry.getKey().toString());
			if(geom==null)
			{
				preNumber=number;
				geom=GeometryUtil.convertGeometry(result.getAddress().getGeoText());
				continue;
			}
			else{
				sum+=number-preNumber;
				preNumber=number;
				Geometry gtemp=GeometryUtil.convertGeometry(result.getAddress().getGeoText());
				dis+=((Point)geom).distance(gtemp)*110*1000;
				geom=gtemp;
			}
		}
		
		if(sum!=0)
		return dis/sum;
		else
			return 0;
	}
	/**
	 * 获得最后分数结果
	 * @param difs
	 * @param aveDis
	 * @param requestNumber
	 * @descriptor:理论上100分之米90分，500米之内80分，1500米之内60分,3000米为40分。5000米为20分，10000米为0分
	 * @return
	 */
	public static List<GeoCodingResult> getScore(Map<Object, GeoCodingResult> difs,double aveDis,int requestNumber){
		Iterator<Map.Entry<Object, GeoCodingResult>> mIterator=difs.entrySet().iterator();
		List<GeoCodingResult> results=new ArrayList<GeoCodingResult>();
		while(mIterator.hasNext()){
			Map.Entry<Object, GeoCodingResult> mEntry=mIterator.next();
			int d=Integer.valueOf(mEntry.getKey().toString());
			GeoCodingResult result=mEntry.getValue();
			double dis=aveDis*(Math.abs(d-requestNumber));
			//获得geoCodingResult的其他属性
			com.vividsolutions.jts.geom.Geometry geometry = GeometryUtil
					.convertGeometry(result.getAddress().getGeoText());
			cn.gls.geometry.data.Geometry geom = GeometryUtil
					.convertGeometry(geometry);
			result.setGeometry(new cn.gls.geocoding.engine.data.Geometry(geom));
			float s=0;
			if(dis<=100){
				 s=(float) (100-(dis/100)*10);
			}
			if(100<dis&&dis<=500){
				s=(float)(90-(dis/500)*10);
			}
			if(500<dis&&dis<=1500){
				s=(float)(80-(dis/1500)*20);
			}
			 if(dis>1500&&dis<=3000){
				s=(float)(60-(dis/3000)*20);
			}
			 if(dis>3000&&dis<=5000){
				 s=(float)(40-(dis/5000)*20);
			 }
			 if(dis>=5000&&dis<=10000){
				 s=(float)(20-(dis/10000)*20);
			 }
			 if(dis>10000)
				 s=0.0f;
			result.setScore(s);
			results.add(result);
		}
		return results;
	}
	
}
