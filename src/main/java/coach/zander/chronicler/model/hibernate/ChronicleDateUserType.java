package coach.zander.chronicler.model.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import coach.zander.chronicler.exception.IllegalDateException;
import coach.zander.chronicler.model.ChronicleCalendar;
import coach.zander.chronicler.model.ChronicleDate;

/** Legacy database path: stores a date in its data format, read back with the current calendar. */
public class ChronicleDateUserType implements UserType {

	public Object assemble(Serializable cached, Object owner) {
		return cached;
	}

	public Object deepCopy(Object value) {
		if (value == null) {
			return null;
		}
		return value;
	}

	public Serializable disassemble(Object value) {
		return (ChronicleDate) value;
	}

	public boolean equals(Object x, Object y) {
		return x.equals(y);
	}

	public int hashCode(Object x) {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object replace(Object original, Object target, Object owner) {
		return original;
	}

	public Class<ChronicleDate> returnedClass() {
		return ChronicleDate.class;
	}

	public int[] sqlTypes() {
		return new int[] { StringType.INSTANCE.sqlType() };
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		assert names.length == 1;
		String string = (String) StringType.INSTANCE.get(rs, names[0], session);
		try {
			return string == null ? null : ChronicleCalendar.current().parse(string);
		} catch (IllegalDateException e) {
			throw new SQLException("Invalid date: " + string, e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			StringType.INSTANCE.set(st, null, index, session);
		} else {
			final ChronicleDate date = (ChronicleDate) value;
			StringType.INSTANCE.set(st, ChronicleCalendar.current().format(date), index, session);
		}
	}
}
