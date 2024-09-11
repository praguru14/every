package com.epps.framework.notification.mail.application.processor;

/**
 * @author Amit
 *
 */

public class QueryProcessor{
/*
public class QueryProcessor implements Processor {

	
	*//**
	 * Represents the hibernate session factory object.
	 *//*
	protected SessionFactory sessionFactory;
	
	*//**
	 * 
	 *//*
	private String query;
	
	@Override
	public void process(Exchange exchange) {
		Message in = exchange.getIn();
		Query query = sessionFactory.openSession().createQuery(this.query);
		List results = getLastUpdatedCondition(query).list();
		in.setBody(results);
	}
	
	private Query getLastUpdatedCondition(Query query) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -3);
		Date date = cal.getTime();
		query.setParameter("date", date);
		return query;
	}

	*//**
	 * Setter for the session factory.
	 * 
	 * @param sessionFactory Represents the hibernate session factory object.
	 *//*
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
*/}
