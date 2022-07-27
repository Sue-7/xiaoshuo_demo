package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import com.xiaoshuotech.minmetals.association.api.dto.${entity?lower_case}.*;

/**
* <p>
* ${table.comment!} 服务类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    List<${entity}DTO>query${entity}List(${entity}ListRequest request, IPage page);

    ${entity}DTO query${entity}(${entity}DetailRequest request);

    void add${entity}(Add${entity}Request request);

    void edit${entity}(Edit${entity}Request request);

}
</#if>
