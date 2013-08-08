/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nitrox.sqlgendsl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Alessandro Lima (alessandrolima@gmail.com)
 */
public class SqlGeneratorTest {
    
    private SqlGenerator sg = new SqlGenerator();
        
    @Test
    public void shouldGenerateSimpleAsteriskSelect() {        

        String query = sg.SELECT("*").FROM("TABLE_A").build();

        assertEquals("SELECT * FROM TABLE_A", query);

    }

    @Test
    public void shouldGenerateMultipleColumnsSimpleSelect() {

        String query = sg.SELECT("COLUMN_A", "COLUMN_B", "COLUMN_C").FROM("TABLE_A").build();

        assertEquals("SELECT COLUMN_A, COLUMN_B, COLUMN_C FROM TABLE_A", query);
    }

    @Test
    public void shouldGenerateOneConditionWhere() {

        String query = sg.SELECT("COLUMN_A", "COLUMN_B", "COLUMN_C").FROM("TABLE_A").WHERE("1 = 1").build();

        assertEquals("SELECT COLUMN_A, COLUMN_B, COLUMN_C FROM TABLE_A WHERE 1 = 1", query);
    }

    @Test
    public void shouldGenerateMultipleConditionsWhere() {

        String query = sg.SELECT("COLUMN_A", "COLUMN_B", "COLUMN_C").FROM("TABLE_A").WHERE("1 = 1").
                AND("COLUMN_A = 001").AND("COLUMN_B = 'ABC'").OR("COLUMN_C = 'EFG'").OR("COLUMN_C LIKE '%DESCRIPTION'").build();

        String espected = "SELECT COLUMN_A, COLUMN_B, COLUMN_C FROM TABLE_A WHERE 1 "
                + "= 1 AND COLUMN_A = 001 AND COLUMN_B = 'ABC' OR COLUMN_C = 'EFG' "
                + "OR COLUMN_C LIKE '%DESCRIPTION'";
        assertEquals(espected, query);
    }

    @Test
    public void shouldGenerateNoOrderEspecificOrderBy() {

        String query = sg.SELECT("COLUMN_A", "COLUMN_B", "COLUMN_C").FROM("TABLE_A").WHERE("1 = 1").
                AND("COLUMN_A = 001").AND("COLUMN_B > ABC").ORDER_BY("COLUMN_C").build();

        String espected = "SELECT COLUMN_A, COLUMN_B, COLUMN_C FROM TABLE_A WHERE 1 "
                + "= 1 AND COLUMN_A = 001 AND COLUMN_B > ABC ORDER BY COLUMN_C";

        assertEquals(espected, query);
    }

    @Test
    public void shouldGenerateOrderByWithExplicitOrder() {

        String query = sg.SELECT("COLUMN_A", "COLUMN_B", "COLUMN_C").FROM("TABLE_A").WHERE("1 = 1").
                AND("COLUMN_A = 001").AND("COLUMN_B > ABC").ORDER_BY("COLUMN_C", Order.ASC).build();

        String espected = "SELECT COLUMN_A, COLUMN_B, COLUMN_C FROM TABLE_A WHERE 1 "
                + "= 1 AND COLUMN_A = 001 AND COLUMN_B > ABC ORDER BY COLUMN_C ASC";

        assertEquals(espected, query);
    }

    @Test
    public void shouldGenerateInnerJoinWithAnd() {
        
        String query = sg.SELECT("TB.*", "TC.*").
                FROM("TABLE_A TA").
                INNER_JOIN("TABLE_B TB").ON("TB.COLUMN_A = TA.COLUMN_A").
                AND("TB.COLUMN_B = TA.COLUMN_B").AND("TB.COLUMN_C = TA.COLUMN_C").
                INNER_JOIN("TABLE_C TC").ON("TB.COLUMN_D = TC.COLUMN_D").
                WHERE("TA.COLUMN_A = 8").AND("TA.COLUMN_B = 40").AND("TA.COLUMN_C = 201").build();

        String espected = "SELECT TB.*, TC.*"
                + " FROM TABLE_A TA"
                + " INNER JOIN TABLE_B TB ON TB.COLUMN_A = TA.COLUMN_A AND TB.COLUMN_B = TA.COLUMN_B AND TB.COLUMN_C = TA.COLUMN_C"
                + " INNER JOIN TABLE_C TC ON TB.COLUMN_D = TC.COLUMN_D"
                + " WHERE TA.COLUMN_A = 8 AND TA.COLUMN_B = 40 AND TA.COLUMN_C = 201";

        assertEquals(espected, query);
    }
}
