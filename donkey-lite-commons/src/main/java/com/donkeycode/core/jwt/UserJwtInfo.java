package com.donkeycode.core.jwt;

import com.donkeycode.core.BaseEntity;

/**
 * @author yanjun.xue
 * @since 2019年6月27日
 */
public class UserJwtInfo extends BaseEntity implements UserJwt {

	private static final long serialVersionUID = 1L;

	private String userCode;
	private String notesId;
	private String notesName;

	public UserJwtInfo(String userCode, String notesId, String notesName) {
		this.userCode = userCode;
		this.notesId = notesId;
		this.notesName = notesName;
	}

	@Override
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Override
	public String getNotesId() {
		return notesId;
	}

	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}

	@Override
	public String getNotesName() {
		return notesName;
	}

	public void setNotesName(String notesName) {
		this.notesName = notesName;
	}
}
