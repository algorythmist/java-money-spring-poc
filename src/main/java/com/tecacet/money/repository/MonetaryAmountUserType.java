package com.tecacet.money.repository;

import com.tecacet.money.util.MoneyUtil;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.javamoney.moneta.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.money.MonetaryAmount;

/**
 * Hibernate User type to convert to and from monetary amounts
 */
public class MonetaryAmountUserType implements UserType {

    private static final int[] SQL_TYPES = {Types.NUMERIC, Types.VARCHAR};

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names,
            SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException, SQLException {

        BigDecimal value = resultSet.getBigDecimal(names[0]);
        if (resultSet.wasNull()) {
            return null;
        }
        String currencyCode = resultSet.getString(names[1]);
        return Money.of(value, currencyCode);
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index,
            SharedSessionContractImplementor sharedSessionContractImplementor)
            throws HibernateException, SQLException {

        if (value == null) {
            statement.setNull(index, Types.NUMERIC);
            statement.setNull(index + 1, Types.VARCHAR);
        } else {
            MonetaryAmount monetaryAmount = (MonetaryAmount) value;
            BigDecimal amount = MoneyUtil.extractAmount(monetaryAmount);
            statement.setBigDecimal(index, amount);
            String currencyCode = MoneyUtil.extractCurrencyCode(monetaryAmount);
            statement.setString(index + 1, currencyCode);
        }
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return MonetaryAmount.class;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object deepCopy(Object value) {
        return value; // MonetaryAmount is immutable
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }
}
