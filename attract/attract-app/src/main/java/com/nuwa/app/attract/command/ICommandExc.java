package com.nuwa.app.attract.command;

import com.alibaba.cola.dto.Response;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;

/**
 * @author hy
 */
public interface ICommandExc<C extends NuwaCommand, R extends Response> {
    /**
     * 命令实现方法
     *
     * @param cmd 具体命令
     * @return Response
     */
    public R execute(C cmd);
}
