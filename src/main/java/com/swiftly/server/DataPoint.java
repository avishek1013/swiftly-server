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
public class DataPoint implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;
	private Long runId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;	
	private double heartRate;	
	
	public DataPoint() {
		
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(double heartRate) {
		this.heartRate = heartRate;
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
	
	 @Override
     public String toString() {
         return new StringBuffer("{'id' : ").append(this.id)
                 .append(", 'runId : ").append(this.runId)
                 .append(", 'time' : ").append(this.time)
                 .append(", 'heartRate' : ").append(this.heartRate)
                 .toString();
     }
	
	
}
