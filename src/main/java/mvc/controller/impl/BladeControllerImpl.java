package mvc.controller.impl;

import mvc.controller.BladeController;
import mvc.model.SliceableModel;

/**
 * Blade controller implementation. Check the relative interface for the documentation.
 */
public class BladeControllerImpl implements BladeController {

    /**
     * {@inheritdoc}.
     */
    @Override
    public void cutSliceable(final SliceableModel sliceable) {
        sliceable.cut();
    }

}
