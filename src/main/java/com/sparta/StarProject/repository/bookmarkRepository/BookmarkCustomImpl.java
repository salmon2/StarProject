package com.sparta.StarProject.repository.bookmarkRepository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.StarProject.domain.QBookmark;
import com.sparta.StarProject.dto.BookmarkMapList;
import com.sparta.StarProject.dto.QBookmarkMapList;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.StarProject.domain.QBookmark.bookmark;

@RequiredArgsConstructor
public class BookmarkCustomImpl implements BookmarkCustom{

}
