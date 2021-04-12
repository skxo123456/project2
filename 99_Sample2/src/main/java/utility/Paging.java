package utility;

public class Paging {
	//페이징 관련 변수	
	private int totalCount = 0 ; //총 레코드 건수
	private int totalPage = 0 ; //전체 페이지 수
	private int pageNumber = 0 ; //보여줄 페이지 넘버(표현 가능한 페이지는 1부터 totalPage까지이다.)
	private int pageSize = 0 ; //한 페이지에 보여줄 건수
	private int beginRow = 0 ; //현재 페이지의 시작 행
	private int endRow = 0 ; //현재 페이지의 끝 행
	private int pageCount = 5 ; //한 화면에 보여줄 페이지 링크 수 (페이지 갯수)=> 레코드 갯수 아님 
	private int beginPage = 0 ; //페이징 처리 시작 페이지 번호
	private int endPage = 0 ; //페이징 처리 끝 페이지 번호
	private int offset = 0 ; //건너뛰어야 할 레코드 갯수(총 레코드 갯수를 상황에 따라 필요한 만큼 분리하여 스킵하는 기능)
	private int limit = 0 ; //한 페이지에 보여줄 레코드 갯수. 위에 있는 pageSize와 똑같지만, 다른 용도를 부여하기 위해 따로 변수 제작.
	private String url = "" ; //예시 ==>  http://localhost:8080/MyServlet/list.ab
	private String pagingHtml = ""; //하단의 숫자 페이지 링크 [맨처음][이전]123[다음][맨끝]
	//검색을 위한 변수 추가
	private String whatColumn = "" ; //검색 모드(작성자, 글제목, 전체 검색은 all) 등등
	private String keyword = "" ; //검색할 단어 

	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getBeginRow() {
		return beginRow;
	}


	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}


	public int getEndRow() {
		return endRow;
	}


	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getBeginPage() {
		return beginPage;
	}


	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}


	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPagingHtml() {
		System.out.println("pagingHtml:"+pagingHtml);
		
		return pagingHtml;
//		pagingHtml:
//			&nbsp;<font color='red'>1</font>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=2&pageSize=2&whatColumn=null&keyword=null'>2</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=3&pageSize=2&whatColumn=null&keyword=null'>3</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=4&pageSize=2&whatColumn=null&keyword=null'>4</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=5&pageSize=2&whatColumn=null&keyword=null'>5</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=6&pageSize=2&whatColumn=null&keyword=null'>6</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=7&pageSize=2&whatColumn=null&keyword=null'>7</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=8&pageSize=2&whatColumn=null&keyword=null'>8</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=9&pageSize=2&whatColumn=null&keyword=null'>9</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=10&pageSize=2&whatColumn=null&keyword=null'>10</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=11&pageSize=2&whatColumn=null&keyword=null'>다음</a>&nbsp;&nbsp;<a href='/ex/list.ab?pageNumber=22&pageSize=2&whatColumn=null&keyword=null'>맨 끝</a>&nbsp;

	}


	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}


	public String getWhatColumn() {
		return whatColumn;
	}


	public void setWhatColumn(String whatColumn) {
		this.whatColumn = whatColumn;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public Paging(
			String _pageNumber, 
			String _pageSize,  
			int totalCount,
			String url, 
			String whatColumn, 
			String keyword) {		

		if(  _pageNumber == null || _pageNumber.equals("null") || _pageNumber.equals("")  ){
			System.out.println("_pageNumber:"+_pageNumber); // null
			_pageNumber = "1" ; // 무조건 1페이지 부터 보기 시작
		}
		this.pageNumber = Integer.parseInt( _pageNumber ) ; 

		if( _pageSize == null || _pageSize.equals("null") || _pageSize.equals("") ){
			_pageSize = "5" ; // 한 페이지에 보여줄 레코드 갯수. 바꾸는 숫자에 따라 한 페이지에 보여줄 게시글의 숫자를 재정의할 수 있음.
		}		
		this.pageSize = Integer.parseInt( _pageSize ) ;
		
		this.limit = pageSize ; // 한 페이지에 보여줄 레코드 갯수(limit과 pageSize 가 똑같은 역할을 맡게됨)

		this.totalCount = totalCount ;

		this.totalPage = (int)Math.ceil((double)this.totalCount / this.pageSize) ; // 만약 totalCount가 7이고 pageSize가 2이면, 7/2 인 3.5가 필요한 totalPage 로 계산되는데,
																				   // 바로 그 3.5의 계산식이 이곳에 있다. 그러나 그러한 소수점을 계산하려면 double 형변환이 필요하고, 이를 다시
																				   // int 형변환 후 올림 계산을 해주면 4가 된다.
		
		this.beginRow = ( this.pageNumber - 1 )  * this.pageSize  + 1 ; // [이전][다음] 등을 누를 때마다, 보여질 블록의 갯수를 제한한다 // 각 페이지의 첫번째 글을 의미
		this.endRow =  this.pageNumber * this.pageSize ; // 즉 beginRow, endRow 는 pageSize 에 절대적인 영향을 받는다. // 각 페이지의 마지막 글을 의미
														 // 만약 전체 필요한 페이지 블록이 8개라면, (1,2)(3,4)(5,6)(7,8)이 되게된다(pageSize가 2인 경우).
														 // 단, 주의할 점은 만약 pageSize가 10인 경우, 한 페이지에 10개의 글이 나오는데,
														 // 거기서 1페이지의 1번째, 2페이지의 11번째, 3페이지의 21번째 글이 beginRow가 되는 것이다.
		
		// 바로 아래의 조건문은, 예를 들어 9페이진 상태에서 글을 한개 삭제하고 보니 8페이지로 보여줄 총량이 줄어들어,
		// 다시 목록 페이지로 돌아올 때 9페이지가 아닌 수정된 8페이지로 돌아오게 하는 조건문 설정이다.
		// 그러한 경우 새롭게 바뀐 최종 페이지로 찾아가도록 하는 조건문이다.
		// 삭제 기능 완료 이후에 보면 좋은 장소. 보고있던 장소로 가겠지만, 총 글 갯수가 줄어, 그 페이지가 사라졌다면 그 직전의 페이지로 이동하도록 하는 if 조건문!
		if( this.pageNumber > this.totalPage ){
			this.pageNumber = this.totalPage ;
		}
		
		this.offset = ( pageNumber - 1 ) * pageSize ; // 1page:0개넘기, 2page:2개넘기, 3pgd:4개넘기
		
		if( this.endRow > this.totalCount ){
			this.endRow = this.totalCount ;
		}

		this.beginPage = ( this.pageNumber - 1 ) / this.pageCount * this.pageCount + 1  ;
		this.endPage = this.beginPage + this.pageCount - 1 ;
		
		System.out.println("pageNumber:"+pageNumber+"/totalPage:"+totalPage);	
		
		if( this.endPage > this.totalPage ){
			this.endPage = this.totalPage ;
		}
		
		System.out.println("pageNumber2:"+pageNumber+"/totalPage2:"+totalPage);	
		this.url = url ; //  /ex/list.ab
		this.whatColumn = whatColumn ;
		this.keyword = keyword ;
		System.out.println("whatColumn:"+whatColumn+"/keyword:"+keyword);
		
		this.pagingHtml = getPagingHtml(url) ;
	
	}
	
	private String getPagingHtml( String url ){ //페이징 문자열을 만든다.
		System.out.println("getPagingHtml url:"+url); // ex/list.ba
		
		String result = "" ;
		//added_param 변수 : 검색 관련하여 추가되는 파라미터 리스트
		String added_param = "&whatColumn=" + whatColumn + "&keyword=" + keyword ; 
		// &whatColumn=singer&keyword=아
		
		// 이전
		if (this.beginPage != 1) {
			result += "&nbsp;<a href='" + url  
					+ "?pageNumber=" + ( 1 ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>맨 처음</a>&nbsp;" ;
			result += "&nbsp;<a href='" + url 
					+ "?pageNumber=" + (this.beginPage - 1 ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>이전</a>&nbsp;" ;
			// 위의 것을 풀어쓰면 다음과 같다
			// <a href="ex/list.tv?pageNumber=1&pageSize=2>맨처음</a> : 무조건 1페이지 이동
		}
		
		// 중간
		for (int i = this.beginPage; i <= this.endPage ; i++) {
			if ( i == this.pageNumber ) {
				result += "&nbsp;<font color='red'>" + i + "</font>&nbsp;"	;
						
			} else {
				result += "&nbsp;<a href='" + url   
						+ "?pageNumber=" + i + "&pageSize=" + this.pageSize 
						+ added_param + "'>" + i + "</a>&nbsp;" ;
				
			}
		}
		System.out.println("result:"+result);  
		System.out.println();
		
		// 다음
		if ( this.endPage != this.totalPage) {
			
			result += "&nbsp;<a href='" + url  
					+ "?pageNumber=" + (this.endPage + 1 ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>다음</a>&nbsp;" ;
			
			result += "&nbsp;<a href='" + url  
					+ "?pageNumber=" + (this.totalPage ) + "&pageSize=" + this.pageSize 
					+ added_param + "'>맨 끝</a>&nbsp;" ;
		}		
		System.out.println("result2:"+result);
		
		return result ; // 위의 페이지 블록 설정에 대한 모든 내용이 result로 리턴이 된다.
	}	
	
}

