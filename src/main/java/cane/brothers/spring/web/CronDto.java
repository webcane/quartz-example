package cane.brothers.spring.web;

public class CronDto {

	private String expression;

	/**
	 * Default constructor
	 */
	public CronDto() {
		super();
	}
	
	public CronDto(String expression) {
		super();
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "CronDto [expression=" + expression + "]";
	}	
}
