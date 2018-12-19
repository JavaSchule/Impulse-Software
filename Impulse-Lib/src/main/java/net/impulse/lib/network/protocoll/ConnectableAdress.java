package net.impulse.lib.network.protocoll;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConnectableAdress{
    private final int port;
    private final String host;
}
