package cn.gls.data;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @ClassName: StandardAddress.java
 * @Description 标准化的地址
 * @Date 2012-5-25
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-6
 */
public final class StandardAddress {
    /********** 基本上这个name代表的是POI点名称 或者没有地理信息的名词比如窝夫小子，中信银行,可以为空, *******/
    private String poiName;

    /** 标准化的地址信息 */
    private String st_address;

    /** 地址类型 */
    private String addr_type;

    /** 邮政编码 */
    private long postal_code;

    /*********************** 比较大的区域名词 ****************************************************************/
    private String province;// 2

    private String city;// 3

    private String county;// 4

    private String town;// 5

    /************** 在这里有严格的分界线啊。以下部门在地址输入的过程中都有可能产生重复的等级名词 ***********************************/
    private String village;// 6

    private String village_suffix;

    private String village_all;

    private String street;// 80不带门牌号

    private String street_suffix;// 街道号码

    private String street_all;// 8带门牌号的

    private String communitis;// 90不带门牌号

    private String communitis_suffix;// 小区号码

    private String communitis_all;// 9带门牌号的

    private String building;// 10级

    private String building_suffix;// 10带后缀的情况

    private String building_all;
    private transient String geoText;
    public String getGeoText() {
        return geoText;
    }

    public void setGeoText(String geoText) {
        this.geoText = geoText;
    }

    /**********************************几何图形**************************************************************************/
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /****************** 地名词的最高级别 ************************************************************************************/
    private transient int high_level = 0;
/************************构造函数***************************************************************************************/
    
    public StandardAddress(String st_address) {
        super();
        this.st_address = st_address;
    }

    public StandardAddress(String poiName, String st_address, String addr_type,
        long postal_code, String province, String city, String county,
        String town, String village, String village_suffix, String village_all,
        String street, String street_suffix, String street_all,
        String communitis, String communitis_suffix, String communitis_all,
        String building, String building_suffix, String building_all,
        int high_level) {
    super();
    this.poiName = poiName;
    this.st_address = st_address;
    this.addr_type = addr_type;
    this.postal_code = postal_code;
    this.province = province;
    this.city = city;
    this.county = county;
    this.town = town;
    this.village = village;
    this.village_suffix = village_suffix;
    this.village_all = village_all;
    this.street = street;
    this.street_suffix = street_suffix;
    this.street_all = street_all;
    this.communitis = communitis;
    this.communitis_suffix = communitis_suffix;
    this.communitis_all = communitis_all;
    this.building = building;
    this.building_suffix = building_suffix;
    this.building_all = building_all;
    this.high_level = high_level;
}

    public StandardAddress() {
        super();
    }

    
    /*********************************** set/get方法 ********************************************************************************************************/

    public String getAddr_type() {
        return addr_type;
    }

    public void setAddr_type(String addr_type) {
        this.addr_type = addr_type;
    }

    public long getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(long postal_code) {
        this.postal_code = postal_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunitis() {
        return communitis;
    }

    public void setCommunitis(String communitis) {
        this.communitis = communitis;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getSt_address() {
        return st_address;
    }

    public void setSt_address(String st_address) {
        this.st_address = st_address;
    }

    public String getVillage_suffix() {
        return village_suffix;
    }

    public void setVillage_suffix(String village_suffix) {
        this.village_suffix = village_suffix;
    }

    public String getVillage_all() {
        return village_all;
    }

    public void setVillage_all(String village_all) {
        this.village_all = village_all;
    }

    public String getStreet_suffix() {
        return street_suffix;
    }

    public void setStreet_suffix(String street_suffix) {
        this.street_suffix = street_suffix;
    }

    public String getStreet_all() {
        return street_all;
    }

    public void setStreet_all(String street_all) {
        this.street_all = street_all;
    }

    public String getCommunitis_suffix() {
        return communitis_suffix;
    }

    public void setCommunitis_suffix(String communitis_suffix) {
        this.communitis_suffix = communitis_suffix;
    }

    public String getCommunitis_all() {
        return communitis_all;
    }

    public void setCommunitis_all(String communitis_all) {
        this.communitis_all = communitis_all;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuilding_suffix() {
        return building_suffix;
    }

    public void setBuilding_suffix(String building_suffix) {
        this.building_suffix = building_suffix;
    }

    public String getBuilding_all() {
        return building_all;
    }

    public void setBuilding_all(String building_all) {
        this.building_all = building_all;
    }

    public int getHigh_level() {
        return high_level;
    }

    public void setHigh_level(int high_level) {
        this.high_level = high_level;
    }

}
