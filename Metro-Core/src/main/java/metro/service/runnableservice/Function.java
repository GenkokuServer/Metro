package metro.service.runnableservice;

import metro.service.Service;

public interface Function<T,R> extends Service, java.util.function.Function<T, R> {

}
