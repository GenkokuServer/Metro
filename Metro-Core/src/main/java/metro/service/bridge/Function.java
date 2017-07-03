package metro.service.bridge;

import metro.service.Service;

public interface Function<T,R> extends Service, java.util.function.Function<T, R> {

}
