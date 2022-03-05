package tech.xs.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import tech.xs.framework.constant.DefaultConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.context.XsContext;

import java.util.Date;


/**
 * Mybatis plus 自动填充
 *
 * @author 沈家文
 * @date 2020/10/21
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date currDate = new Date();
        Long currentUserId = XsContext.getUserId();
        Object originalObject = metaObject.getOriginalObject();
        BaseEntity entity = null;
        if (originalObject instanceof BaseEntity) {
            entity = (BaseEntity) originalObject;
        }
        if (entity != null) {
            if (entity.getUpdateTime() == null) {
                entity.setUpdateTime(currDate);
            }
            if (entity.getCreateTime() == null) {
                entity.setCreateTime(currDate);
            }
            if (currentUserId != null) {
                String currUser = currentUserId + "";
                if (entity.getCreateUser() == null) {
                    entity.setCreateUser(currUser);
                }
                if (entity.getUpdateUser() == null) {
                    entity.setUpdateUser(currUser);
                }
            } else {
                entity.setCreateUser(DefaultConstant.SYS_USER);
                entity.setUpdateUser(DefaultConstant.SYS_USER);
            }
        } else {
            if (currentUserId != null) {
                this.strictInsertFill(metaObject, "createUser", Long.class, currentUserId);
                this.strictInsertFill(metaObject, "updateUser", Long.class, currentUserId);
            } else {
                this.strictInsertFill(metaObject, "createUser", String.class, DefaultConstant.SYS_USER);
                this.strictInsertFill(metaObject, "updateUser", String.class, DefaultConstant.SYS_USER);
            }
            this.strictInsertFill(metaObject, "createTime", Date.class, currDate);
            this.strictInsertFill(metaObject, "updateTime", Date.class, currDate);
        }

        Long currentCompanyId = XsContext.getCompanyId();
        if (currentCompanyId != null) {
            this.strictInsertFill(metaObject, "companyId", Long.class, currentCompanyId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currDate = new Date();
        Object originalObject = metaObject.getOriginalObject();
        BaseEntity entity = null;
        Long currentUserId = XsContext.getUserId();
        this.strictUpdateFill(metaObject, "updateTime", Date.class, currDate);
        if (originalObject instanceof BaseEntity) {
            entity = (BaseEntity) originalObject;
        }
        if (entity != null) {
            if (entity.getUpdateTime() == null) {
                entity.setUpdateTime(currDate);
            }
            if (entity.getUpdateUser() == null && currentUserId != null) {
                entity.setUpdateUser(currentUserId + "");
            } else {
                entity.setUpdateUser(DefaultConstant.SYS_USER);
            }
        } else {
            if (currentUserId != null) {
                this.strictUpdateFill(metaObject, "updateUser", Long.class, currentUserId);
            } else {
                this.strictUpdateFill(metaObject, "updateUser", String.class, DefaultConstant.SYS_USER);
            }
            this.strictUpdateFill(metaObject, "updateTime", Date.class, currDate);
        }

        Long currentCompanyId = XsContext.getCompanyId();
        if (currentCompanyId != null) {
            this.strictUpdateFill(metaObject, "companyId", Long.class, currentCompanyId);
        }

    }
}
