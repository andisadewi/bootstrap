package Backend;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public interface IEntity {
	void update(Statement stm) throws SQLException;
	void insert(Statement stm) throws SQLException, ParseException;
	void delete(Statement stm) throws SQLException;
}
