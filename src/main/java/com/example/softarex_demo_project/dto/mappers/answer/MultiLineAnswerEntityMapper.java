package com.example.softarex_demo_project.dto.mappers.answer;

import com.example.softarex_demo_project.dto.question.answer.MultiLineAnswerEntityDto;
import com.example.softarex_demo_project.model.question.MultiLineAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for MultiLineAnswerEntity and MultiLineAnswerEntityDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface MultiLineAnswerEntityMapper {
    MultiLineAnswerEntityMapper INSTANCE = Mappers.getMapper(MultiLineAnswerEntityMapper.class);

    @Mapping(source = "answerEntity", target = ".")
    MultiLineAnswerEntityDto entityToDto(MultiLineAnswerEntity answerEntity);

    @Mapping(source = "dto", target = ".")
    MultiLineAnswerEntity dtoToEntity(MultiLineAnswerEntityDto dto);
}
