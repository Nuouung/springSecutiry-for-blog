package security.study.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SecretBoardTokenRepository {

    private final List<String> tokenList = new ArrayList<>();

    public void save(String token) {
        tokenList.add(token);
    }

    public List<String> getTokenList() {
        return tokenList;
    }
}
