package subject.bean;

import java.sql.Timestamp;

public class AdultHealthBean {
	/* 身高 */
	private int personTall;
	/* 體重下限 */
	private float lowerBound;
	/* 體重上限 */
	private float upperBound;
	/* 肥胖體重值 */
	private String fatBodyWeight;
	/* 匯入時間 */
	private Timestamp time;
	/* 匯入的檔案類型 */
	private String fileType;

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getPersonTall() {
		return personTall;
	}

	public void setPersonTall(int personTall) {
		this.personTall = personTall;
	}

	public float getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(float lowerBound) {
		this.lowerBound = lowerBound;
	}

	public float getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(float upperBound) {
		this.upperBound = upperBound;
	}

	public String getFatBodyWeight() {
		return fatBodyWeight;
	}

	public void setFatBodyWeight(String fatBodyWeight) {
		this.fatBodyWeight = fatBodyWeight;
	}
}
