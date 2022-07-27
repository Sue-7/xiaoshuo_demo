package com.xiaoshuotech.minmetals.association.api.dto.${entity?lower_case};

<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
    <#if chainModel>
    </#if>
</#if>
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.xiaoshuotech.cloud.core.base.BaseRequest;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * ${table.comment!}编辑请求参数
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
</#if>
<#if swagger2>
@ApiModel(description="${table.comment!}编辑请求参数")
</#if>
public class Edit${entity}Request extends BaseRequest {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
