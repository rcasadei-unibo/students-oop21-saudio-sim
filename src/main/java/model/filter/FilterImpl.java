package model.filter;

import model.source.Source;
import model.source.SourceType;
import static org.lwjgl.openal.EXTEfx.alFilteri;
import static org.lwjgl.openal.EXTEfx.alFilterf;
import static org.lwjgl.openal.EXTEfx.AL_FILTER_TYPE;
import static org.lwjgl.openal.EXTEfx.AL_FILTER_NULL;
import static org.lwjgl.openal.EXTEfx.AL_FILTER_LOWPASS;
import static org.lwjgl.openal.EXTEfx.AL_LOWPASS_GAIN;
import static org.lwjgl.openal.EXTEfx.AL_LOWPASS_GAINHF;
import static org.lwjgl.openal.EXTEfx.AL_FILTER_BANDPASS;
import static org.lwjgl.openal.EXTEfx.AL_BANDPASS_GAIN;
import static org.lwjgl.openal.EXTEfx.AL_BANDPASS_GAINLF;
import static org.lwjgl.openal.EXTEfx.AL_BANDPASS_GAINHF;
import static org.lwjgl.openal.EXTEfx.AL_FILTER_HIGHPASS;
import static org.lwjgl.openal.EXTEfx.AL_HIGHPASS_GAIN;
import static org.lwjgl.openal.EXTEfx.AL_HIGHPASS_GAINLF;
import java.util.List;

/**
 * Extension of AbstractEffect, with methods apply and remove.
 *
 */
public class FilterImpl extends AbstractFilter {

    public FilterImpl() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyFilter(final Source source, final SourceType type) {
        initFilterBuffer();

        switch (type) {
        case LOW:
            apply(source, AL_FILTER_LOWPASS, List.of(AL_LOWPASS_GAIN, AL_LOWPASS_GAINHF), LOWPASS_VALUE);
            break;
        case MID:
            apply(source, AL_FILTER_BANDPASS, List.of(AL_BANDPASS_GAIN, AL_BANDPASS_GAINLF, AL_BANDPASS_GAINHF), BANDPASS_VALUE);
            break;
        case HIGH:
            apply(source, AL_FILTER_HIGHPASS, List.of(AL_HIGHPASS_GAIN, AL_HIGHPASS_GAINLF), HIGHPASS_VALUE);
            break;
        default:
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFilter(final Source source) {
        initFilterBuffer();
        final int filter = getFilter().get(0);

        alFilteri(filter, AL_FILTER_TYPE, AL_FILTER_NULL);

        setOnSource(filter, source);
    }

    /**
     * Apply the specified type of filter to the source.
     * @param source  the source
     * @param type  the type of the filter (lowpass, bandpass, highpass)
     * @param attributes  the attributes of the filter
     * @param value  the value for the filter, got from interface
     */
    private void apply(final Source source, final int type, final List<Integer> attributes, final float value) {
        final int filter = getFilter().get(0);
        alFilteri(filter, AL_FILTER_TYPE, type);

        for (final int attr : attributes) {
            alFilterf(filter, attr, value);
        }

        setOnSource(filter, source);
    }
}
