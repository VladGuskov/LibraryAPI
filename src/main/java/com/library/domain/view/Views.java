package com.library.domain.view;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */


public final class Views {

    public interface Id {}

    public interface IdName extends Id {}

    public interface IdNameParams extends IdName {}

    public interface FullBook extends IdNameParams {}

    public interface FullLibrary extends IdNameParams {}

    public interface FullAuthor extends IdNameParams {}

    public interface FullGenre extends IdNameParams {}

    public interface FullPublishingHouse extends IdNameParams {}
}
