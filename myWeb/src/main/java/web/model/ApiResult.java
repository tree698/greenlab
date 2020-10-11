package web.model;

import lombok.Data;

/**
 * API 함수에서 공통형식의 리턴값
 * @author KHKIM
 *
 */
@Data
public class ApiResult {
	
	/**
	 * 성공
	 */
	public final static int 	RET_SUCCESS_CODE = 1000;
	public final static String 	RET_SUCCESS_MSG	 = "성공하였습니다";
	
	/**
	 * 실패
	 */
	public final static int 	RET_FAIL_CODE 	= 9999;
	public final static String 	RET_FAIL_MSG 	= "실패하였습니다";
	
	/**
	 * 리턴 코드
	 */
	private int retCode;
	
	/**
	 * 리턴 메세지
	 */
	private String retMsg;
	
	/**
	 * 필요시 리턴 데이타
	 */
	private Object data;

	
	/**
	 * 기본형 응답
	 */
	public ApiResult(int retCode) {
		super();
		this.retCode = retCode;
	}
	
	/**
	 * 기본형 응답, 커스텀 메시지
	 */
	public ApiResult(int retCode, String retMsg) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
	
	/**
	 * 전체 응답데이터
	 */
	public ApiResult(int retCode, String retMsg, Object data) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
		this.data = data;
	}
}
