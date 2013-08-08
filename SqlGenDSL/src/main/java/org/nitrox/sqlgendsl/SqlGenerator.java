/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nitrox.sqlgendsl;

import com.google.common.base.Joiner;
import java.util.Arrays;

/**
 *
 * @author Alessandro Lima (alessandrolima@gmail.com)
 */
public class SqlGenerator {

    StringBuilder query;

    public SqlGenerator() {
        query = new StringBuilder();
    }

    public SqlGenerator SELECT(String... columns) {

        query.append("SELECT ");
        query.append(Joiner.on(", ").join(Arrays.asList(columns)));       
        return this;
    }

    public SqlGenerator FROM(String tableObject) {
        query.append(" FROM ");
        query.append(tableObject);
        return this;
    }

    public SqlGenerator WHERE(String clausula) {
        query.append(" WHERE ");
        query.append(clausula);
        return this;
    }
    
    public SqlGenerator AND(String clausula) {
        query.append(" AND ");
        query.append(clausula);
        return this;
    }
    
    public SqlGenerator OR(String clausula) {
        query.append(" OR ");
        query.append(clausula);
        return this;
    }
    
    public SqlGenerator LIKE(String clausula) {
        query.append(" LIKE ");
        query.append(clausula);
        return this;
    }
    
    public SqlGenerator IN(String... values) {
        query.append(" IN ");
        query.append("(");
        query.append(Joiner.on(", ").join(Arrays.asList(values)));
        query.append(")");
        return this;
    }
    
    public SqlGenerator INNER_JOIN(String table) {
        query.append(" INNER JOIN ");        
        query.append(table);
        return this;
    }
    
    public SqlGenerator RIGHT_JOIN(String table) {
        query.append(" RIGHT JOIN ");        
        query.append(table);
        return this;
    }
    
    public SqlGenerator LEFT_JOIN(String table) {
        query.append(" LEFT JOIN ");        
        query.append(table);
        return this;
    }    
    
    public SqlGenerator JOIN(String table) {
        query.append(" JOIN ");
        query.append(table);
        return this;
    }
    
    public SqlGenerator ON(String clausule) {
        query.append(" ON ");
        query.append(clausule);
        return this;
    }
    
    public SqlGenerator ORDER_BY(String coluna) {
        query.append(" ORDER BY ");
        query.append(coluna);
        return this;
    }

    public SqlGenerator ORDER_BY(String coluna, Order order) {
        ORDER_BY(coluna);
        query.append(" ");
        query.append(order.name());
        return this;
    }

    public String build() {
        String sql = query.toString();
        query = new StringBuilder();
        return sql;
    }
}
