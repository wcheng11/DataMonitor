package edu.thss.monitor.pub.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;
/**
 * 本项目使用的Dialect
 * 由于Oracle10gDialect不支持对Oracle数据库NVARCHAR类型字段的原生SQL查询，因此此处继承并注册Hibernate类型
 * @author yangtao
 */
public class MonitorOracleDialect extends Oracle10gDialect {

	public MonitorOracleDialect() {
        super();
        registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());      
    }
}
