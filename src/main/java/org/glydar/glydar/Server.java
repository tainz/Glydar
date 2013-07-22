package org.glydar.glydar;

import org.glydar.glydar.models.Player;

import java.util.Collection;
import java.util.HashMap;

public class Server implements Runnable {

    private boolean running = true;

    public Collection<Player> getConnectedPlayers() {
        return Player.getConnectedPlayers();
    }

    @Override
    public void run() {
        while (running) {
            /* TODO Server loop / tick code.
               Eventually; All periodic events will be processed here, such as AI logic, etc for entities.
             */
        }
    }
}
