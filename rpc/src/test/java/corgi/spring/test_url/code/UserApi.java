package corgi.spring.test_url.code;

import com.dounine.corgi.exception.SerException;

/**
 * Created by huanghuanlai on 16/9/27.
 */

public interface UserApi {

    void login(String username) throws SerException;
}
