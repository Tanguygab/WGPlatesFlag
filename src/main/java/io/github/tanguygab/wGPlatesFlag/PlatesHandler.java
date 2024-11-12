package io.github.tanguygab.wGPlatesFlag;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;

import javax.annotation.Nullable;

public class PlatesHandler extends FlagValueChangeHandler<State> {

    public static final Factory FACTORY = new Factory();
    public static class Factory extends Handler.Factory<PlatesHandler> {
        @Override
        public PlatesHandler create(Session session) {
            // create an instance of a handler for the particular session
            // if you need to pass certain variables based on, for example, the player
            // whose session this is, do it here
            return new PlatesHandler(session);
        }
    }

    @Nullable
    private State plates;

    // construct with your desired flag to track changes
    public PlatesHandler(Session session) {
        super(session, WGPlatesFlag.PRESSURE_PLATES_FLAG);
    }

    // ... override handler methods here
    @Override
    protected void onInitialValue(LocalPlayer player, ApplicableRegionSet set, State value) {
        plates = value;
    }

    @Override
    protected boolean onSetValue(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, State currentValue, State lastValue, MoveType moveType) {
        plates = currentValue;
        return true;
    }

    @Override
    protected boolean onAbsentValue(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, State lastValue, MoveType moveType) {
        plates = null;
        return true;
    }

    @Nullable
    public State getPlates() {
        return plates;
    }
}