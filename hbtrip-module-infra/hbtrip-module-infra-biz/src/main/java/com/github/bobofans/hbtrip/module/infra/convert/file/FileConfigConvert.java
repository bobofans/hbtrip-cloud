package com.github.bobofans.hbtrip.module.infra.convert.file;

import com.github.bobofans.hbtrip.module.infra.controller.admin.file.vo.config.FileConfigSaveReqVO;
import com.github.bobofans.hbtrip.module.infra.dal.dataobject.file.FileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 文件配置 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigSaveReqVO bean);

}