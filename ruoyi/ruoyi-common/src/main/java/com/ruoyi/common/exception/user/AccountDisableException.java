package com.ruoyi.common.exception.user;

/**
 * 
 * @author lk
 * 账号注销异常
 *
 */
public class AccountDisableException extends UserException {
	
	private static final long serialVersionUID = 1L;

	public AccountDisableException() {
		super("user.account.disable", null);

	}
}
