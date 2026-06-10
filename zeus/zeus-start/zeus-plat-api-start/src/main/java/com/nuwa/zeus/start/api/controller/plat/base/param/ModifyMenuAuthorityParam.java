package com.nuwa.zeus.start.api.controller.plat.base.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyMenuAuthorityParam extends NuwaCommand {

    private List<Menus> menus;

    @Data
    public static class Menus {
        private String menuId;
        private String parentMenuId;
    }
}
