package com.netmap.sql;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.WithItem;

public class BuildSQL {

	public static void main(String[] args) {
		try {
			Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM EXIMTRX.IPLC_MASTER WHERE C_MAIN_REF = 'ABC'");
			Select select = (Select) stmt;  
		    SelectBody selectBody = select.getSelectBody();
		    System.out.println(selectBody.toString());
		    List<WithItem> listWithItem = select.getWithItemsList();
		    for(int i=0;i<listWithItem.size();i++){
		    	WithItem withItem = listWithItem.get(i);
		    	String lstrWithItemName = withItem.getName();
		    	System.out.println(lstrWithItemName);
		    }
		    String lstrSQL = select.toString();
		    System.out.println(lstrSQL);
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}  
	}
}