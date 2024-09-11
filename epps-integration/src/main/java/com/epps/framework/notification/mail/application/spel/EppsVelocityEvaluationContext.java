package com.epps.framework.notification.mail.application.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class EppsVelocityEvaluationContext {
	
	 ExpressionParser parser;
	
	 Object dataObj;
	
	StandardEvaluationContext springEvaluationContext;
	
	

	public EppsVelocityEvaluationContext() {
		super();
	}



	public EppsVelocityEvaluationContext(ExpressionParser parser,
			Object dataObj, StandardEvaluationContext springEvaluationContext) {
		super();
		this.parser = parser;
		this.dataObj = dataObj;
		this.springEvaluationContext = springEvaluationContext;
	}
	
	public Object eval(String expression){
		return parser.parseExpression(expression).getValue(springEvaluationContext);
	}
	
	

}
