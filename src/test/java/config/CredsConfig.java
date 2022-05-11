package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:conf/creds.properties")
public interface CredsConfig extends Config {
    String login();
    String password();
}
