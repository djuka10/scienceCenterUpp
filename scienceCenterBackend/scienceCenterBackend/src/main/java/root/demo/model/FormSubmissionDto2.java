package root.demo.model;

import java.io.Serializable;

public class FormSubmissionDto2 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fieldId;
	Object fieldValue;
	
	
	public FormSubmissionDto2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormSubmissionDto2(String fieldId, Object fieldValue) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}
