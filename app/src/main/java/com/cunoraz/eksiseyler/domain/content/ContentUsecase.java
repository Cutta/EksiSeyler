package com.cunoraz.eksiseyler.domain.content;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public interface ContentUsecase {

    Call<ResponseBody> getPostList(String channel);

}
