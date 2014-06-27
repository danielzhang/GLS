/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package cn.gls.geocoding.engine.dao;

import java.util.List;

import cn.gls.data.Place;
import cn.gls.geocoding.engine.data.GeoCodingResponse;

/**
 * @ClassName IHistroyDao.java
 * @Createdate 2012-6-3
 * @Description 对历史记录的访问
 * @Version 1.0
 * @Update 2012-6-3
 * @author "Daniel Zhang"
 */
public interface IHistroyDao {
/**
 * 把返回結果插入到标准化表中
 * 
 * @Param IHistroyDao
 * @Description TODO
 * @return int
 * @param response
 * @return
 */
int insertRecordToStandardTable(GeoCodingResponse response);
/**
 * 插入地名词到标准表中
 *@Param IHistroyDao
 *@Description TODO
 *@return boolean
 * @param places
 * @return
 */
boolean insertRecordPlaceToStandardTable(List<Place> places);
}
