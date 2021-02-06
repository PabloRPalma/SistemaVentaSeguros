package bo.octasoft.hibernate;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;
 
public class MSSQLServerDialect extends SQLServer2012Dialect {
 
 public MSSQLServerDialect() {
  super();
   
  registerColumnType(Types.CHAR, "nchar(1)");
  registerColumnType(Types.LONGVARCHAR, "nvarchar(max)" );
  registerColumnType(Types.VARCHAR, 4000, "nvarchar($l)");
  registerColumnType(Types.VARCHAR, "nvarchar(max)");
  registerColumnType(Types.CLOB, "nvarchar(max)" );
  registerColumnType(Types.BIT, "bit");
  registerColumnType(Types.NCHAR, "nchar(1)");
  registerColumnType(Types.LONGNVARCHAR, "nvarchar(max)");
  registerColumnType(Types.NVARCHAR, 4000, "nvarchar($l)");
  registerColumnType(Types.NVARCHAR, "nvarchar(max)");
  registerColumnType(Types.NCLOB, "nvarchar(max)");

  registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName());
  registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
  registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
  registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName() );

 }
 
}
