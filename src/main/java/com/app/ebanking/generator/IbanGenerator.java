// package com.app.ebanking.generator;

// import org.hibernate.HibernateException;
// import org.hibernate.engine.spi.SharedSessionContractImplementor;
// import org.hibernate.id.IdentifierGenerator;
// import org.iban4j.CountryCode;
// import org.iban4j.Iban;

// public class IbanGenerator implements IdentifierGenerator {
// // Considered iban4j in https://github.com/arturmkrtchyan/iban4j
// @Override
// public Object generate(SharedSessionContractImplementor session, Object
// object) throws HibernateException {
// Iban iban = Iban.random(CountryCode.AT);
// return iban;
// }
// }
