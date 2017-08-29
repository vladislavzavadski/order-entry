package com.netcracker.orderentry.catalog.service;

import com.netcracker.orderentry.catalog.domain.Tag;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by ulza1116 on 8/21/2017.
 */
public interface TagService {
    Tag createTag(Tag tag);

    Tag getTag(int tagId) throws NotFoundException;

    void deleteTag(int tagId) throws NotFoundException;

    Tag updateTag(Tag tag, int tagId) throws NotFoundException;

    List<Tag> createTag(List<Tag> tagList);
}
