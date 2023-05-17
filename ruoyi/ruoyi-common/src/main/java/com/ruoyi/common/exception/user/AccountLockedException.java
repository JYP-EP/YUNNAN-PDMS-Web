package com.ruoyi.common.exception.user;

/**
 * 
 * @author lk
 *账锁定异常
 */
public class AccountLockedException extends UserException {

	private static final long serialVersionUID = 1L;

	public AccountLockedException() {
		super("user.account.locked", null);

	}

}
