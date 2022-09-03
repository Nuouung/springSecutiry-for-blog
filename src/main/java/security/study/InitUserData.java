package security.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import security.study.entity.Authority;
import security.study.entity.EncryptionAlgorithm;
import security.study.entity.User;
import security.study.repository.AuthorityRepository;
import security.study.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitUserData {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void setInitUserData() {

        User user = new User();
        user.setUsername("jinseok");
        user.setPassword("$2a$10$8SltI0Jht3aZC.LNkfPZ1OeKh.iAAtVmnh.SkJpF1nqaH0RfIBOye"); // 1234
        user.setAlgorithm(EncryptionAlgorithm.BCRYPT);

        Authority authority = new Authority();
        authority.setName("read");
        authority.setUser(user);

        userRepository.save(user);
        authorityRepository.save(authority);
    }
}
