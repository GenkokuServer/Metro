package metro.service;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class ServiceManager {

  public static final ServiceManager DEFAULT_MANAGER = new ServiceManager();

  public static ServiceManager getDefault() {
    return DEFAULT_MANAGER;
  }

  private final Map<ProviderInfo, ServiceProvider> providers = Maps.newHashMap();

  public ServiceProvider getProvider(ProviderInfo info) {
    return providers.get(info);
  }

  public Collection<ProviderInfo> getProviders() {
    return providers.keySet();
  }

}
