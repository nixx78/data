package lv.nixx.poc.db.dao;

import javax.persistence.Entity;

import java.util.Date;

import javax.persistence.*;

@NamedStoredProcedureQuery(name = "sp_get_bugs_by_period", procedureName = "sandbox_sp.get_bugs_by_period", resultClasses = {
		DayStatistic.class }, parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "start_date", type = Date.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "end_date", type = Date.class),
				@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "bugs", type = DayStatistic.class) })
@Entity
public class DayStatistic {

	@Id
	@Column(name ="month_day")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name="count")
	private int bugsCount;
	
	@Column(name="ids")
	private String bugsList;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getBugsCount() {
		return bugsCount;
	}

	public void setBugsCount(int bugsCount) {
		this.bugsCount = bugsCount;
	}

	public String getBugsList() {
		return bugsList;
	}

	public void setBugsList(String bugsList) {
		this.bugsList = bugsList;
	}

	@Override
	public String toString() {
		return "DayStatistic [date=" + date + ", bugsCount=" + bugsCount + ", bugsList=" + bugsList + "]";
	}

}
