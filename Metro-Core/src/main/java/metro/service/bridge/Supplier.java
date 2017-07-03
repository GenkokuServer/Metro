package metro.service.bridge;

import metro.service.Service;

public interface Supplier<T> extends Service, java.util.function.Supplier<T> {

}
