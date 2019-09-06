package jeonghoj.boardproject.validator;

import jeonghoj.boardproject.domain.dto.RegisterRequestDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegisterRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequestDto registerRequestDto = (RegisterRequestDto) target;
        if(registerRequestDto.getUsername() == null || registerRequestDto.getUsername().trim().isEmpty()){
            errors.rejectValue("username","required","필수입니다");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","required","필수입니다");
        ValidationUtils.rejectIfEmpty(errors,"password","required","필수입니다");
        ValidationUtils.rejectIfEmpty(errors,"confirmPassword","required","필수입니다");
        if (!registerRequestDto.getPassword().isEmpty()) {
            if(!registerRequestDto.isPasswordEqualToConfirmPassword()){
                errors.rejectValue("confirmPassword","not match","일치하지 않습니다");
            }
        }
        if(registerRequestDto.getUsername().length() < 3 || registerRequestDto.getUsername().length() > 10){
            errors.rejectValue("username","length","3글자 이상 10글자 이하로 입력해주십시오");
        }
        if(registerRequestDto.getPassword().length() < 4 || registerRequestDto.getPassword().length() > 25){
            errors.rejectValue("password","length","4글자 이상 25글자 이하로 입력해주십시오");
        }
        if(registerRequestDto.getConfirmPassword().length() < 4 || registerRequestDto.getConfirmPassword().length() > 25){
            errors.rejectValue("confirmPassword","length","4글자 이상 25글자 이하로 입력해주십시오");
        }
    }
}
