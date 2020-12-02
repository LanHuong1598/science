package vn.com.mta.science.module.user.schema;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PasswordChangeCreate implements Serializable {

    @NotNull
    @Size(min = 6)
    private String currentPassword;

    @NotNull
    @Size(min = 6)
    private String newPassword;

    private String hint;
}
