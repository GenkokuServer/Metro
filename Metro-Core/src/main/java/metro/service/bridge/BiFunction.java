package metro.service.bridge;

import metro.service.Service;

public interface BiFunction<T, U, R> extends Service, java.util.function.BiFunction<T, U, R> {

}
