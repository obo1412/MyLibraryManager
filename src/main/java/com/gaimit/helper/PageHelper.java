package com.gaimit.helper;

public class PageHelper {
	/***** GET�뙆�씪誘명꽣濡� 泥섎━�븷 媛� *****/
    private int page = 1; 			// �쁽�옱 �럹�씠吏� 踰덊샇

    /***** DB�뿉�꽌 議고쉶�븳 寃곌낵 媛� *****/
    private int totalCount = 0;		// �쟾泥� 湲� 媛쒖닔 議고쉶

    /***** 媛쒕컻�옄媛� �젙�쓽�빐�빞 �븯�뒗 媛� *****/
    private int listCount = 10;		// �븳 �럹�씠吏��뿉 蹂댁뿬吏� 湲��쓽 紐⑸줉 �닔
    private int groupCount = 5;		// �븳 洹몃９�뿉 �몴�떆�븷 �럹�씠吏�踰덊샇 媛쒖닔

	/***** �뿰�궛泥섎━媛� �븘�슂�븳 媛� *****/
	private int totalPage = 0;		// �쟾泥� �럹�씠吏� �닔
    private int startPage = 0;		// �쁽�옱 洹몃９�쓽 �떆�옉 �럹�씠吏� 
    private int endPage = 0;		// �쁽�옱 洹몃９�쓽 留덉�留� �럹�씠吏�
    private int prevPage = 0;		// �씠�쟾 洹몃９�쓽 留덉�留� �럹�씠吏�
    private int nextPage = 0;		// �씠�쟾 洹몃９�쓽 泥� �럹�씠吏�
    private int limitStart = 0;		// MySQL�쓽 Limit �떆�옉 �쐞移�


	
	/** �럹�씠吏� 援ы쁽�뿉 �븘�슂�븳 怨꾩궛�떇�쓣 泥섎━�븯�뒗 硫붿꽌�뱶 */
	public void pageProcess(int page, int totalCount, int listCount, int groupCount) {
		this.page = page;
		this.totalCount = totalCount;
		this.listCount = listCount;
		this.groupCount = groupCount;

		// �쟾泥� �럹�씠吏� �닔
	    totalPage = ((totalCount-1)/listCount)+1;

	    // �쁽�옱 �럹�씠吏��뿉 ���븳 �삤李� 議곗젅
	    if (page < 0) {
	    	page = 1;
	    }
	    
	    if (page > totalPage) {
	    	page = totalPage;
	    }

	    // �쁽�옱 �럹�씠吏� 洹몃９�쓽 �떆�옉 �럹�씠吏� 踰덊샇
	    startPage = ((page - 1) / groupCount) * groupCount + 1;

	    // �쁽�옱 �럹�씠吏� 洹몃９�쓽 �걹 �럹�씠吏� 踰덊샇
	    endPage = (((startPage - 1) + groupCount) / groupCount) * groupCount;

	    // �걹 �럹�씠吏� 踰덊샇媛� �쟾泥� �럹�씠吏��닔瑜� 珥덇낵�븯硫� �삤李⑤쾾�쐞 議곗젅
	    if (endPage > totalPage) { endPage = totalPage; }

	    // �씠�쟾 洹몃９�쓽 留덉�留� �럹�씠吏�
	    if (startPage > groupCount) { prevPage = startPage - 1; }
	    else { prevPage = 0; }

	    // �떎�쓬 洹몃９�쓽 泥� �럹�씠吏�
	    if (endPage < totalPage) { nextPage = endPage + 1; }
	    else { nextPage = 0; }

	    // 寃��깋 踰붿쐞�쓽 �떆�옉 �쐞移�
	    limitStart = (page-1) * listCount;
	}

	public int getPage() { return page; }
	public void setPage(int page) { this.page = page; }

	public int getTotalCount() { return totalCount; }
	public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

	public int getListCount() { return listCount; }
	public void setListCount(int listCount) { this.listCount = listCount; }
	
	public int getGroupCount() { return groupCount; }
	public void setGroupCount(int groupCount) { this.groupCount = groupCount; }

	public int getTotalPage() { return totalPage; }
	public void setTotalPage(int totalPage) { this.totalPage = totalPage; }

	public int getStartPage() { return startPage; }
	public void setStartPage(int startPage) { this.startPage = startPage; }

	public int getEndPage() { return endPage; }
	public void setEndPage(int endPage) { this.endPage = endPage; }

	public int getPrevPage() { return prevPage; }
	public void setPrevPage(int prevPage) { this.prevPage = prevPage; }

	public int getNextPage() { return nextPage; }
	public void setNextPage(int nextPage) { this.nextPage = nextPage; }

	public int getLimitStart() { return limitStart; }
	public void setLimitStart(int limitStart) { this.limitStart = limitStart; }

	@Override
	public String toString() {
		return "PageHelper [page=" + page + ", totalCount=" + totalCount
				+ ", listCount=" + listCount + ", groupCount=" + groupCount
				+ ", totalPage=" + totalPage + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", prevPage=" + prevPage
				+ ", nextPage=" + nextPage + ", limitStart=" + limitStart + "]";
	}
}
