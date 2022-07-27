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
import java.util.List;
import com.xiaoshuotech.cloud.core.base.PageRequest;

/**
* <p>
* ${table.comment!}列表查询请求参数
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
@Data
</#if>
<#if swagger2>
@ApiModel(description="${table.comment!}列表查询请求参数")
</#if>
public class ${entity}ListRequest extends PageRequest {

    @ApiModelProperty(value = "主键id")
    private List<Integer> pkidList;

}