package kr.co.ehr.cmn;

public class DTO {

	/**확장자*/
	private String ext;
	/** 총 글 수 */
	private int totalCnt;
	/** 글 번호 */
	private int num;

	public DTO() {
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "DTO [ext=" + ext + ", totalCnt=" + totalCnt + ", num=" + num + "]";
	}



}
