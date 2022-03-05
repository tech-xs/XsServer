package tech.xs.system.domain.bo.page.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.ListPageBo;
import tech.xs.system.domain.entity.SysPageApi;


@Getter
@Setter
@ToString
public class PageListApiBo extends ListPageBo<SysPageApi> {
    private Long pageId;
    private Long apiId;
    private Long permissionId;
}
