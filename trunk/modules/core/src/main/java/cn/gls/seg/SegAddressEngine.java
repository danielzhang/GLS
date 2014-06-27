package cn.gls.seg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.gls.assist.ECEngine;
import cn.gls.assist.PinYinEngine;
import cn.gls.assist.ThesaurusEngine;
import cn.gls.context.GLSConfigContext;
import cn.gls.context.GLSContext;
import cn.gls.data.GLSRequestParameter;
import cn.gls.data.PinyinPlace;
import cn.gls.data.Place;
import cn.gls.data.SegAddress;
import cn.gls.place.dao.IPlaceDao;
import cn.gls.util.PlaceUtil;

/**
 * @ClassName: SegAddressEngine.java
 * @Description 
 * @Date 2012-7-4
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-7-4
 */
public class SegAddressEngine implements ISegAddressEngine {

    private ISegWord segWord;

    private GLSContext context=GLSConfigContext.getInstance();

    private List<SegAddress> addresses;


	private IPlaceDao placeDao;

    public void setPlaceDao(IPlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    public void setSegWord(ISegWord segWord) {
        this.segWord = segWord;
    }


    public List<SegAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<SegAddress> addresses) {
		this.addresses = addresses;
	}

	public SegAddressEngine() {
		super();
	}

	public SegAddressEngine(ISegWord segWord, GLSContext context,
            IPlaceDao placeDao) {
        super();
        this.segWord = segWord;
        this.context = context;
        this.placeDao = placeDao;
    }



    /**
     * (non-Javadoc)
     * 
     * @see cn.gls.seg.ISegAddressEngine#standardPlace(java.lang.String, int)
     */
    public List<Place> standardPlace(String address, int cityCode) {

        // if("江川路248弄114".equalsIgnoreCase(address))
        SegAddress segAddress = segAddressByDictionary(address);
        if (segAddress.isMisprint() || segAddress.isPinyin())
            segAddress = segAddressByStyle(address);

        return standardPlace(segAddress.getPlacesList(), cityCode);
    }

    // 分词引擎
    private SegAddress segAddressByDictionary(String address) {
        GLSRequestParameter parameter = new GLSRequestParameter(address);
        if (!(segWord instanceof AutoSegWord))
            segWord = new AutoSegWord();
        return segWord.seg4Address(parameter);
    }

    private SegAddress segAddressByStyle(String address) {
        segWord = new StyleSegWord();
        GLSRequestParameter parameter = new GLSRequestParameter(address);
        SegAddress segAddress = segWord.seg4Address(parameter);
        return segAddress;
    }
//暂时不处理又含有拼音又含有错字的情况
    public List<Place> standardPlace(List<Place> wds, int cityCode) {
    	
        List<Place> places = new ArrayList<Place>();
        Iterator<Place> iterator = wds.iterator();
        SegAddress address=new SegAddress();
        while (iterator.hasNext()) {
            Place place = iterator.next();
            // Map.Entry<String, Integer> iEntry =
            // map.entrySet().iterator().next();
            try {
                switch (place.getPlaceLevel()) {
                    case 12:
                        // 首先判断不是pinyin，如果是pinyin的话转化一下,我们这里只处理一个拼音词的情况，多个暂不处理
                        if (PlaceUtil.ispinyin(place.getName())) {
                            PinYinEngine pinyinEngine = new PinYinEngine(
                                    context,
                                    placeDao);
                            List<Place> pinyinPlaces = pinyinEngine
                                    .getPlacesByPinyin(place, cityCode);
                            //2012-11-29 更改拼音 得到拼音地名词，很多个地名词，但是拼音相同。
                              addresses=new ArrayList<SegAddress>();
                              
                              for(int i=0;i<pinyinPlaces.size();i++){
                            	 SegAddress tempAddress=new SegAddress();
                            	 List<Place> nowPlaces=address.getPlacesList();
                            	 nowPlaces.add(pinyinPlaces.get(i));
                            	 tempAddress.setPlacesList(nowPlaces);
                            	 addresses.add(tempAddress);
                              }
                            if (pinyinPlaces != null)
                                places.addAll(pinyinPlaces);
                        }
                        break;
                    case 13:
                        // 错误的词转化为标准词
                        ECEngine ecEngine = new ECEngine(
                               context, placeDao);
                        List<Place> errorPlaces = ecEngine
                                .getPlacesByErrorPlaceName(place, cityCode);
                        //2012-11-29 处理纠错词
                          addresses=new ArrayList<SegAddress>();                       
                        for(int i=0;i<errorPlaces.size();i++){
                      	 SegAddress tempAddress=new SegAddress();
                      	 List<Place> nowPlaces=address.getPlacesList();
                      	 nowPlaces.add(errorPlaces.get(i));
                      	 tempAddress.setPlacesList(nowPlaces);
                      	 addresses.add(tempAddress);
                        }
                        if (errorPlaces != null)
                            places.addAll(errorPlaces);
                        break;
                    default:
                        ThesaurusEngine tEngine = new ThesaurusEngine(
                                context, placeDao);
                        // 是地名词，并且不是门牌号,判断是否是同义词。如果是则需要转化
                        List<Place> tPlaces = tEngine.getPlacesByName(place,
                                cityCode);                     
                       address.getPlacesList().add(place);
                        if (tPlaces != null)
                            places.addAll(tPlaces);
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return places;
    }

    /**
     * 把门牌号添加到相对应的地名词中去
     * 
     * @param name 门牌号
     * @param places 地名词组
     * @param pre_placeName 前一个地名词名称
     * @return
     */
    // private List<Place> getPlacesByPlaceName(String name, List<Place> places,
    // String pre_placeName) {
    //
    // for (Iterator<Place> it = places.iterator(); it.hasNext();) {
    // Place place = it.next();
    // if (place.getPlaceLevel() < 6)
    // continue;
    // else {
    // if (pre_placeName.equalsIgnoreCase(place.getName())){
    // //判断是否name含有数字
    // if(place.getAllName()!=null&&!place.getAllName().equalsIgnoreCase(place.getName()))
    // continue;
    // place.setPlaceNum(StringUtils.selectIntegerFromString(name));
    // place.setAllName(pre_placeName+name);
    // }
    // break;
    // }
    // }
    // return places;
    // }

}
