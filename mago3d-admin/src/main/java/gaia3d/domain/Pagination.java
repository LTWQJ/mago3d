package gaia3d.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Service 계층에서 페이지 처리를 Controller 계층으로 전달하기 위한 도메인
 * 从Service阶层将页面处理传递到Controller阶层的域名
 * @author jeongdae
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    /**
    * @param uri
    * @param searchParameters
    * @param totalCount
    * @param pageNo
    */
    public Pagination(String uri, String searchParameters, long totalCount, long pageNo) {
        this.uri = uri;
        this.searchParameters = searchParameters;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        if(this.pageNo == 0l) this.pageNo = 1l;
        this.pageRows = 10l;
        this.pageListCount = 5l;
        init();
    }

    /**
    * @param uri
    * @param searchParameters
    * @param totalCount
    * @param pageNo
    * @param pageRows
    */
    public Pagination(String uri, String searchParameters, long totalCount, long pageNo, long pageRows) {
        this.uri = uri;
        this.searchParameters = searchParameters;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageRows = pageRows;
        this.pageListCount = 10l;
        init();
    }

    /**
    * @param uri
    * @param searchParameters
    * @param totalCount
    * @param pageNo
    */
    public Pagination(String uri, String searchParameters, long totalCount, long pageNo, long pageRows, long pageListCount) {
        this.uri = uri;
        this.searchParameters = searchParameters;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageRows = pageRows;
        this.pageListCount = pageListCount;
        init();
    }

    private void init() {
    	this.rowNumber = this.totalCount - (this.pageNo - 1l) * this.pageRows;

        this.offset = (this.pageNo - 1l) * this.pageRows;

        this.lastPage = 0l;
        if(this.totalCount != 0l) {
            if(this.totalCount % this.pageRows == 0) {
                this.lastPage = (this.totalCount / this.pageRows);
            } else {
                this.lastPage = (this.totalCount / this.pageRows) + 1l;
            }
        }

        this.startPage = ((this.pageNo - 1l) / this.pageListCount) * this.pageListCount + 1l;
        this.endPage = ((this.pageNo - 1l) / this.pageListCount) * this.pageListCount + pageListCount;
        if(this.endPage > this.lastPage) {
            this.endPage = this.lastPage;
        }

        long remainder = 0l;
        this.prePageNo = 0l;
        if(this.pageNo > pageListCount) {
            // TODO 이전을 눌렀을때 현재 페이지 - 10 이 아닌 항상 1, 11, 21... 형태로 표시하고 싶을때
            remainder = this.pageNo % this.pageListCount;
            this.prePageNo = this.pageNo - this.pageListCount - remainder + 1l;
            // TODO 이전을 눌렀을때 현재 페이지 - 10 형태로 표시하고 싶을경우 3, 13, 23 ...
//			this.prePageNo = this.pageNo - this.pageListCount;
            existPrePage = true;
        }

        this.nextPageNo = 0l;
        if(this.lastPage > pageListCount && this.pageNo <= ((this.lastPage / this.pageListCount) * this.pageListCount)) {
            if(this.lastPage >= (this.startPage + this.pageListCount)) {
                if(this.pageNo % this.pageListCount == 0l) {
                    // TODO 다음을 눌렀을때 현재 페이지 + 10 이 아닌 항상 11, 21, 31... 형태로 표시하고 싶을때
                    this.nextPageNo = this.pageNo + 1l;
                } else {
                    // TODO 다음을 눌렀을때 현재 페이지 + 10 이 아닌 항상 11, 21, 31... 형태로 표시하고 싶을때
                    if(this.lastPage >= this.pageNo + this.pageListCount) {
                        remainder = this.pageNo % this.pageListCount;
                        this.nextPageNo = this.pageNo + this.pageListCount - remainder + 1l;
                        // TODO 다음을 눌렀을때 현재 페이지 + 10 형태로 표시하고 싶을경우 13, 23, 33 ...
    //					this.nextPageNo = this.pageNo + this.pageListCount;
                    } else {
                        remainder = this.lastPage % this.pageListCount;
                        this.nextPageNo = this.lastPage - remainder + 1l;
                        // TODO 다음을 눌렀을때 현재 페이지 + 10 형태로 표시하고 싶을경우 13, 23, 33 ...
    //					this.nextPageNo = this.lastPage;
                    }
                }
                existNextPage = true;
            }
        }

        if(this.totalCount == 0l) {
            this.pageNo = 0l;
        }
    }

    // 총 건수
    private long totalCount;
    // 게시물 번호
    private Long rowNumber;
    // 페이지 번호
    private long pageNo = 1l;
    // 처음
    private long firstPage = 1l;
    // 끝
    private long lastPage;
    // 페이지 시작
    private long startPage;
    // 페이지 종료
    private long endPage;

    // 이전
    private long prePageNo;
    // 다음
    private long nextPageNo;
    // 이전 페이지 존재여부 Flag
    private boolean existPrePage = false;
    // 다음 페이지 존재여부 Flag
    private boolean existNextPage = false;

    // 한 페이지에 표시할 건수, 목록 페이지에서는 기본 10건(총건수가 23개 라면 20개씩 2page 처럼 몇개가 한 페이지 인지)
    private long pageRows;
    // pageNo를 몇개까지 표시하고 다음 페이지로 넘길건지( < 1 2 3 4 5 6 7 > 이 경우 7이 됨)
    private long pageListCount;

    // 페이지 처리를 위한 시작
    private Long offset;

    /******* 페이징 링크 변수 ******/
    private String uri;

    /******** 검색 조건 **********/
    private String searchParameters;

    private long pageIndex;

}
