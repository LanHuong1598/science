package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.user.model.Gender;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

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
