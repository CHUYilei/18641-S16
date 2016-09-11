package adapter;

import server.AutoServer;

/**
 * Created by ychu1 on 16/2/7.
 * <p>
 * Empty class hiding the abstarct ProxyAutomobile's real content
 */
public class BuildAuto extends ProxyAutomobile implements CreateAuto, UpdateAuto, AutoServer {
}
