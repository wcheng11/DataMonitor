package edu.thss.monitor.base.redis;

public class AppLogTest{

	private String operateDetail;
	public String getOperateDetail() {
		return operateDetail;
	}
	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}
	public String getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
	private String operateResult;

	@Override
	public boolean equals(Object b)
	{
		AppLogTest test = (AppLogTest) b;
		if (operateDetail.equals(test.operateDetail) && operateResult.equals(test.operateResult))
			return true;
		else
			return false;
	}
}
