package metro.service;

public interface ServiceProvider {

  Service getService();

  Class<? extends Service> getServiceType();

}
