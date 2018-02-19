package com.twosonsoft.pilot.action;

import org.apache.struts2.convention.annotation.Action;

public class BizAction extends ActionTemplate  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -548981064226574348L;

	@Action("")
	public String getBizData() throws Exception {

		BizData bizData = new BizData("data1", "data2", "data3");
		this.writeJsonToHttpServletResponse(bizData);

		return null;

	}

	class BizData {
		String d1;
		String d2;
		String d3;

		public BizData(String d1, String d2, String d3) {
			this.d1 = d1;
			this.d2 = d2;
			this.d3 = d3;
		}

		public String getD1() {
			return d1;
		}

		public void setD1(String d1) {
			this.d1 = d1;
		}

		public String getD2() {
			return d2;
		}

		public void setD2(String d2) {
			this.d2 = d2;
		}

		public String getD3() {
			return d3;
		}

		public void setD3(String d3) {
			this.d3 = d3;
		}

	}

}
