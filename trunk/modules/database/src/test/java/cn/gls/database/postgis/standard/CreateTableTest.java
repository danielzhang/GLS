package cn.gls.database.postgis.standard;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cn.gls.database.LoadApplicationContext;
import cn.gls.database.operator.ICreateTableAndIndex;

@SuppressWarnings("deprecation")
public class CreateTableTest extends
		AbstractDependencyInjectionSpringContextTests {

	private ICreateTableAndIndex createTable;

	public void setCreateTable(ICreateTableAndIndex createTable) {
		this.createTable = createTable;
	}

	public CreateTableTest() {
		super();
		LoadApplicationContext loadApplicationContext = LoadApplicationContext
				.getInstance();
		createTable = (ICreateTableAndIndex) loadApplicationContext
				.getBean("databaseAssistDao");
	}

	public void testCreateTable() {
		int ri = createTable.existPlaceIndex();
		System.out.println(ri);
	}
}
