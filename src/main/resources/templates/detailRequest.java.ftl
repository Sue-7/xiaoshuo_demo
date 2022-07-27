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
import javax.validation.constraints.NotNull;

/**
* <p>
* ${table.comment!}详情查询请求参数
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
@Data
</#if>
<#if swagger2>
@ApiModel(description="${table.comment!}详情查询请求参数")
</#if>
public class ${entity}DetailRequest {

    @ApiModelProperty(value = "主键id", required = true)
    @NotNull(message = "主键id不可为空")
    private Integer pkid;

}
