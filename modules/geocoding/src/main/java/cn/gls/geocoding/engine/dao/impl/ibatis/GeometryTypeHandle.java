package cn.gls.geocoding.engine.dao.impl.ibatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;
import com.ibatis.sqlmap.engine.type.TypeHandler;
import com.vividsolutions.jts.geom.Geometry;

public class GeometryTypeHandle extends BaseTypeHandler implements TypeHandler {

    public Object getResult(ResultSet arg0, String arg1) throws SQLException {
        Geometry geometry=(Geometry) arg0.getObject("the_geom");
        if(arg0.wasNull())
         return null;
        else
            return geometry;
    }

    public Object getResult(ResultSet arg0, int arg1) throws SQLException {
       Geometry geometry=(Geometry) arg0.getObject(arg1);
       if(arg0.wasNull())
        return null;
       else
           return geometry;
    }

    public Object getResult(CallableStatement arg0, int arg1)
            throws SQLException {
        Geometry geometry=(Geometry) arg0.getObject(arg1);
        if(arg0.wasNull())
         return null;
        else
            return geometry;
    }

    public void setParameter(PreparedStatement arg0, int arg1, Object arg2,
            String arg3) throws SQLException {
      arg0.setObject(arg1, ((Geometry)arg2));

    }

    public Object valueOf(String arg0) {
        
        return arg0;
    }

}
