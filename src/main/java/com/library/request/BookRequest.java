package com.library.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author XE on 18.09.2019
 * @project LibraryAPI
 */

public class BookRequest {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long bookId;
}
