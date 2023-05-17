package com.ruoyi.common.exception.user;

/**
 * 密码为初始密码，请重新修改密码
 * 
 * @author lk
 */
public class InitialPasswordNException extends UserException
{
    private static final long serialVersionUID = 1L;

    public InitialPasswordNException()
    {
        super("initail.passwrod.used", new Object[]{0});
    }
}
