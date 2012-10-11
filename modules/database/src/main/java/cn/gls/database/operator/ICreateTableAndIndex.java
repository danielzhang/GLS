package cn.gls.database.operator;
/**
 * 
 * @date 2012-8-20
 * @author "Daniel Zhang"
 * @update 2012-8-20
 * @description 产生表及其索引的接口
 *
 */
public interface ICreateTableAndIndex {
	
          int existPlaceTable();
          void createPlaceTable();
          
          int existPinyinTable();
          void createPinyinTable();
          
          int existTycTable();
          void createTycTable();
          
          int existFatherAndSonTable();
          void createFatherAndSonTable();
          
          int existProvinceTable();
          void createProvinceTable();
          
          int existCityTable();
          void createCityTable();
          
          int existPoliticalTable();
          void createPoliticalTable();
          
          int existStreetTable();
          void createStreetTable();
          
          int existAddressTable();
          void createAddressTable();
          
          void createPlaceIndex();
          void createPinyinIndex();
          void createTycIndex();
          void createFatherAndSonIndex();
          void createProvinceIndex();
          void createCityIndex();
          void createPoliticalIndex();
          void createStreetIndex();
          void createAddressIndex();
          
         int existPlaceIndex();
         int existPinyinIndex();
         int existTycIndex();
         int existFatherAndSonIndex();
         int existProvinceIndex();
         int existCityIndex();
         int existPoliticalIndex();
         int existStreetIndex();
         int existAddressIndex();
          
}
