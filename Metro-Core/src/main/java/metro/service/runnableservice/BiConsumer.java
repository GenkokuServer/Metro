package metro.service.runnableservice;

import metro.service.Service;

public interface BiConsumer<T,U> extends Service, java.util.function.BiConsumer<T, U> {

}
