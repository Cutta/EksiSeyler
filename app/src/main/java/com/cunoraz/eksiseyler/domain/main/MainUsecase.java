package com.cunoraz.eksiseyler.domain.main;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

public interface MainUsecase {

    boolean isSavingModeActive();

    void storeSavingModeStatus(boolean willDisplayImages);

}
