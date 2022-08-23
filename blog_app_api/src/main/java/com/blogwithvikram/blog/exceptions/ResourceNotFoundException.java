package com.blogwithvikram.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String resourceFieldName;
	long fieldValue;
	String username;

	public ResourceNotFoundException(String resourceName, String resourceFieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, resourceFieldName, fieldValue));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String resourceFieldName, String username) {
		super(String.format("%s not found with %s : %s", resourceName, resourceFieldName, username));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.username = username;
	}
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceFieldName() {
		return resourceFieldName;
	}

	public void setResourceFieldName(String resourceFieldName) {
		this.resourceFieldName = resourceFieldName;
	}

	public long getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
