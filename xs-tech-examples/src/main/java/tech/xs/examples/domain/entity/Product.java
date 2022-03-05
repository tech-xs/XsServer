package tech.xs.examples.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.Date;

/**
 * 产品
 *
 * @author 沈家文
 * @date 2021/2/19 16:14
 */
@Getter
@Setter
@ToString
public class Product extends BaseEntity {

    private String name;
    private String prodNo;

}
