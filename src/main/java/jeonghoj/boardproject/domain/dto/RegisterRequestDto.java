package jeonghoj.boardproject.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    @Length(min = 3, max = 30, message = "길이는 3글자에서 30글자 이하입니다")
    String username;
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 70)
    String password;
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 70)
    String confirmPassword;

    public boolean isPasswordEqualToConfirmPassword(){
        return this.password.equals(this.confirmPassword);
    }
}