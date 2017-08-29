package com.netcracker.orderentry.catalog.service;

import com.netcracker.orderentry.catalog.domain.Tag;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
public interface TagService {
    Tag createTag(Tag tag);

    Tag getTag(int tagId);

    void deleteTag(int tagId);

    void updateTag(Tag tag, int tagId);

    void createTag(List<Tag> tagList);
}
