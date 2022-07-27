package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoshuotech.cloud.core.util.CopyDtoUtils;
import com.xiaoshuotech.cloud.core.util.Func;
import com.xiaoshuotech.cloud.mybatis.util.ModelUtils;
import java.util.List;
import com.xiaoshuotech.minmetals.association.api.dto.${entity?lower_case}.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import com.xiaoshuotech.minmetals.association.api.enums.ValidEnum;

/**
* <p>
* ${table.comment!} 服务实现类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Service
@Slf4j
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public List<${entity}DTO> query${entity}List(${entity}ListRequest request, IPage page){
        return this.baseMapper.query${entity}List(request, page);
    }

    @Override
    public ${entity}DTO query${entity}(${entity}DetailRequest request) {
        ${entity} record = new ${entity}();
        record.setPkid(request.getPkid());
        record.setValid(ValidEnum.VALID.getCode());
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>(record);
        ${entity} ${entity?uncap_first} = super.getOne(queryWrapper);
        if (Func.isNull(${entity?uncap_first})) {
            return null;
        }
        ${entity}DTO ${entity?uncap_first}DTO = CopyDtoUtils.copyDto(${entity?uncap_first}, ${entity}DTO.class, true);
        return ${entity?uncap_first}DTO;
    }

    @Override
    public void add${entity}(Add${entity}Request request) {

    }

    @Override
    public void edit${entity}(Edit${entity}Request request) {

    }


}
</#if>