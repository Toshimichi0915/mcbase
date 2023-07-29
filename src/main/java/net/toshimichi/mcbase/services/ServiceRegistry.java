package net.toshimichi.mcbase.services;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServiceRegistry {

    private final List<ServiceEntry> services = new ArrayList<>();
    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = true;
        services.forEach(service -> service.setEnabled(enabled));
    }

    public void register(Service service) {
        services.add(new ServiceEntry(service));
    }

    public void unregister(Service service) {
        services.removeIf(serviceEntry -> serviceEntry.getService() == service);
    }

    @Data
    private static class ServiceEntry {

        private final Service service;
        private boolean enabled;

        public void setEnabled(boolean enabled) {
            if (this.enabled == enabled) return;
            this.enabled = enabled;

            if (enabled) {
                service.onEnable();
            } else {
                service.onDisable();
            }
        }
    }
}
