package com.swiftly.server;

import java.util.Date;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Measurement implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;
	private Long runId;
	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;	
	
	private double value;
	
	public Measurement() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date start) {
		this.startTime = start;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date end) {
		this.endTime = end;
	}				

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
         return new StringBuffer("{'id' : ").append(this.id)
                 .append(", 'runId : ").append(this.runId)
                 .append(", 'startTime' : ").append(this.startTime)
                 .append(", 'endTime' : ").append(this.endTime)
                 .append(", 'type' : ").append(this.type)
                 .append(", 'value' : ").append(this.value)                 
                 .toString();
     }
	
	
	
}
