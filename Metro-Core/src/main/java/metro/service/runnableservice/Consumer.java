package metro.service.runnableservice;

import metro.service.Service;

public interface Consumer<T> extends Service, java.util.function.Consumer<T> {

}
