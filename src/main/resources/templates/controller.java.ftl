package ${package.Controller};

import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiaoshuotech.cloud.core.base.BaseResponse;
import com.xiaoshuotech.cloud.core.util.Func;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshuotech.minmetals.association.api.util.PageRequestUtils;
import javax.validation.Valid;
import java.util.List;
import com.xiaoshuotech.minmetals.association.api.dto.${entity?lower_case}.*;

<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@Api(tags = "${table.comment!}", value = "${table.comment!}API")
@RestController
<#else>
@Controller
</#if>@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if><#else><#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass}{<#else>public class ${table.controllerName} {</#if>

    @Autowired
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};

    @ApiOperation(value = "列表", notes = "${table.comment!}列表查询")
    @PostMapping("/query${entity}List")
    public BaseResponse<IPage<${entity}DTO>> query${entity}List(@RequestBody ${entity}ListRequest request){
        IPage page = PageRequestUtils.createQueryPage(request);
        List<${entity}DTO> list = ${(table.serviceName?substring(1))?uncap_first}.query${entity}List(request, page);
        if (Func.isEmpty(page)) {
            page = new Page<>(1, Func.isEmpty(list) ? 10 : list.size());
            page.setTotal(Func.isEmpty(list) ? 0 : list.size());
        }
        page.setRecords(list);
        return BaseResponse.ok(page);
    }

    @PostMapping(value = "/query${entity}")
    @ApiOperation(value = "详情", notes = "${table.comment!}详情查询")
    public BaseResponse<${entity}DTO> query${entity}(@RequestBody @Valid ${entity}DetailRequest request) {
        ${entity}DTO detail = ${(table.serviceName?substring(1))?uncap_first}.query${entity}(request);
        return BaseResponse.ok(detail);
    }

    @PostMapping(value = "/add${entity}")
    @ApiOperation(value = "新增", notes = "${table.comment!}新增")
    public BaseResponse add${entity}(@RequestBody @Valid Add${entity}Request request) {
        ${(table.serviceName?substring(1))?uncap_first}.add${entity}(request);
        return BaseResponse.ok();
    }

    @PostMapping(value = "/edit${entity}")
    @ApiOperation(value = "编辑", notes = "${table.comment!}编辑")
    public BaseResponse edit${entity}(@RequestBody @Valid Edit${entity}Request request) {
        ${(table.serviceName?substring(1))?uncap_first}.edit${entity}(request);
        return BaseResponse.ok();
    }
}


</#if>