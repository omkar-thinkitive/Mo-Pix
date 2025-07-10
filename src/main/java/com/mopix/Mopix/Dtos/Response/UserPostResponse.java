package com.mopix.Mopix.Dtos.Response;

import com.mopix.Mopix.Entity.PostEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPostResponse {

    private PostEntity postEntity;
}
