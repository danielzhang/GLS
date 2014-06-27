package cn.gls.database.postgis.standard;
import java.util.Map;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import cn.gls.database.GLSDataBaseException;
import cn.gls.database.data.DataType;

/**
 * @ClassName: PostGISOperator.java
 * @Description 与数据库标准表之间的操作
 * @Date 2012-6-14
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public  class PostGISOperator{

public PostGISOperator(int type) {
    if((DataType.getMin()<=type)&&(DataType.getMax()>=type))
    this.type=type;
    else
        throw new GLSDataBaseException("定义"+getClass().getName()+"错误，超出导入类型范围，类型必须在{"+DataType.getMin()+"---"+DataType.getMax()+"}之间");
}
private  int type=0;

public void setType(int type) {
    if((DataType.getMin()<=type)&&(DataType.getMax()>=type))
    this.type = type;
    else
        throw new GLSDataBaseException("超出导入类型范围，类型必须在{"+DataType.getMin()+"---"+DataType.getMax()+"}之间");
}

public int insert(FeatureCollection<SimpleFeatureType, SimpleFeature> fcollection, Map<String, String> fieldMap, 
        String dbTableName,String dataType, String sCityName){
    if(type!=0){
    PostGISOperatorContext postGISContext=new PostGISOperatorContext();
    postGISContext.setOperator(type);
    return postGISContext.insert(fcollection, fieldMap, dbTableName,dataType, sCityName);
    }
    else{
        throw new GLSDataBaseException("超出导入类型范围，类型必须在{"+DataType.getMin()+"---"+DataType.getMax()+"}之间");
    }
}
}
