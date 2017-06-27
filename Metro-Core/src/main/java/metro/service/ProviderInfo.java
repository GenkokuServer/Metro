package metro.service;

import java.util.UUID;

public interface ProviderInfo extends Comparable<ProviderInfo> {

  UUID getIdentity();

  String getProviderName();

  long getProviderVersion();

}
