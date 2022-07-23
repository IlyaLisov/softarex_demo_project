package com.example.softarex_demo_project.dto.mappers;

import com.example.softarex_demo_project.dto.mappers.answer.CheckboxAnswerEntityMapper;
import com.example.softarex_demo_project.dto.mappers.answer.DateAnswerEntityMapper;
import com.example.softarex_demo_project.dto.mappers.answer.MultiLineAnswerEntityMapper;
import com.example.softarex_demo_project.dto.mappers.answer.RadioButtonAnswerEntityMapper;
import com.example.softarex_demo_project.dto.mappers.answer.SingleLineAnswerEntityMapper;
import com.example.softarex_demo_project.dto.question.AnswerEntityDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.dto.question.answer.CheckboxAnswerEntityDto;
import com.example.softarex_demo_project.dto.question.answer.DateAnswerEntityDto;
import com.example.softarex_demo_project.dto.question.answer.MultiLineAnswerEntityDto;
import com.example.softarex_demo_project.dto.question.answer.RadioButtonAnswerEntityDto;
import com.example.softarex_demo_project.dto.question.answer.SingleLineAnswerEntityDto;
import com.example.softarex_demo_project.model.question.AnswerEntity;
import com.example.softarex_demo_project.model.question.CheckboxAnswerEntity;
import com.example.softarex_demo_project.model.question.DateAnswerEntity;
import com.example.softarex_demo_project.model.question.MultiLineAnswerEntity;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.model.question.RadioButtonAnswerEntity;
import com.example.softarex_demo_project.model.question.SingleLineAnswerEntity;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of QuestionMapper.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Component
public class QuestionMapperImplementation implements QuestionMapper {
    @Override
    public QuestionDto questionToQuestionDto(Question question) {
        if (question == null) {
            return null;
        }
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setAuthor(UserMapper.INSTANCE.userToUserDto(question.getAuthor()));
        questionDto.setRecipient(UserMapper.INSTANCE.userToUserDto(question.getRecipient()));
        questionDto.setQuestion(question.getQuestion());
        questionDto.setAnswerEntity(answerEntityToAnswerEntityDto(question.getAnswerEntity()));
        return questionDto;
    }

    @Override
    public Question questionDtoToQuestion(QuestionDto dto) {
        if (dto == null) {
            return null;
        }
        Question question = new Question();
        question.setId(dto.getId());
        question.setQuestion(dto.getQuestion());
        question.setAuthor(UserMapper.INSTANCE.userDtoToUser(dto.getAuthor()));
        question.setRecipient(UserMapper.INSTANCE.userDtoToUser(dto.getRecipient()));
        question.setAnswerEntity(answerEntityDtoToAnswerEntity(dto.getAnswerEntity()));
        return question;
    }

    @Override
    public AnswerEntityDto answerEntityToAnswerEntityDto(AnswerEntity answerEntity) {
        switch (answerEntity.getAnswerType()) {
            case CHECKBOX:
                return CheckboxAnswerEntityMapper.INSTANCE.entityToDto((CheckboxAnswerEntity) answerEntity);
            case DATE:
                return DateAnswerEntityMapper.INSTANCE.entityToDto((DateAnswerEntity) answerEntity);
            case MULTILINE_TEXT:
                return MultiLineAnswerEntityMapper.INSTANCE.entityToDto((MultiLineAnswerEntity) answerEntity);
            case RADIO_BUTTON:
                return RadioButtonAnswerEntityMapper.INSTANCE.entityToDto((RadioButtonAnswerEntity) answerEntity);
            case SINGLE_LINE_TEXT:
                return SingleLineAnswerEntityMapper.INSTANCE.entityToDto((SingleLineAnswerEntity) answerEntity);
            default:
                return null;
        }
    }

    @Override
    public AnswerEntity answerEntityDtoToAnswerEntity(AnswerEntityDto dto) {
        switch (dto.getAnswerType()) {
            case CHECKBOX:
                return CheckboxAnswerEntityMapper.INSTANCE.dtoToEntity((CheckboxAnswerEntityDto) dto);
            case DATE:
                return DateAnswerEntityMapper.INSTANCE.dtoToEntity((DateAnswerEntityDto) dto);
            case MULTILINE_TEXT:
                return MultiLineAnswerEntityMapper.INSTANCE.dtoToEntity((MultiLineAnswerEntityDto) dto);
            case RADIO_BUTTON:
                return RadioButtonAnswerEntityMapper.INSTANCE.dtoToEntity((RadioButtonAnswerEntityDto) dto);
            case SINGLE_LINE_TEXT:
                return SingleLineAnswerEntityMapper.INSTANCE.dtoToEntity((SingleLineAnswerEntityDto) dto);
            default:
                return null;
        }
    }
}
