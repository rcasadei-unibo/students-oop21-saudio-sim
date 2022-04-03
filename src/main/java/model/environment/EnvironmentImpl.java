package model.environment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import model.effect.ALEffects;
import model.listener.Listener;
import model.source.Source;
import model.source.hub.SourcesHub;
import model.utility.Vec3f;

public class EnvironmentImpl implements Environment {
    private final SourcesHub sourcesHub;
    private final Listener listener;
    private final Space space;
    private final Set<ALEffects> effect;

    public EnvironmentImpl(final SourcesHub sourcesHub, final Listener listener, final Space space) {
        super();
        this.sourcesHub = sourcesHub;
        this.listener = listener;
        this.space = space;
        this.effect = new HashSet<>();

        //check pos of sources
        // TODO  eliminare le source nella stessa posizione
        this.sourcesHub.getAll().stream().filter(s -> !this.space.isAvailable(s.getPosition())).collect(Collectors.toSet());
    }

    /**
    * 
    *{@inheritDoc}
    */
    @Override
    public Listener getListener() {
        return this.listener;
    }

    /**
    * 
    *{@inheritDoc}
    */
    @Override
    public Space getSpace() {
       return this.space;
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public SourcesHub getSourceHub() {
        return this.sourcesHub;
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void moveSourceWithID(final int id, final Vec3f pos) {
        final Source sourceToMove = this.sourcesHub.getSource(id);
        final int signX = (sourceToMove.getPosition().getX() + pos.getX()) < 0 ? -1 : 1;
        final int signY = (sourceToMove.getPosition().getY() + pos.getY()) < 0 ? -1 : 1;
        if (this.space.isAvailable(pos)) {
            sourceToMove.setPosition(pos);
        } else if (Math.abs(sourceToMove.getPosition().getX() - pos.getX()) < Math.abs(sourceToMove.getPosition().getY() - pos.getY())) {
            this.moveSourceWithID(id, new Vec3f(pos.getX() + signX * this.space.getScale(), pos.getY(), pos.getZ()));
        } else if (Math.abs(sourceToMove.getPosition().getX() - pos.getX()) > Math.abs(sourceToMove.getPosition().getY() - pos.getY())) {
            this.moveSourceWithID(id, new Vec3f(pos.getX(), pos.getY() + signY * this.space.getScale(), pos.getZ()));
        } else {
            this.moveSourceWithID(id, new Vec3f(pos.getX() + signX * this.space.getScale(), pos.getY() + signY * this.space.getScale(), pos.getZ()));
        }
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void moveSourceWithVec3f(final Vec3f oldPos, final Vec3f newPos) {
        final Source sourceToMove = this.sourcesHub.getSourceFromPos(oldPos);
        final int signX = (sourceToMove.getPosition().getX() + newPos.getX()) < 0 ? -1 : 1;
        final int signY = (sourceToMove.getPosition().getY() + newPos.getY()) < 0 ? -1 : 1;
        if (this.space.isAvailable(newPos)) {
            sourceToMove.setPosition(newPos);
        } else if (Math.abs(sourceToMove.getPosition().getX() - newPos.getX()) < Math.abs(sourceToMove.getPosition().getY() - newPos.getY())) {
            this.moveSourceWithVec3f(oldPos, new Vec3f(newPos.getX() + signX * this.space.getScale(), newPos.getY(), newPos.getZ()));
        } else if (Math.abs(sourceToMove.getPosition().getX() - newPos.getX()) > Math.abs(sourceToMove.getPosition().getY() - newPos.getY())) {
            this.moveSourceWithVec3f(oldPos, new Vec3f(newPos.getX(), newPos.getY() + signY * this.space.getScale(), newPos.getZ()));
        } else {
            this.moveSourceWithVec3f(oldPos, new Vec3f(newPos.getX() + signX * this.space.getScale(), newPos.getY() + signY * this.space.getScale(), newPos.getZ()));
        }
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void addEffect(final ALEffects effect, final float level) {
        this.effect.add(effect);
        this.sourcesHub.applyFilter(effect, level);
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void removeEffect(final ALEffects effect) {
        this.effect.remove(effect);
        this.sourcesHub.removeFilter(effect);
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public Set<ALEffects> getEffectSet() {
        return Collections.unmodifiableSet(this.effect);
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void addSourceToSourceHub(final Source source) {
        if (space.isAvailable(source.getPosition())) {
            sourcesHub.addSource(source);
        }
        //TODO se sbagliato restituire false
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void removeSourceFromSourceHubWithVec3f(final Vec3f posSource) {
        space.removeSourcePos(posSource);
        sourcesHub.removeSource(sourcesHub.getSourceFromPos(posSource));
    }

    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public void removeSourceFromSourceHubWithId(final int idSource) {
        final Source toDelete = sourcesHub.getSource(idSource);
        space.removeSourcePos(toDelete.getPosition());
        sourcesHub.removeSource(toDelete);
    }

}
