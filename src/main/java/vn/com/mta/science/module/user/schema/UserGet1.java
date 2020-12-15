package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.user.model.User;

@Getter
@Setter
@NoArgsConstructor
public class UserGet1 extends SchemaGet<User, Long> {

    private String token;

    private String au;

    public UserGet1(User user) {
        super(user);
    }

    @Override
    public void parse(User user) {
    }
}
